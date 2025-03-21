package com.yupi.buffoj.model.enums;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色枚举
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum JudgeInfoMessageEnum {

    ACCEPT("成功", "Accepted"),
    WRONG_ANSWER("答案错误", "Wrong_Answer"),
    SYSTEM_ERROR("System Error", "系统错误"),
    TIME_LIMIT_EXCEEDED("超出时间限制", "Time_Limit_Exceeded"),
    MEMORY_LIMIT_EXCEEDED("超出内存限制", "Memory_Limit_Exceeded"),
    PRESENTATION_ERROR("Presentation Error", "格式错误"),
    OUTPUT_LIMIT_EXCEEDED("输出超限", "Output_Limit_Exceeded"),
    RUNTIME_ERROR("运行时错误", "Runtime_Error"),
    COMPILE_ERROR("编译错误", "Compile_Error"),
    IDLENESS_LIMIT_EXCEEDED("程序不输出", "Idleness_Limit_Exceeded");

    private final String text;

    private final String value;

    JudgeInfoMessageEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static JudgeInfoMessageEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (JudgeInfoMessageEnum anEnum : JudgeInfoMessageEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
