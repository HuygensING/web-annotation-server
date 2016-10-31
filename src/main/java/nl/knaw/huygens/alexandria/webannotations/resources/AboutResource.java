package nl.knaw.huygens.alexandria.webannotations.resources;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;
import com.google.common.collect.ImmutableMap;

@Path("about")
public class AboutResource {

  @GET
  @Timed
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAbout() {
    Map<String, String> about = ImmutableMap.of("version", "1");
    return Response.ok(about).build();
  }

}
