package com.blue.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
public class Log4jAdvice {
    private static final Logger logger = LoggerFactory.getLogger(Log4jAdvice.class);

    public void printLog4j(String methodName) {
        logger.warn("[ Log4j Advice ]  " + methodName + " �������� �������� ������ �߻��� ���ɼ��� ����");
    }
}
