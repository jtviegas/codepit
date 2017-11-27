package org.aprestos.labs;

import org.aprestos.labs.*;
import org.aprestos.labs.ItemsApiService;
import org.aprestos.labs.factories.ItemsApiServiceFactory;

import io.swagger.annotations.ApiParam;
import io.swagger.jaxrs.*;

import org.aprestos.labs.Error;
import org.aprestos.labs.Item;

import java.util.List;
import org.aprestos.labs.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/items")


@io.swagger.annotations.Api(description = "the items API")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2017-11-26T19:58:44.190Z")
public class ItemsApi  {
   private final ItemsApiService delegate;

   public ItemsApi(@Context ServletConfig servletContext) {
      ItemsApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("ItemsApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (ItemsApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = ItemsApiServiceFactory.getItemsApi();
      }

      this.delegate = delegate;
   }

    @POST
    
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Add a new item to the store", notes = "", response = void.class, tags={ "item", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 405, message = "Invalid input", response = void.class) })
    public Response addItem(@ApiParam(value = "" ,required=true)@HeaderParam("api_key") String apiKey
,@ApiParam(value = "item object that needs to be added to the store" ,required=true) Item body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.addItem(apiKey,body,securityContext);
    }
    @DELETE
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Deletes an item", notes = "", response = void.class, tags={ "item", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Item not found", response = void.class) })
    public Response deleteItem(@ApiParam(value = "" ,required=true)@HeaderParam("api_key") String apiKey
,@ApiParam(value = "Item id to delete",required=true) @PathParam("id") String id
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.deleteItem(apiKey,id,securityContext);
    }
    @GET
    
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "get items eventually using category and/or subcategory", notes = "", response = Item.class, responseContainer = "List", tags={ "item", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Item.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid status value", response = Item.class, responseContainer = "List") })
    public Response findItems(@ApiParam(value = "category value that need to be considered for filter",required=true) @QueryParam("cat") String cat
,@ApiParam(value = "subcategory value that need to be considered for filter",required=true) @QueryParam("subcat") String subcat
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.findItems(cat,subcat,securityContext);
    }
    @GET
    @Path("/{id}")
    
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Find item by ID", notes = "Returns a single item", response = Item.class, tags={ "item", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "successful operation", response = Item.class),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = Item.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Item not found", response = Item.class) })
    public Response getItemById(@ApiParam(value = "ID of item to return",required=true) @PathParam("id") String id
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getItemById(id,securityContext);
    }
    @PUT
    @Path("/{id}")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Update an existing item", notes = "", response = void.class, tags={ "item", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "Item not found", response = void.class),
        
        @io.swagger.annotations.ApiResponse(code = 405, message = "Validation exception", response = void.class) })
    public Response updateItem(@ApiParam(value = "" ,required=true)@HeaderParam("api_key") String apiKey
,@ApiParam(value = "item object that needs to be added to the store" ,required=true) Item body
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.updateItem(apiKey,body,securityContext);
    }
}
