
package com.agileandmore.examples.handler;

import com.agileandmore.examples.model.views.ApiGatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * Find a User by phone number.
 *
 * @ServerlessEndpoint
 * @ServerlessPathParam mobile the full international phone/mobile number of the user.
 * @ServerlessOutput 200 com.agileandmore.examples.model.User The User.
 * @ServerlessOutput 404 com.agileandmore.examples.model.views.ErrorMessage An error message if the User does not exist.
 * @ServerlessOutput 400 com.agileandmore.examples.model.views.ErrorMessage An error message if the User is trying to query another User
 * information, but does not have the proper role to do so.
 */
public class FindUserByPhoneHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> i, Context cntxt) {
        // TODO normally you'd create a User and encapsulate it in an ApiGatewayResponse, in the body field.
        return new ApiGatewayResponse();
    }
    
}
