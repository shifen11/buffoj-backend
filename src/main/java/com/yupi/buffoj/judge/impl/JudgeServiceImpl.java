package com.yupi.buffoj.judge.impl;

import cn.hutool.json.JSONUtil;
import com.yupi.buffoj.common.ErrorCode;
import com.yupi.buffoj.exception.BusinessException;
import com.yupi.buffoj.judge.JudgeService;
import com.yupi.buffoj.judge.codesandbox.CodeSandbox;
import com.yupi.buffoj.judge.codesandbox.CodeSandboxFactory;
import com.yupi.buffoj.judge.codesandbox.CodeSandboxProxy;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.buffoj.model.dto.question.JudgeCase;
import com.yupi.buffoj.model.dto.question.JudgeConfig;
import com.yupi.buffoj.model.dto.questionSubmit.JudgeInfo;
import com.yupi.buffoj.model.entity.Question;
import com.yupi.buffoj.model.entity.QuestionSubmit;
import com.yupi.buffoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.buffoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.buffoj.model.vo.QuestionSubmitVO;
import com.yupi.buffoj.service.QuestionService;
import com.yupi.buffoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

public class JudgeServiceImpl implements JudgeService {

    @Value("${codeSandbox.type:Example}")
    private String type;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private QuestionService questionService;

    @Resource
    private CodeSandboxFactory codeSandboxFactory;

    @Resource
    private CodeSandboxProxy codeSandboxProxy;

    @Override
    public QuestionSubmitVO doJudge(long id) {
        QuestionSubmit questionSubmit = questionSubmitService.getById(id);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息为空");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目为空");
        }
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        questionSubmit.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmit);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新失败");
        }
        CodeSandbox codeSandbox = codeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> intputList = judgeCaseList.stream().map(judgeCase -> judgeCase.getInput()).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = new ExecuteCodeRequest().builder().language(questionSubmit.getLanguage()).code(questionSubmit.getCode()).inputList(intputList).build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.SYSTEM_ERROR;
        if (outputList.size() != intputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        for (int i = 0; i < judgeCaseList.size(); i++) {
            if (!judgeCaseList.get(i).getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                return null;
            }
        }
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();

        Long memoryLimit = judgeInfo.getMemoryLimit();
        Long timeLimit = judgeInfo.getTimeLimit();


        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);

        Long needTimeLimit = judgeConfig.getTimeLimit();
        Long needMemoryLimit = judgeConfig.getMemoryLimit();

        if (memoryLimit > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if (timeLimit > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_LIMIT_EXCEEDED;
            return null;
        }
        return null;
    }
}
