package com.yupi.buffoj.model.dto.questionSubmit;

import com.yupi.buffoj.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 题目查询请求
 */
@Data
public class QuestionSubmitQueryRequest extends PageRequest implements Serializable {


    private static final long serialVersionUID = 1L;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;
    /**
     * 创建用户 id
     */
    private Long userId;
}