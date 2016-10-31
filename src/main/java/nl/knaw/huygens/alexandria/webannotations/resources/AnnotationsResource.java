package nl.knaw.huygens.alexandria.webannotations.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.jena.query.Dataset;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.tdb.TDBFactory;

import com.codahale.metrics.annotation.Timed;

@Path("annotations")
public class AnnotationsResource {

  @GET
  @Timed
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAnnotations() {
    String directory = "data/tdb";
    Dataset dataset = TDBFactory.createDataset(directory);
    dataset.begin(ReadWrite.READ);
    // Get model inside the transaction
    Model model = dataset.getDefaultModel();
    dataset.end();
    String annotations = "TODO";
    return Response.ok(annotations).build();
  }

}
