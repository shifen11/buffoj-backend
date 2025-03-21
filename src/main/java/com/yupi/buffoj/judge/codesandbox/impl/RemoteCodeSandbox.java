package com.yupi.buffoj.judge.codesandbox.impl;

import com.yupi.buffoj.judge.codesandbox.CodeSandbox;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱
 */
public class RemoteCodeSandbox implements CodeSandbox {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        return new ExecuteCodeResponse();
    }
}
