package org.aprestos.labs.json.beanmapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aprestos.labs.json.beanmapping.strategies.interfaces.Guy;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;

public class MappingTests {

  @Test
  public void test() throws Exception {

    final String fieldName = "description";
    JustAClass o = new JustAClass();
    o.setDesc("description yeah");
    o.setId(new Long(34));
    o.setName("jonathan");
    o.setNums(Arrays.asList(new Long(22), new Long(23)));

    ObjectMapper om = new ObjectMapper();

    // Construct a Jackson JavaType for your class
    JavaType javaType = om.getTypeFactory().constructType(JustAClass.class);

    // Introspect the given type
    BeanDescription beanDescription = om.getSerializationConfig().introspect(javaType);

    // Find properties
    List<BeanPropertyDefinition> properties = beanDescription.findProperties();
    for (BeanPropertyDefinition p : properties) {
      System.out.println(p.getName());
      if (p.getName().equals(fieldName)) {
        p.getSetter().callOnWith(o, "uhuh");
      }

    }

    assertTrue(true);
  }

  @Test
  public void test_map() throws Exception {

    JustAClass o = new JustAClass();
    o.setDesc("description yeah");
    o.setId(new Long(34));
    o.setName("jonathan");
    o.setNums(Arrays.asList(new Long(22), new Long(23)));

    ObjectMapper om = new ObjectMapper();
    Map<String, Object> map = om.convertValue(o, new TypeReference<Map<String, Object>>() {
    });
    JustAClass anotherO = om.convertValue(map, JustAClass.class);
    assertEquals(o, anotherO);
  }

  @Test
  public void test_map2() throws Exception {

    JustAnotherClass o = new JustAnotherClass();
    o.setDesc("description yeah");
    o.setId(new Long(34));
    o.setName("jonathan");
    o.setNums(Arrays.asList(new Long(22), new Long(23)));
    o.setFeeling("ahahah");
    o.setGender(Gender.F);
    o.setTough(true);

    ObjectMapper om = new ObjectMapper();
    Map<String, Object> map = om.convertValue(o, new TypeReference<Map<String, Object>>() {
    });
    String ostr = om.writeValueAsString(o);
    JustAnotherClass anotherO = om.convertValue(map, JustAnotherClass.class);
    assertEquals(o, anotherO);
  }

  @Test
  public void test_map3() throws Exception {

    JustAnotherClass o = new JustAnotherClass();
    o.setDesc("description yeah");
    o.setId(new Long(34));
    o.setName("jonathan");
    o.setNums(Arrays.asList(new Long(22), new Long(23)));
    o.setFeeling("ahahah");
    o.setGender(Gender.F);
    o.setTough(true);

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("id", new Long(34));
    map.put("description", "description yeah");
    map.put("name", "jonathan");
    map.put("numbers", Arrays.asList(new Long(22), new Long(23)));
    map.put("feeling", "ahahah");
    map.put("gender", Gender.F);
    map.put("Tough", true);

    ObjectMapper om = new ObjectMapper();

    JustAnotherClass anotherO = om.convertValue(map, JustAnotherClass.class);
    assertEquals(o, anotherO);
  }

  @Test
  public void test_map4() throws Exception {

    Guy o = new Guy("john", "lkjfalskdjdflk", true);
    ObjectMapper om = new ObjectMapper();
    String s = om.writeValueAsString(o);
    System.out.println(s);
    o = new Guy();
    o.setName("john");
    s = om.writeValueAsString(o);
    System.out.println(s);

  }

}
