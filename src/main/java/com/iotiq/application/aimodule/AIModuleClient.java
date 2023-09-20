package com.iotiq.application.aimodule;

import com.iotiq.application.aimodule.messages.NextStopRequest;
import com.iotiq.application.aimodule.messages.NextStopResponse;

public interface AIModuleClient {
    NextStopResponse getNextStop(NextStopRequest request);
}
