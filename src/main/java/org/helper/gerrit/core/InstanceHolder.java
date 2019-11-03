package org.helper.gerrit.core;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Sukhpal Singh
 *
 */
public final class InstanceHolder {
  
  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  
  private InstanceHolder() {
    throw new UtilityClassInstantiationException();
  }
  
  public static ObjectMapper getObjectMapper() {
    return OBJECT_MAPPER;
  }
  
}
