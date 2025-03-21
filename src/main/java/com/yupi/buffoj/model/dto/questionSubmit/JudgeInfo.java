package com.yupi.buffoj.model.dto.questionSubmit;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeInfo {

    /**
     * 程序执行信息
     */
    private String message;

    /**
     * 消耗内存
     */
    private Long memoryLimit;

    /**
     * 消耗时间
     */
    private Long timeLimit;

}
