package org.aprestos.labs.jaxrs.jerseyjee;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.aprestos.labs.jaxrs.jerseyjee.resources.ItemsApi;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath("/api")
public class Bootstrap extends Application {

  public Bootstrap() {

    BeanConfig beanConfig = new BeanConfig();
    beanConfig.setVersion("1.0.2");
    beanConfig.setSchemes(new String[] { "http" });
    beanConfig.setHost("localhost:9080");
    beanConfig.setBasePath("/api");
    beanConfig.setResourcePackage("org.aprestos.labs.jaxrs.jerseyjee.resources");
    beanConfig.setScan(true);
  }

  @Override
  public Set<Class<?>> getClasses() {
    HashSet<Class<?>> classes = new HashSet<Class<?>>();

    classes.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    classes.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    classes.add(ItemsApi.class);

    return classes;
  }

}
