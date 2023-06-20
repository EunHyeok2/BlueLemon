package com.blue.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AfterReturningAdvice {
	
	@AfterReturning(pointcut = "PointcutCommon.getPointcut()", returning = "returnObj")
	public void afterReturningLog(JoinPoint jp, Object returnObj) {
		
		String methodName = jp.getSignature().getName();
		
		System.out.println("[ AfterReturning  ]  " + methodName + "() �޼��� ���� �� : " + returnObj.toString());
	}
}
