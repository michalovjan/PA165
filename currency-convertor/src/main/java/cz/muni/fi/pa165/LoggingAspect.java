package cz.muni.fi.pa165;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Named
@Aspect
public class LoggingAspect {

    @Around("execution(public * *(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        System.err.println("Calling method: "
                + joinPoint.getSignature());
        Long time = System.nanoTime();

        Object result = joinPoint.proceed();

        System.err.println("Method finished: "
                + joinPoint.getSignature() + " elapsed time: " + (System.nanoTime() - time) + "ns");

        return result;
    }

}

