package com.example.taskmanager.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Before("@annotation(com.example.taskmanager.aspect.annotation.Loggable)")
    public void logMethodCall(JoinPoint joinPoint) {
        logger.info("Вызов метода: {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(
            pointcut = "@annotation(com.example.taskmanager.aspect.annotation.ExceptionHandling)",
            throwing = "ex"
    )
    public void logCreationError(Exception ex) {
        logger.error("Ошибка при создании задачи: {}", ex.getMessage());
    }

    @AfterReturning(
            pointcut = "@annotation(com.example.taskmanager.aspect.annotation.ResultHandling)",
            returning = "task"
    )
    public void logCreationSuccess(Object task) {
        logger.info("Результат: {}", task);
    }

    @Around("@annotation(com.example.taskmanager.aspect.annotation.LogTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        logger.info("Метод getAllTasks выполнен за {} мс", end - start);
        return result;
    }
}