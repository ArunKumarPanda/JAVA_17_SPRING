package com.adobe.prj.aop;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class LogAspect {
	Logger logger = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.adobe.prj.service.*.*(..))")
	public void logBefore(JoinPoint jp) {
		logger.info("Called :" + jp.getSignature());
		Object[] args = jp.getArgs();
		for(Object arg : args) {
			logger.info("Argument :" + arg);
		}
	}
	
	
	@After("execution(* com.adobe.prj.service.*.*(..))")
	public void logAfter(JoinPoint jp) {
		logger.info("***********" );
	}
	

	@Around("execution(* com.adobe.prj.service.*.*(..))")
	public Object profile(ProceedingJoinPoint jp) throws Throwable {
		long startTime = new Date().getTime(); 
			Object ret = jp.proceed(); // actual method called
		long endTime = new Date().getTime();
		logger.info("Time : " + (endTime - startTime) + " ms");
		return ret;
	}

	// not aware of Request, Response
	@AfterThrowing(value = "execution(* com.adobe.prj.service.*.*(..))", throwing = "ex")
	public void logException(Exception ex) {
		logger.info("Exception : " + ex.getMessage());
	}
}
