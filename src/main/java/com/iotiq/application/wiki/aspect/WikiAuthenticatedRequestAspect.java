package com.iotiq.application.wiki.aspect;

import com.iotiq.application.wiki.WikiAuthService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class WikiAuthenticatedRequestAspect {

    private final WikiAuthService wikiAuthService;

    @Pointcut("@annotation(com.iotiq.application.wiki.annotation.WikiAuthenticatedRequest)")
    public void outgoingRequest() {
    }

    @Before("outgoingRequest()")
    public void preRequestAuthCheck() {
        wikiAuthService.preRequest();
    }
}
