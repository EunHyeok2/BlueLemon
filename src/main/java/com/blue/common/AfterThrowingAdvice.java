package com.blue.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AfterThrowingAdvice {
	
	@AfterThrowing(pointcut = "PointcutCommon.allPointcut()", throwing = "exceptObj")
	public void exceptionLog(JoinPoint jp, Exception exceptObj) {
		
		String methodName = jp.getSignature().getName();
		
		System.out.println("[ AfterThrowing   ]  " + methodName + "() �޼��� ���� �� ���� �߻� : " + exceptObj.getMessage());
	}
}
