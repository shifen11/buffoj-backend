package com.yupi.buffoj.model.dto.question;

import lombok.Data;

/**
 * 题目配置
 */
@Data
public class JudgeConfig {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（MB）
     */
    private Long memoryLimit;

    /**
     * 堆栈（KB）
     */
    private Long stackLimit;

}
