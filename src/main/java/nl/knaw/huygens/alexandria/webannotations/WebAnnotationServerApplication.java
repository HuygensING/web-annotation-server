package nl.knaw.huygens.alexandria.webannotations;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.knaw.huygens.alexandria.webannotations.health.DefaultHealthCheck;
import nl.knaw.huygens.alexandria.webannotations.resources.AboutResource;
import nl.knaw.huygens.alexandria.webannotations.resources.AnnotationsResource;

public class WebAnnotationServerApplication extends Application<WebAnnotationServerConfiguration> {

  public static void main(final String[] args) throws Exception {
    new WebAnnotationServerApplication().run(args);
  }

  @Override
  public String getName() {
    return "WebAnnotationServer";
  }

  @Override
  public void initialize(final Bootstrap<WebAnnotationServerConfiguration> bootstrap) {
    // TODO: application initialization
  }

  @Override
  public void run(final WebAnnotationServerConfiguration configuration, final Environment environment) {
    environment.jersey().register(new AboutResource());
    environment.jersey().register(new AnnotationsResource());
    environment.healthChecks().register("default", new DefaultHealthCheck());
  }

}
