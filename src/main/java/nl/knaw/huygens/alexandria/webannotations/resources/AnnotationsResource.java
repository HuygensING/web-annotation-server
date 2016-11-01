package nl.knaw.huygens.alexandria.webannotations.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jsonldjava.core.JsonLdError;
import com.github.jsonldjava.core.JsonLdOptions;
import com.github.jsonldjava.core.JsonLdProcessor;
import com.github.jsonldjava.utils.JsonUtils;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.jersey.params.UUIDParam;
import nl.knaw.huygens.alexandria.webannotations.core.WebAnnotation;
import nl.knaw.huygens.alexandria.webannotations.core.WebAnnotationPrototype;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.TDBFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Path("annotations")
public class AnnotationsResource {
  private static final String JSONLD_MEDIATYPE = "application/ld+json; profile=\"http://www.w3.org/ns/anno.jsonld\"";
  private static final String RESOURCE_TYPE_URI = "http://www.w3.org/ns/ldp#Resource";
  private static final String ANNOTATION_TYPE_URI = "http://www.w3.org/ns/oa#Annotation";
  private static final Set<String> ALLOWED_METHODS = ImmutableSet.<String>of("PUT", "GET", "OPTIONS", "HEAD", "DELETE");

  @POST
  @Consumes(JSONLD_MEDIATYPE)
  @Produces(JSONLD_MEDIATYPE)
  public Response addWebAnnotation(String jsonld) throws IOException, JsonLdError {
    UUID uuid = UUID.randomUUID();
    Object jsonObject = JsonUtils.fromString(jsonld);
    Map context = new HashMap();
    JsonLdOptions options = new JsonLdOptions();
    Map<String,Object> compact = JsonLdProcessor.compact(jsonObject, context, options);
    compact.put("id","http://localhost:2015/annotations/" + uuid);
    String created = Instant.now().toString();
    compact.put("created", created);
    Dataset dataset = getDataset();
    dataset.begin(ReadWrite.WRITE);
    Model defaultModel = dataset.getDefaultModel();
//    Object rdf = JsonLdProcessor.toRDF(compact, new JenaTripleCallback());
    dataset.commit();

    return Response.created(URI.create((String) compact.get("id")))//
        .link(RESOURCE_TYPE_URI, "type")//
        .link(ANNOTATION_TYPE_URI, "type")//
        .tag(String.valueOf(created.hashCode()))//
        .allow(ALLOWED_METHODS)//
        .entity(compact)//
        .build();
  }

  @GET
  @Timed
  @Produces(MediaType.APPLICATION_JSON)
  public Response getAnnotations() {
    Dataset dataset = getDataset();
    dataset.begin(ReadWrite.READ);
    Model model = dataset.getDefaultModel();
    Object annotations = model;
    dataset.end();
    return Response.ok(annotations).build();
  }

  @GET
  @Path("{uuid}")
  @Produces(JSONLD_MEDIATYPE)
  public Response getWebAnnotation(@PathParam("uuid") UUIDParam uuidParam) {
    // WebAnnotation webAnnotation = readExistingWebAnnotation(uuidParam);
    WebAnnotation webAnnotation = new WebAnnotation();
    return Response.ok(webAnnotation.json())//
        .link(RESOURCE_TYPE_URI, "type")//
        .link(ANNOTATION_TYPE_URI, "type")//
        .tag(webAnnotation.eTag())//
        .allow(ALLOWED_METHODS)//
        .build();
  }

  private WebAnnotation validate(WebAnnotationPrototype prototype) {
    String json = "";
    try {
      json = new ObjectMapper().writeValueAsString(prototype);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return new WebAnnotation()//
        .setJson(json)//
        .setETag(String.valueOf(prototype.getModified().hashCode()));
  }

  private Dataset getDataset(){
    String directory = "data/tdb";
    Dataset dataset = TDBFactory.createDataset(directory);
    return dataset;
  }


  private void something(){
    JenaJSONLD.init(); // Only needed once

    Dataset dataset = DatasetFactory.createMem();
    RDFDataMgr.read(dataset, inputStream, "http://example.com/", JenaJSONLD.JSONLD);

  }
}
