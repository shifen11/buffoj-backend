package com.yupi.buffoj.judge.codesandbox;

import com.yupi.buffoj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.buffoj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.buffoj.model.enums.QuestionsubmitLanguageEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class CodesandboxTest {

   @Value("${codeSandbox.type:Remote}")
   private String type;


    @Test
    void executeCode() {
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox=new CodeSandboxProxy(codeSandbox);

        String code = "public class Example {\n" +
                "    public static void main(String[] args) {\n" +
                "        System.out.println(\"Hello, world!\");\n" +
                "    }\n" +
                "}";
        String language = QuestionsubmitLanguageEnum.JAVA.getValue();
        List<String> inputs = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest request = new ExecuteCodeRequest().builder()
                .language(language)
                .code(code)
                .inputList(inputs)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String type = scanner.next();
            CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
            String code = "public class Example {\n" +
                    "    public static void main(String[] args) {\n" +
                    "        System.out.println(\"Hello, world!\");\n" +
                    "    }\n" +
                    "}";
            String language = QuestionsubmitLanguageEnum.JAVA.getValue();
            List<String> inputs = Arrays.asList("1 2", "3 4");
            ExecuteCodeRequest request = new ExecuteCodeRequest().builder()
                    .language(language)
                    .code(code)
                    .inputList(inputs)
                    .build();
            ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(request);
        }
    }

}