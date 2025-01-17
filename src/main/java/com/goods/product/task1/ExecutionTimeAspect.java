package com.goods.product.task1;

import com.goods.product.task1.ExecutionTime;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ExecutionTimeAspect {

    @Before("@annotation(executionTime)")
    public void before(ExecutionTime executionTime) {
        long start = System.currentTimeMillis();
        System.out.println("Start time: " + start);
    }

    @After("@annotation(executionTime)")
    public void after(ExecutionTime executionTime) {
        long end = System.currentTimeMillis();
        System.out.println("End time: " + end);
    }
}
