package com.agileandmore.examples.handler;

import com.agileandmore.examples.model.views.ApiGatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;


/**
 * Get the list of Quotation object in the system, for a specific User.
 *
 * @ServerlessEndpoint
 * @ServerlessPathParam id the user ID (currently the international phone number). This parameter is mandatory.
 * @ServerlessOutput 200 com.agileandmore.examples.model.Quotation[] The array of Quotations for this User.
 * @ServerlessOutput 400 com.agileandmore.examples.model.views.ErrorMessage An error message if the user id is invalid.
 */
public class GetUserQuotationsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {


    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        // TODO your implementation here
      return null;
    }

}
