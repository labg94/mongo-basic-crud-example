package com.cleveritgroup.mongoexample.config;

import lombok.Generated;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

@Aspect
@Slf4j
@Configuration
public class LoggerAspect {

    @Generated
    @Pointcut("execution(* com.cleveritgroup.mongoexample.repository.*.*(..))")
    public void repositoryLogs() {

    }

    @Generated
    @Pointcut("execution(* com.cleveritgroup.mongoexample.service.*.*(..))")
    public void servicesLogs() {
    }

    @Generated
    @Pointcut("execution(* com.cleveritgroup.mongoexample.controller.*.*(..))")
    public void controllerLogs() {

    }

    @Pointcut("execution(* com.cleveritgroup.mongoexample.exception.ErrorController.*(..))")
    public void errorHandlerLogs() {
    }

    @AfterReturning(value = "repositoryLogs()",
            returning = "result")
    public void afterReturningData(JoinPoint joinPoint, Object result) {

        log.info("{} with args {} returned {}",
                joinPoint.getSignature(),
                joinPoint.getArgs()
                , result);

    }


    @Before("servicesLogs()")
    public void beforeService(JoinPoint joinPoint) {

        log.info("INIT - {} with parameters {}",
                joinPoint.getSignature(),
                joinPoint.getArgs());

    }

    @AfterReturning(value = "servicesLogs()",
            returning = "result")
    public void afterReturningServices(JoinPoint joinPoint, Object result) {

        log.info("END - {} returned with values {}", joinPoint
                , result);

    }

    @AfterThrowing(value = "servicesLogs()", throwing = "ex")
    public void afterThrowingServices(JoinPoint joinPoint, Exception ex) {

        log.info("END with exception - {} error message {}", joinPoint.getSignature()
                , ex.getMessage());
    }


    @Before("controllerLogs()")
    public void beforeController(JoinPoint joinPoint) {
        log.info("INIT - {} with request {}", joinPoint.getSignature(), joinPoint.getArgs());
    }

    @AfterReturning(value = "controllerLogs()", returning = "response")
    public void afterReturnController(JoinPoint joinPoint, Object response) {
        log.info("END - {} returning => {}", joinPoint.getSignature(), response);
    }

    @AfterThrowing(value = "controllerLogs()", throwing = "ex")
    public void afterReturnController(JoinPoint joinPoint, Exception ex) {
        log.info("END with exception - {} error message => {}", joinPoint.getSignature(), ex.getMessage());
    }


    @Before(value = "errorHandlerLogs()")
    public void beforeHandlingException(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Exception exc = (Exception) args[0];
        log.info("Handling exception => {}  with message {} ", exc.getClass(), exc.getMessage());
    }

}