package kr.co.wjin.common;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.util.StopWatch;

public class StopWatchAdvice {
	
	public Object methodEstimate(ProceedingJoinPoint pp) throws Throwable {
		String methodName = pp.getSignature().getName();
		StopWatch stopWatch = new StopWatch();
		// start
		stopWatch.start();
		Object obj = pp.proceed();
		// stop
		stopWatch.stop();
		System.out.println(methodName + "() 메소드 수행에 걸린 시간 : " + stopWatch.getTotalTimeMillis() + "(ms)");
		return obj;
	}
}
