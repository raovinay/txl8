package com.rao.translate.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.rao.translate.model.TranslateRequest;
import com.rao.translate.model.TranslateResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/translate")
@Api(value = "/translate")
public class TranslateResource {
    @POST
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Translate", response = Response.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK"),
        @ApiResponse(code = 400, message = "Bad request"),
        @ApiResponse(code = 500, message = "Server error", response = String.class)
    })
    public TranslateResponse translate(TranslateRequest request) {
        Translate translate = createTranslateService();
        TranslateOption srcLang = TranslateOption.sourceLanguage(request.getSource());
        TranslateOption tgtLang = TranslateOption.targetLanguage(request.getTarget());

        // Use translate `model` parameter with `base` and `nmt` options.
        TranslateOption model = TranslateOption.model("nmt");

        Translation translation = translate.translate(request.getQuery(), srcLang, tgtLang, model);

        return new TranslateResponse(translation.getTranslatedText());
    }

    public static Translate createTranslateService() {
        return TranslateOptions.newBuilder().build().getService();
    }
}
