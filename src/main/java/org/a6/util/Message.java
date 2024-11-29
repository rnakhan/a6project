package org.a6.util;

import java.time.Instant;

public class Message implements Comparable<Message> {

  private Instant timestamp;
  private String message;

  public Message(String message) {
    this.timestamp = Instant.now();
    this.message = message;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public int compareTo(Message other) {
    return this.timestamp.compareTo(other.timestamp);
  }
}