package com.agileandmore.examples.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.agileandmore.examples.model.views.ApiGatewayResponse;
import java.util.Map;

/**
 * Create a new Gradle in the system.
 * Because Gradles don't really exist in real life, this does not create much.
 *
 * @ServerlessEndpoint
 * @ServerlessInput java.lang.String A dummy message.
 * @ServerlessOutput 201 void if the Gradle has been created.
 * @ServerlessOutput 409 void An error if the Gradle already exists.
 * @SecurityDef type=jwt name=jwt_scheme2 documentation=A JWT token to allow access to the endpoint. This token contains
 * the user id (an UUID v4 string) and a list of roles as a comma separated list of strings. The token also has an expiration
 * date.
 * @SecurityRef jwt_scheme2
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
