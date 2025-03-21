package com.yupi.buffoj.judge.codesandbox.impl;

import com.yupi.buffoj.judge.codesandbox.CodeSandbox;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方代码沙箱");
        return new ExecuteCodeResponse();
    }
}
