package com.yupi.buffoj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.buffoj.common.ErrorCode;
import com.yupi.buffoj.constant.CommonConstant;
import com.yupi.buffoj.exception.BusinessException;
import com.yupi.buffoj.mapper.QuestionSubmitMapper;
import com.yupi.buffoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.yupi.buffoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.yupi.buffoj.model.entity.Question;
import com.yupi.buffoj.model.entity.QuestionSubmit;
import com.yupi.buffoj.model.entity.User;
import com.yupi.buffoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.buffoj.model.enums.QuestionsubmitLanguageEnum;
import com.yupi.buffoj.model.vo.QuestionSubmitVO;
import com.yupi.buffoj.service.QuestionService;
import com.yupi.buffoj.service.QuestionSubmitService;
import com.yupi.buffoj.utils.SqlUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lenovo
 * @description 针对表【question_submit(题目提交)】的数据库操作Service实现
 * @createDate 2025-03-16 05:19:03
 */
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
        implements QuestionSubmitService {

    @Resource
    private QuestionService questionService;

    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        String language = questionSubmitAddRequest.getLanguage();
        QuestionsubmitLanguageEnum languageEnum = QuestionsubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编程语言错误");
        }
        // 判断实体是否存在，根据类别获取实体
        Long questionId = questionSubmitAddRequest.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(userId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);
        //初始化状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        return questionSubmit.getId();
    }

    /**
     * 获取查询包装类
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    @Override
    public QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest) {

        QueryWrapper<QuestionSubmit> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryRequest == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryRequest.getLanguage();
        Integer status = questionSubmitQueryRequest.getStatus();
        Long questionId = questionSubmitQueryRequest.getQuestionId();
        String sortField = questionSubmitQueryRequest.getSortField();
        String sortOrder = questionSubmitQueryRequest.getSortOrder();
        Long userId = questionSubmitQueryRequest.getUserId();
        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(status), "status", status);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "questionId", questionId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public Page<QuestionSubmitVO> getQuestionVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser) {
        List<QuestionSubmit> records = questionSubmitPage.getRecords();
        List<QuestionSubmitVO> voList = new ArrayList<>();
        for (QuestionSubmit record : records) {
            QuestionSubmitVO questionSubmitVO = QuestionSubmitVO.objToVo(record);
            if (Objects.equals(questionSubmitVO.getUserId(), loginUser.getId()) || "admin".equals(loginUser.getUserRole())) {
                voList.add(questionSubmitVO);
            }
        }
        Page<QuestionSubmitVO> voPage = new Page<>();
        voPage.setCurrent(questionSubmitPage.getCurrent());
        voPage.setSize(questionSubmitPage.getSize());
        voPage.setRecords(voList);
        voPage.setTotal(voList.size());
        return voPage;
    }

}




