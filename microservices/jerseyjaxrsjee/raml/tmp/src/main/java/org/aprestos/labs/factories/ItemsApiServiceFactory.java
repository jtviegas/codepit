package org.aprestos.labs.factories;

import org.aprestos.labs.ItemsApiService;
import org.aprestos.labs.impl.ItemsApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-26T19:58:44.190Z")
public class ItemsApiServiceFactory {
    private final static ItemsApiService service = new ItemsApiServiceImpl();

    public static ItemsApiService getItemsApi() {
        return service;
    }
}
