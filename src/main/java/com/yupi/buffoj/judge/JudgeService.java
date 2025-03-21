package com.yupi.buffoj.judge;

import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.buffoj.model.vo.QuestionSubmitVO;

public interface JudgeService {
  public QuestionSubmitVO doJudge(long id);
}
