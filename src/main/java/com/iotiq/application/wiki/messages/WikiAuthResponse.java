package com.iotiq.application.wiki.messages;

public record WikiAuthResponse(String jwt, Boolean mustChangePwd, Boolean mustProvideTFA, Boolean mustSetupTFA,
                               Boolean continuationToken, String redirect, Boolean tfaQRImage,
                               ResponseResult responseResult) {
}
