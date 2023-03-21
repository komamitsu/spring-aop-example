package org.komamitsu.springtest.aop;

import java.time.Duration;
import java.time.Instant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect {
  @Around("execution(* *WithTrace(..))")
  Object traceMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant start = Instant.now();
    Object result = joinPoint.proceed();
    Duration duration = Duration.between(start, Instant.now());
    System.out.printf("[TRACE] %s took %d ms\n", joinPoint.toLongString(), duration.toMillis());
    return result;
  }
}
