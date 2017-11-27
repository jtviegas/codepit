package org.aprestos.labs;

import org.aprestos.labs.*;
import org.aprestos.labs.*;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import org.aprestos.labs.Error;
import org.aprestos.labs.Item;

import java.util.List;
import org.aprestos.labs.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-26T19:58:44.190Z")
public abstract class ItemsApiService {
    public abstract Response addItem(String apiKey,Item body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response deleteItem(String apiKey,String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response findItems( @NotNull String cat, @NotNull String subcat,SecurityContext securityContext) throws NotFoundException;
    public abstract Response getItemById(String id,SecurityContext securityContext) throws NotFoundException;
    public abstract Response updateItem(String apiKey,Item body,SecurityContext securityContext) throws NotFoundException;
}
