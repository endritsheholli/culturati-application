package com.iotiq.application.wiki.messages;

import lombok.Data;

@Data
public class WikiAuthResponse {
    private String jwt;
    private Boolean mustChangePwd;
    private Boolean mustProvideTFA;
    private Boolean mustSetupTFA;
    private Boolean continuationToken;
    private String redirect;
    private Boolean tfaQRImage;
    private ResponseResult responseResult;
}
