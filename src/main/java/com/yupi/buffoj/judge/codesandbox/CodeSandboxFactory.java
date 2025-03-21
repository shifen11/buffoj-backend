package com.yupi.buffoj.judge.codesandbox;

import com.yupi.buffoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.buffoj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.yupi.buffoj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

public class CodeSandboxFactory {
    public static CodeSandbox newInstance(String type) {
   switch (type) {
       case "Example":
           return new ExampleCodeSandbox();
           case "Remote":
               return new RemoteCodeSandbox();
       case"ThirdParty":
           return new ThirdPartyCodeSandbox();
       default:
           return new ExampleCodeSandbox();
   }
    }

}
