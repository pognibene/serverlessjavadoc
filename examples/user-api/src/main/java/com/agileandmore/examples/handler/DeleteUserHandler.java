
package com.agileandmore.examples.handler;

import com.agileandmore.examples.model.views.ApiGatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * Delete an existing User in the system.
 *
 * @ServerlessEndpoint
 * @ServerlessPathParam mobile the full international phone/mobile number of the user.
 * @ServerlessOutput 204 void If the User has been deleted successfully.
 * @ServerlessOutput 404 com.agileandmore.examples.model.views.ErrorMessage An error message if the User does not exist.
 */
public class DeleteUserHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse>{

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        // here you would place the real code for your lambda
        return new ApiGatewayResponse();
    }
}
