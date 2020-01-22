
package com.agileandmore.examples.handler;

import com.agileandmore.examples.model.views.ApiGatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * Update an existing User.
 *
 * @ServerlessEndpoint
 * @ServerlessOutput 200 void If the user has been updated with success.
 * @ServerlessOutput 404 com.agileandmore.examples.model.views.ErrorMessage An error message if the User does not exist.
 */
public class UpdateUserByPhoneHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse>{

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> i, Context cntxt) {
        // TODO proper implementation here
        return new ApiGatewayResponse();
    }
    
}
