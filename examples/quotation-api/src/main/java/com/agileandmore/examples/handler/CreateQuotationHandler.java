package com.agileandmore.examples.handler;

import com.agileandmore.examples.model.views.ApiGatewayResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import java.util.Map;

/**
 * Create a new Quotation object in the system, for a specific User.
 *
 * @ServerlessEndpoint
 * @ServerlessPathParam id the user ID (currently the international phone
 * number). This parameter is mandatory.
 * @ServerlessInput com.agileandmore.examples.model.Quotation The Quotation object
 * to create
 * @ServerlessOutput 201 com.agileandmore.examples.model.Quotation The Payment,
 * updated with the creation timestamp.
 * @ServerlessOutput 400 com.agileandmore.examples.model.views.ErrorMessage An
 * error message if the Quotation attributes are invalid.
 */
public class CreateQuotationHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        // TODO put your own code here
        return new ApiGatewayResponse();
    }

}
