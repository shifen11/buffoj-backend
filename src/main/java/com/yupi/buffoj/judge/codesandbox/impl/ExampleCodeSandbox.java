package com.yupi.buffoj.judge.codesandbox.impl;
import java.util.List;

import com.yupi.buffoj.judge.codesandbox.CodeSandbox;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.buffoj.model.dto.questionSubmit.JudgeInfo;
import com.yupi.buffoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.buffoj.model.enums.QuestionSubmitStatusEnum;

/**
 * 示例代码沙箱
 */
public class ExampleCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
     List<String> inputList = executeCodeRequest.getInputList();

        ExecuteCodeResponse executeCodeResponse = ExecuteCodeResponse.builder()
        		.status(QuestionSubmitStatusEnum.SUCCEED.getValue())
        		.message("succeed")
        		.outputList(inputList)
        		.judgeInfo(new JudgeInfo())
        		.build();
        return executeCodeResponse;

    }
}
