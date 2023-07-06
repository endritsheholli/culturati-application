package com.iotiq.application.wiki;

import com.iotiq.application.wiki.annotation.WikiAuthenticatedRequest;
import com.iotiq.application.wiki.domain.ItemDto;
import com.iotiq.application.wiki.messages.ItemCreateResponse;
import com.iotiq.application.wiki.messages.ItemFilter;
import com.iotiq.application.wiki.messages.ItemRequest;
import com.iotiq.application.wiki.messages.ResponseResult;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
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
            @Value("http://localhost/graphql/") String url
    ) {
        this.authService = wikiAuthService;

        WebClient webClient = WebClient.builder().baseUrl(url).build();
        graphQlClient = HttpGraphQlClient.builder(webClient).build();
    }

    @Override
    public Collection<ItemDto> getItems(ItemFilter filter) {
        return graphQlClient
                .mutate()
                .header("Authorization", authService.getAccessToken())
                .build()
                .document("getPages")
                .retrieve("pages.list")
                .toEntityList(ItemDto.class)
                .block();
    }

    @Override
    public Optional<ItemDto> getItem(@NotNull Integer id) {
        return graphQlClient
                .document("getPage")
                .variable("id", id)
                .retrieve("pages.single")
                .toEntity(ItemDto.class)
                .blockOptional();
    }

    @Override
    @WikiAuthenticatedRequest
    public ItemCreateResponse createItem(ItemRequest request) {
        String accessToken = authService.getAccessToken();
        return graphQlClient
                .mutate()
                .header("Authorization", accessToken)
                .build()
                .document("createPage")
                .variable("path", request.path())
                .retrieve("pages.create")
                .toEntity(ItemCreateResponse.class)
                .block();
    }

    @Override
    @WikiAuthenticatedRequest
    public ResponseResult deleteItem(String id) {
        return graphQlClient
                .document("deletePage")
                .retrieve("pages.delete.responseResult")
                .toEntity(ResponseResult.class)
                .block();
    }
}
