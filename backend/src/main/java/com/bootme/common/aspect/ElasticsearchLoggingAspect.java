package com.bootme.common.aspect;

//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StopWatch;
//
//import java.util.Arrays;
//import java.util.Optional;
//
//@Aspect
//@Component
//@Slf4j
//public class ElasticsearchLoggingAspect {
//
//    @Around("execution(* com.bootme.post.repository.PostElasticsearchRepository.*(..))")
//    public Object logRepositoryMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        Object result = null;
//        try {
//            result = joinPoint.proceed();
//            return result;
//        } finally {
//            stopWatch.stop();
//            String logMessage = String.format(
//                    "| %d ms | %s",
//                    stopWatch.getTotalTimeMillis(),
//                    getCallingMethodDetails()
//            );
//            log.info(logMessage);
//        }
//    }
//
//    private String getCallingMethodDetails() {
//        Optional<StackTraceElement> stackTraceElement = Arrays.stream(Thread.currentThread().getStackTrace())
//                .filter(e -> e.getClassName().startsWith("com.bootme") && e.getClassName().endsWith("Service"))
//                .findFirst();
//
//        return stackTraceElement.map(StackTraceElement::toString).orElse("Unknown method");
//    }
//
//}
