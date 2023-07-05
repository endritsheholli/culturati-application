package com.iotiq.application.wiki;

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

    private final HttpGraphQlClient graphQlClient;

    public WikiClientImpl(@Value("http://localhost/graphql/") String url) {
        WebClient webClient = WebClient.builder().baseUrl(url).build();
        graphQlClient = HttpGraphQlClient.builder(webClient)
                .header("Authorization", "Bearer " + "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiZW1haWwiOiJpbmNpQGlvdGlxLm5ldCIsIm5hbWUiOiJBZG1pbmlzdHJhdG9yIiwiYXYiOm51bGwsInR6IjoiQW1lcmljYS9OZXdfWW9yayIsImxjIjoiZW4iLCJkZiI6IiIsImFwIjoiIiwicGVybWlzc2lvbnMiOlsibWFuYWdlOnN5c3RlbSJdLCJncm91cHMiOlsxXSwiaWF0IjoxNjg4NDc2NzAyLCJleHAiOjE2ODg0Nzg1MDIsImF1ZCI6InVybjp3aWtpLmpzIiwiaXNzIjoidXJuOndpa2kuanMifQ.Mp03Tfe2weamzBGOeyKTA2GjIE1Ekum_3SDohkcSGfmk2s1FHRLS905THTocJ8sFBjjTnfNH4wuQAJt6O_bwgYrp0XCfMGBmGSabvk-ukD86jiq8CYrmvXAGjgNi7OXcgvr0OEjmiePcVQdkdXmFC6EBSNKHpCelKCYd0aYhr9F41q1-1vD7PcqxfBXvKC1qsfKEtzcrG_OoMMxU_ZWkTWoDtQYV6yN3tLJb14Q0FDdWZHeTqW07pldnAdf40jrLxv9_uzwgQ0NulqKKOFfohlPLIp4Y2HXwEZQD_3eP3T1YAR4VHR3mVEJbPZskf-bCk8M_CIXsIDWH8UCaoMkiaw")
                .build();
    }

    @Override
    public Collection<ItemDto> getItems(ItemFilter filter) {
//        language=GraphQL
        String query = """
                query {
                  pages {
                    list(orderBy: TITLE) {
                      id
                      path
                      title
                    }
                  }
                }
                """;

        return graphQlClient.document(query).retrieve("pages.list").toEntityList(ItemDto.class).block();
    }

    @Override
    public Optional<ItemDto> getItem(@NotNull Integer id) {
//        language=GraphQL
        String query = """
                query($id: Int!) {
                  pages {
                    single (id: $id) {
                      path
                      title
                      createdAt
                      updatedAt
                    }
                  }
                }
                 """;

        return graphQlClient.document(query).variable("id", id)
                .retrieve("pages.single").toEntity(ItemDto.class).blockOptional();
    }

    @Override
    public ItemCreateResponse createItem(ItemRequest request) {
//        language=GraphQL
        String query = """
                mutation($path: String!) {
                     pages {
                         create(
                             content: "some content"
                             description: "some description"
                             editor: "ckeditor"
                             isPublished: false
                             isPrivate: true
                             locale: "en"
                             path: $path
                             title: "some-title"
                             tags: ["tag1"]
                         ) {
                             responseResult {
                                 succeeded
                                 errorCode
                                 slug
                                 message
                             }
                             page {
                                 id
                                 path
                                 title
                             }
                         }
                     }
                 }
                """;

        return graphQlClient.document(query).variable("path", request.path())
                .retrieve("pages.create").toEntity(ItemCreateResponse.class).block();
    }

    @Override
    public ResponseResult deleteItem(String id) {
//        language=GraphQL
        String query = """
                query($id: Int!) {
                  pages  {
                    single (id: $id) {
                      id
                    }
                  }
                }
                """;

        return graphQlClient.document(query).retrieve("pages.delete.responseResult").toEntity(ResponseResult.class)
                .block();
    }
}
