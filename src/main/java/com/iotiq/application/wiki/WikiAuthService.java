package com.iotiq.application.wiki;

public interface WikiAuthService {

    /**
     * <a href="https://whimsical.com/sending-requests-to-cotwin-api-7VocHj9v5rDSKKfbpvv8hf">authentication flowchart</a>
     */
    void preRequest();

    String getAccessToken();
}
