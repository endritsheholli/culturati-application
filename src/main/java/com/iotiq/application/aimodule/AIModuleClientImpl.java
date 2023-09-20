package com.iotiq.application.aimodule;

import com.iotiq.application.aimodule.messages.NextStopRequest;
import com.iotiq.application.aimodule.messages.NextStopResponse;
import org.springframework.stereotype.Service;

@Service
public class AIModuleClientImpl implements AIModuleClient {
    @Override
    public NextStopResponse getNextStop(NextStopRequest request) {
        return new NextStopResponse();
    }
}
