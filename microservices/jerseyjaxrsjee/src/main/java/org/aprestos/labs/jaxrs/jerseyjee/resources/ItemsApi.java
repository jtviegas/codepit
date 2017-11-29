package org.aprestos.labs.jaxrs.jerseyjee.resources;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.aprestos.labs.jaxrs.jerseyjee.model.Item;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(tags = { "item" }, authorizations = { @Authorization(value = "default") })
@Path("/items")
@Produces({ "application/json" })
public class ItemsApi {

  public ItemsApi(@Context ServletConfig servletContext) {
  }

  @POST
  @Consumes({ "application/json" })
  @ApiOperation(value = "Add a new item to the store", notes = "", response = void.class, tags = { "item", })
  @ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input", response = void.class),
      @ApiResponse(code = 201, message = "success", response = Item.class) })
  public Response addItem(@ApiParam(value = "", required = true) @HeaderParam("api_key") String apiKey,
      @ApiParam(value = "item object that needs to be added to the store", required = true) Item body,
      @Context SecurityContext securityContext) throws NotFoundException {
    Response r = null;
    return r;
  }

  @GET
  @Produces({ "application/json" })
  @ApiOperation(value = "get items eventually using category and/or subcategory", notes = "", response = Item.class, responseContainer = "List", tags = {
      "item", })
  @io.swagger.annotations.ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation", response = Item.class, responseContainer = "List"),
      @ApiResponse(code = 400, message = "Invalid status value", response = void.class) })
  public Response findItems(
      @ApiParam(value = "category value that need to be considered for filter", required = false) @QueryParam("cat") String cat,
      @ApiParam(value = "subcategory value that need to be considered for filter", required = false) @QueryParam("subcat") String subcat,
      @Context SecurityContext securityContext) throws NotFoundException {
    Response r = Response.ok().build();
    return r;
  }

  @Path("/{id}")
  @DELETE
  @ApiOperation(value = "Deletes an item", notes = "", response = void.class, tags = { "item", })
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
      @ApiResponse(code = 404, message = "Item not found", response = void.class),
      @ApiResponse(code = 204, message = "success", response = void.class) })
  public Response deleteItem(@ApiParam(value = "", required = true) @HeaderParam("api_key") String apiKey,
      @ApiParam(value = "Item id to delete", required = true) @PathParam("id") String id,
      @Context SecurityContext securityContext) throws NotFoundException {
    Response r = null;
    return r;
  }

  @Path("/{id}")
  @GET
  @ApiOperation(value = "Find item by ID", notes = "Returns a single item", response = Item.class, tags = { "item", })
  @ApiResponses(value = { @ApiResponse(code = 200, message = "successful operation", response = Item.class),
      @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
      @ApiResponse(code = 404, message = "Item not found", response = void.class) })
  public Response getItemById(@ApiParam(value = "ID of item to return", required = true) @PathParam("id") String id,
      @Context SecurityContext securityContext) throws NotFoundException {
    Response r = null;
    return r;
  }

  @Path("/{id}")
  @PUT
  @Consumes({ "application/json" })
  @Produces({ "application/json" })
  @ApiOperation(value = "Update an existing item", notes = "", response = void.class, tags = { "item", })
  @ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied", response = void.class),
      @ApiResponse(code = 404, message = "Item not found", response = void.class),
      @ApiResponse(code = 405, message = "Validation exception", response = void.class) })
  public Response updateItem(@ApiParam(value = "", required = true) @HeaderParam("api_key") String apiKey,
      @ApiParam(value = "item object that needs to be added to the store", required = true) Item body,
      @Context SecurityContext securityContext) throws NotFoundException {
    Response r = null;
    return r;
  }

}
