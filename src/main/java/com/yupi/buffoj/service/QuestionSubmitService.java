package com.yupi.buffoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.buffoj.model.dto.question.QuestionQueryRequest;
import com.yupi.buffoj.model.dto.questionSubmit.QuestionSubmitAddRequest;
import com.yupi.buffoj.model.dto.questionSubmit.QuestionSubmitQueryRequest;
import com.yupi.buffoj.model.entity.Question;
import com.yupi.buffoj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.buffoj.model.entity.User;
import com.yupi.buffoj.model.vo.QuestionSubmitVO;

/**
* @author lenovo
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-03-16 05:19:03
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {

    /**
     * 题目提交

     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    Page<QuestionSubmitVO> getQuestionVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
