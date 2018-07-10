package org.aprestos.labs.json.beanmapping.strategies.interfaces;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = GuyImpl.class)
public interface Guy extends Entity, Identifiable {

}