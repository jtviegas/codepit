package io.swagger.api;

import io.swagger.api.*;
import io.swagger.model.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import io.swagger.model.Item;
import io.swagger.model.Error;

import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-26T19:16:03.817Z")
public abstract class ItemsApiService {
    public abstract Response addItem(String apiKey,Item body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response deleteItem(String apiKey,String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response findItems(String cat,String subcat,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemById(String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response updateItem(String apiKey,Item body,SecurityContext securityContext) throws NotFoundException;
}
