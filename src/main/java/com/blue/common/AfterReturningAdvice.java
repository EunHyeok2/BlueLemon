package com.blue.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Service;

@Service
@Aspect
public class AfterReturningAdvice {
	
//	@Pointcut("execution(* com.ezen.biz..*Impl.get*(..))")
//	public void getPointcut() { }
	
	@AfterReturning(pointcut = "PointcutCommon.getPointcut()", returning = "returnObj")
	public void afterReturningLog(JoinPoint jp, Object returnObj) {
		
		// �޼��� ��
		String methodName = jp.getSignature().getName();
		
		System.out.printf("[ AfterReturning ]  �޼��� �� : %s(), ���� �� : %s\n", methodName, returnObj.toString());
	}
}
