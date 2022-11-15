package spring.aop.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import spring.aop.entity.NewlecExam;

@Component
@Aspect
public class AnnoAspect {
//    @Before("execution(* spring.aop.entity.NewlecExam.*(..))")
    @Before("execution(* *(..))")
    public void before(JoinPoint joinPoint) {
        System.out.println("--------------- @Before ---------------");
        System.out.println(joinPoint.toString());
        System.out.println(joinPoint.toShortString());
        System.out.println(joinPoint.toLongString());
        System.out.println("시작전");
    }
    @Pointcut("execution(* *(..))")
//    @Pointcut("bean(exam)")
    public void cut() {
        System.out.println("!!!!");
    }

    @Around("execution(* *(..))")
    public Object Around(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        String message = (end - start) + "ms 시간이 걸렸습니다.";

        System.out.println(message);
        return result;
    }

}
