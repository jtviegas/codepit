package org.aprestos.labs.experiment.jms;

import java.io.Serializable;

public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  private String to;

  private String body;

  public Message() {
  }

  public Message(String to, String body) {
    this.to = to;
    this.body = body;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return String.format("Message{to=%s, body=%s}", getTo(), getBody());
  }

}
