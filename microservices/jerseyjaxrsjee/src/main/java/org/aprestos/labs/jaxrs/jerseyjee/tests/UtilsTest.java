package org.aprestos.labs.jaxrs.jerseyjee.tests;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import org.aprestos.labs.jaxrs.jerseyjee.model.Item;

public class UtilsTest {

  private static final Random random = new Random();

  public static Item getRandomItemWithId() {
    Item result = new Item();
    result.setId(UUID.randomUUID().toString());
    result.setName(UUID.randomUUID().toString());
    result.setPrice(new BigDecimal(random.nextDouble() * 100.0));
    result.setCategory(UUID.randomUUID().toString());
    result.setSubcategory(UUID.randomUUID().toString());
    result.setNotes(UUID.randomUUID().toString());
    result.setImages(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
    return result;
  }

}
