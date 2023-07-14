package com.iotiq.application.wiki;

import com.iotiq.application.wiki.annotation.WikiAuthenticatedRequest;
import com.iotiq.application.wiki.domain.PageDto;
import com.iotiq.application.wiki.messages.PageCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.PageCreateRequest;
import com.iotiq.application.wiki.messages.ResponseResult;
import jakarta.validation.constraints.NotNull;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collection;
import java.util.Optional;

@Service
public class WikiClientImpl implements WikiClient {

    private final WikiAuthService authService;
    private final HttpGraphQlClient graphQlClient;

    public WikiClientImpl(
            WikiAuthService wikiAuthService,
            WikiAuthConfig wikiAuthConfig
    ) {
        this.authService = wikiAuthService;

        WebClient webClient = WebClient.builder().baseUrl(wikiAuthConfig.getBaseUrl()).build();
        graphQlClient = HttpGraphQlClient.builder(webClient).build();
    }

    @Override
    public Collection<PageDto> getPages(ItemFilter filter) {
        return graphQlClient
                .documentName("getPages")
                .retrieve("pages.list")
                .toEntityList(PageDto.class)
                .block();
    }

    @Override
    public Optional<PageDto> getPage(@NotNull Integer id) {
        return graphQlClient
                .documentName("getPage")
                .variable("id", id)
                .retrieve("pages.single")
                .toEntity(PageDto.class)
                .blockOptional();
    }

    @Override
    @WikiAuthenticatedRequest
    public PageCreateResponse createPage(PageCreateRequest request) {
        String accessToken = authService.getAccessToken();
        return graphQlClient
                .mutate()
                .header("Authorization", accessToken)
                .build()
                .documentName("createPage")
                .variable("path", request.path())
                .retrieve("pages.create")
                .toEntity(PageCreateResponse.class)
                .block();
    }

    @Override
    @WikiAuthenticatedRequest
    public ResponseResult deletePage(String id) {
        String accessToken = authService.getAccessToken();
        return graphQlClient
                .mutate()
                .header("Authorization", accessToken)
                .build()
                .documentName("deletePage")
                .retrieve("pages.delete.responseResult")
                .toEntity(ResponseResult.class)
                .block();
    }
}
