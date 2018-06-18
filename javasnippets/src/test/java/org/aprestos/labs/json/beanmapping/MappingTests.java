package org.aprestos.labs.json.beanmapping;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aprestos.labs.json.beanmapping.strategies.interfaces.Entity;
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

    ObjectMapper om = new ObjectMapper();
    String sEntity = "{\r\n" + "  \"Active\": true\r\n" + "  , \"Name\": \"jonathen silva\"\r\n"
        + "  , \"Id\": \"dsfjhdasvlkjhaskjlv\"\r\n" + "  , \"Public\": true\r\n" + "}";

    Entity oEntity = om.convertValue(sEntity, Entity.class);

    String sEntity2 = om.writeValueAsString(oEntity);
    Entity oEntity2 = om.convertValue(sEntity2, Entity.class);

    assertEquals(oEntity, oEntity2);

    // Identifiable id = new IdentifiableImpl();
    // id.setId("adsada");
    // String s = om.writeValueAsString(id);
    // System.out.println(s);
    //
    // Identifiable id2 = om.convertValue(s, Identifiable.class);
    // assertEquals(id, id2);
    //
    // Guy guy = new GuyImpl();
    // guy.setActive(true);
    // guy.setId("lkjfalskdjdflk");
    // guy.setName("john");
    //
    // Friend f = new FriendImpl();
    // f.setDegree("grande");
    // f.setGuy(guy);
    //
    // s = om.writeValueAsString(f);
    // System.out.println(s);
    // Friend f2 = om.convertValue(s, Friend.class);
    // assertEquals(f, f2);
    //
    // s = om.writeValueAsString(guy);
    // System.out.println(s);
    // Guy guy2 = om.convertValue(s, Guy.class);
    //
    // assertEquals(guy, guy2);

  }

  @Test
  public void test_map5() throws Exception {

    JustAClass o = new JustAClass();
    o.setDesc("description yeah");
    o.setId(new Long(34));
    o.setName("jonathan");
    o.setNums(Arrays.asList(new Long(22), new Long(23)));

    Map<String, Object> map = new HashMap<String, Object>();
    map.put("name", "paul");
    map.put("numbers", null);

    JustAClass o2 = new JustAClass();
    o2.setDesc("description yeah");
    o2.setId(new Long(34));
    o2.setName("paul");

    ObjectMapper om = new ObjectMapper();

    Map<String, Object> c = om.convertValue(o, new TypeReference<Map<String, Object>>() {
    });
    for (Map.Entry<String, Object> e : map.entrySet())
      c.put(e.getKey(), e.getValue());

    o = om.convertValue(c, JustAClass.class);
    assertEquals(o, o2);
  }

}
