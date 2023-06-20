package com.blue.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

@Service
@Aspect
public class AroundAdvice {
	
	@Around("PointcutCommon.allPointcut()")
	public Object aroundLog(ProceedingJoinPoint pjp) throws Throwable {
		
		String methodName = pjp.getSignature().getName();		
		StopWatch stopWatch = new StopWatch();		
		// ��� Ÿ���� ������ �޾Ƴ��� ���� ������Ʈ �迭 ���� Object[] ���
		Object[] args = pjp.getArgs();
		
		System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ���� ��");
		
		stopWatch.start();
		Object returnObj = pjp.proceed();
		stopWatch.stop();
		
		if (args.length == 0) {
			System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ���ϰ� : ����");
		} else {
			System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ���ϰ� : ����");
		}		
		System.out.println("[ Around - After  ]  " + methodName + "() �޼��� ���� �ð� : " + stopWatch.getTotalTimeMillis());
		System.out.println("[ Around - After  ]  " + methodName + "() �޼��� ���� �Ϸ�");
		
		return returnObj;
	}
}
