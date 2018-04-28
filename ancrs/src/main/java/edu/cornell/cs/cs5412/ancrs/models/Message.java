package edu.cornell.cs.cs5412.ancrs.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    public String message;

    public Message() {};

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
