package io.swagger.api.factories;

import io.swagger.api.ItemsApiService;
import io.swagger.api.impl.ItemsApiServiceImpl;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-26T19:16:03.817Z")
public class ItemsApiServiceFactory {
    private final static ItemsApiService service = new ItemsApiServiceImpl();

    public static ItemsApiService getItemsApi() {
        return service;
    }
}
