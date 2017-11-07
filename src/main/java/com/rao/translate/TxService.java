package com.rao.translate;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rao.translate.check.Health;
import com.rao.translate.config.TxConfig;
import com.rao.translate.resource.TranslateResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class TxService extends Application<TxConfig> {

    @Override
    public void initialize(final Bootstrap<TxConfig> btstrp) {

        final ObjectMapper om = btstrp.getObjectMapper();
        om.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);

        btstrp.addBundle(new ViewBundle<>());

        // add swagger bundle config
        btstrp.addBundle(new SwaggerBundle<TxConfig>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(final TxConfig cfg) {
                return cfg.swaggerBundleConfiguration;
            }
        });
    }
    @Override
    public void run(final TxConfig txConfig, final Environment environment) throws Exception {
        environment.jersey().register(new TranslateResource());
        environment.healthChecks().register("Check", new Health());
    }
    public static void main(String[] args) {
        try {
            if(args.length==0){
                args=new String[2];
                args[0]="server";
                args[1]="src/main/resources/config.yml";
            }
            new TxService().run(args);
        } catch (Exception e) {
            //logger.error
        }
    }
}
