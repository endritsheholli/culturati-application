package com.iotiq.application.wiki;

import com.iotiq.application.wiki.domain.WikiAuth;
import com.iotiq.application.wiki.exception.AuthenticationException;
import com.iotiq.application.wiki.exception.RefreshTokenExpiredException;
import com.iotiq.application.wiki.exception.WikiException;
import com.iotiq.application.wiki.messages.WikiAuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.http.HttpStatus;
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
        if (auth == null) {
            authenticate();
        } else if (auth.isAccessTokenExpired()) {
            try {
                refresh();
            } catch (RefreshTokenExpiredException | WikiException ex) {
                authenticate();
            }
        }
    }

    @Override
    public String getAccessToken() {
        return "Bearer " + auth.getAuthToken();
    }

    private void refresh() {
        // send refresh request
        if (auth == null || auth.isRefreshTokenExpired()) {
            throw new RefreshTokenExpiredException();
        }
        String host = "api.co-twin.com";
        WikiAuthResponse authResponse = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host(host)
                        .pathSegment("auth")
                        .pathSegment("refresh")
                        .queryParam("refreshToken", auth.getRefreshToken())
                        .build())
                .retrieve()
                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new RefreshTokenExpiredException()))
                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new WikiException()))
                .bodyToMono(WikiAuthResponse.class)
                .onErrorResume(this::logAndMoveOn)
                .block();

        if (authResponse == null) {
            logger.error("refresh response was null");
            throw new WikiException();
        }
        auth.refreshWith(authResponse);
    }

    private void authenticate() {
        String host = "http://localhost/graphql/";

        //        language=GraphQL
        String query = """
                mutation Authentication {
                    authentication {
                        login(username: "inci@iotiq.net", password: "admin123", strategy: "local") {
                            responseResult {
                                succeeded
                                errorCode
                                slug
                                message
                            }
                            jwt
                            mustChangePwd
                            mustProvideTFA
                            mustSetupTFA
                            continuationToken
                            redirect
                            tfaQRImage
                        }
                    }
                }
                """;

        WikiAuthResponse authResponse = graphQlClient.mutate()
                .build()
                .document(query)
//                .variable("path", request.path())
                .retrieve("authentication.login")
                .toEntity(WikiAuthResponse.class)
                .block();

//        WikiAuthResponse authResponse = webClient.post()
//                .uri(uriBuilder -> uriBuilder
//                        .scheme("https")
//                        .host(host)
//                        .pathSegment("auth")
//                        .pathSegment("login")
//                        .queryParam("password", "admin123")
//                        .queryParam("username", "inci@iotiq.net")
//                        .build())
//                .retrieve()
//                .onStatus(status -> HttpStatus.UNAUTHORIZED == status, response -> Mono.error(new AuthenticationException()))
//                .onStatus(status -> HttpStatus.BAD_REQUEST == status, response -> Mono.error(new WikiException()))
//                .bodyToMono(WikiAuthResponse.class)
//                .onErrorResume(this::logAndMoveOn)
//                .block();

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
