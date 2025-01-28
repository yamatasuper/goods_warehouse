package com.goods.product.task1.logs;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeMeasurementAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(TimeMeasurementAspect.class);

    @Autowired
    private FileLogger fileLogger;

    @Around("@annotation(TimeMeasured)")
    public Object measureTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            String logMessage = String.format("Execution time of %s: %d ms", joinPoint.getSignature(), executionTime);

            LOGGER.info(logMessage);

            fileLogger.logToFile("method_execution_time.log", logMessage);
        }
    }
}