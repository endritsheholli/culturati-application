package com.iotiq.application.wiki.messages;

public record GraphQLRequestBody(String query, Object variables) {
}
