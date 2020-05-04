package org.aprestos.labs.model.dtos;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleMessage implements Serializable {

    private final long timestamp;
    private final String text;

}
