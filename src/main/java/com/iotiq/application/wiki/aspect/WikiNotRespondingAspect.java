package com.iotiq.application.wiki.aspect;

import com.iotiq.application.wiki.exception.WikiNoResponseException;
import org.springframework.graphql.client.GraphQlTransportException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class WikiNotRespondingAspect {

    @Around("@annotation(com.iotiq.application.wiki.annotation.WikiNotResponding)")
    public Object handleException(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (GraphQlTransportException ex) {
            throw new WikiNoResponseException();
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
