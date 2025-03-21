package com.yupi.buffoj.judge.codesandbox.model;

import com.yupi.buffoj.model.dto.questionSubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExecuteCodeResponse {

    private Integer status;

    private String message;

    private List<String> outputList;

    private JudgeInfo judgeInfo;
}
