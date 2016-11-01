package nl.knaw.huygens.alexandria.webannotations.core;

/**
 * Created by bram on 1-11-16.
 */
public class WebAnnotation {

  private String json = "";
  private String eTag = "";

  public WebAnnotation setJson(String json) {
    this.json = json;
    return this;
  }

  public String json() {
    return json;
  }

  public WebAnnotation setETag(String eTag) {
    this.eTag = eTag;
    return this;
  }

  public String eTag() {
    return eTag;
  }

}