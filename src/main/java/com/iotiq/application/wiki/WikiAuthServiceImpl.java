package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.WikiAuth;
import com.iotiq.application.wiki.exception.AuthenticationException;
import com.iotiq.application.wiki.exception.WikiNoResponseException;
import com.iotiq.application.wiki.messages.WikiAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WikiAuthServiceImpl implements WikiAuthService {

    private WikiAuth auth;
    private final WikiAuthConfig config;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpGraphQlClient graphQlClient;

    public WikiAuthServiceImpl(WikiAuthConfig config) {
        this.config = config;

        WebClient webClient = WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .build();
        this.graphQlClient = HttpGraphQlClient.builder(webClient).build();
    }

    /**
     * <a href="https://whimsical.com/sending-requests-to-cotwin-api-7VocHj9v5rDSKKfbpvv8hf">authentication flowchart</a>
     */
    @Override
    public void preRequest() throws AuthenticationException {
        if (auth == null || auth.isAccessTokenExpired()) {
            authenticate();
        }
    }

    @Override
    public String getAccessToken() {
        return "Bearer " + auth.getAuthToken();
    }

    private void authenticate() {
        WikiAuthResponse authResponse = graphQlClient.mutate()
                .build()
                .documentName("authenticate")
                .variable("username", config.getUsername())
                .variable("password", config.getPassword())
                .variable("strategy", config.getStrategy())
                .retrieve("authentication.login")
                .toEntity(WikiAuthResponse.class)
//                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new AuthenticationException()))
//                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new WikiException()))
                .onErrorResume(this::logAndMoveOn)
                .block();

        if (authResponse == null) {
            logger.error("auth response was null");
            throw new WikiNoResponseException();
        }
        auth = WikiAuth.from(authResponse);
    }

    private Mono<? extends WikiAuthResponse> logAndMoveOn(Throwable throwable) {
        logger.error(throwable.getMessage());
        return Mono.empty();
    }
}
