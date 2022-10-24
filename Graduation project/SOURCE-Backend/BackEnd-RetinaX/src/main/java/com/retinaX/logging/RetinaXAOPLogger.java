package com.retinaX.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class RetinaXAOPLogger {

    @Pointcut
    @Before(value = "annotation(com.retinaX.logging.RetinaXLog)")
    public void log(){

    }
}
