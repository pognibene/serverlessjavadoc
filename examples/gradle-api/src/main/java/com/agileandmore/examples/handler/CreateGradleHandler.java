package com.agileandmore.examples.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.agileandmore.examples.model.views.ApiGatewayResponse;
import java.util.Map;

/**
 * Create a new User in the system.
 *
 * @ServerlessEndpoint
 * @ServerlessInput com.agileandmore.model.User The User to create.
 * @ServerlessOutput 201 com.agileandmore.model.User The created User, with
 * updated information.
 * @ServerlessOutput 409 void An error if the User already exists.
 */
public class CreateGradleHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        
        // note that even though the lambda returns an ApiGatewayResponse,
        // this type just is just a wrapper for the actual response and we are expected
        // to populate it.
        // in this case, the 'real' response object returned by API Gateway would be a User
        return new ApiGatewayResponse();
    }
}
