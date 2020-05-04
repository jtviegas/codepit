package org.aprestos.labs.experiment.jms;

import java.io.Serializable;

public class Message implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long id;

  private String to;

  private String body;

  public Message() {
  }

  public Message(Long id, String to, String body) {
    this.id = id;

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("Message {id:%d, to=%s, body=%s}", getId(), getTo(), getBody());

  }

}
