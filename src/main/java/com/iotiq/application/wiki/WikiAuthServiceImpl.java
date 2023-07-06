package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.WikiAuth;
import com.iotiq.application.wiki.exception.AuthenticationException;
import com.iotiq.application.wiki.exception.WikiException;
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

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final WebClient webClient = WebClient.builder()
            .baseUrl("http://localhost/graphql/")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    private final HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder(webClient).build();

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
        String username = "inci@iotiq.net";
        String password = "admin123";
        String strategy = "local";

        WikiAuthResponse authResponse = graphQlClient.mutate()
                .build()
                .documentName("authenticate")
                .variable("username", username)
                .variable("password", password)
                .variable("strategy", strategy)
                .retrieve("authentication.login")
                .toEntity(WikiAuthResponse.class)
//                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new AuthenticationException()))
//                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new WikiException()))
                .onErrorResume(this::logAndMoveOn)
                .block();

        if (authResponse == null) {
            logger.error("auth response was null");
            throw new WikiException();
        }
        auth = WikiAuth.from(authResponse);
    }

    private Mono<? extends WikiAuthResponse> logAndMoveOn(Throwable throwable) {
        logger.error(throwable.getMessage());
        return Mono.empty();
    }
}
