package com.blue.common;

import org.aspectj.lang.JoinPoint;
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
		
		// ��� Ÿ���� ������ �޾Ƴ��� ���� ������Ʈ �迭 ���� Object[] ���
		Object[] args = pjp.getArgs();
		
		//StopWatch stopWatch = new StopWatch();
		
		System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ���� ��");
		
		//stopWatch.start();
		
		// ����Ͻ� �޼��� (insertBoard, updateBoard ...) ȣ���� �� ���� ���� returnObj�� ����ش�.
		Object returnObj = pjp.proceed();
		//stopWatch.stop();
		
		if (args.length == 0) {
			System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ARGS : ����");
		} else {
			System.out.println("[ Around - Before ]  " + methodName + "() �޼��� ARGS : " + returnObj.toString());
		}		
		//System.out.println("[ Around - After ]  " + methodName + "() �޼��� ���࿡ �ɸ� �ð� : " + stopWatch.getTotalTimeMillis());
		System.out.println("[ Around - After ]  " + methodName + "() �޼��� ���� �Ϸ�");
		
		return returnObj;
	}
}
