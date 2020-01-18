package com.goduke.test;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.goduke.function.answer.GetAnswerHandler;
import com.goduke.function.answer.GetAnswersHandler;
import com.goduke.function.test.GetRecruiterTests;
import com.goduke.function.test.GetTestsHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.goduke.model.answer.Answer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetTests {

    public Context createContext(){
        return new Context() {
            @Override
            public String getAwsRequestId() {
                return null;
            }

            @Override
            public String getLogGroupName() {
                return null;
            }

            @Override
            public String getLogStreamName() {
                return null;
            }

            @Override
            public String getFunctionName() {
                return null;
            }

            @Override
            public String getFunctionVersion() {
                return null;
            }

            @Override
            public String getInvokedFunctionArn() {
                return null;
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return null;
            }
        };
    }
    @Test
    public void testGetTests() {
        GetTestsHandler handler = new GetTestsHandler();
        Context ctx = createContext();
        List<com.goduke.model.test.Test> tests = handler.handleRequest(new com.goduke.model.test.Test(), ctx);
        assertTrue(tests.size() > 0);

    }
    @Test
    public void testGetRecruiterTests() {
        GetRecruiterTests getRecruiterTests = new GetRecruiterTests();
        Context ctx = createContext();
        List<com.goduke.model.test.Test> tests = getRecruiterTests.handleRequest("admin@example.com", ctx);
        assertTrue(tests.size() > 0);

    }
    @Test
    public void testGetAnswer() {
        GetAnswersHandler getAnswersHandler = new GetAnswersHandler();
        Context ctx = createContext();
        List<Answer> answers = getAnswersHandler.handleRequest(new Answer(), ctx);
        GetAnswerHandler getAnswerHandler = new GetAnswerHandler();
        Answer answer = getAnswerHandler.handleRequest(answers.get(0), ctx);
        assertTrue(answer != null);

    }
}
