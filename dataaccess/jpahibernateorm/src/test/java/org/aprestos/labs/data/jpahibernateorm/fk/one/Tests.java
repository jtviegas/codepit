package org.aprestos.labs.data.jpahibernateorm.fk.one;

import java.math.BigDecimal;

import org.aprestos.labs.data.jpahibernateorm.fk.one.model.Part;
import org.aprestos.labs.data.jpahibernateorm.fk.one.model.PartType;
import org.aprestos.labs.data.jpahibernateorm.fk.one.repository.PartRepository;
import org.aprestos.labs.data.jpahibernateorm.fk.one.repository.PartTypeRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Tests {

  @Autowired
  private PartTypeRepository partTypeRepository;

  @Autowired
  private PartRepository partRepository;

  @Test
  public void test_00() {

    PartType t = partTypeRepository.save(new PartType("ahahah"));
    Part p = partRepository.save(new Part("ahahah and more", BigDecimal.valueOf(5), t));

    Assert.assertNotNull(p.getId());
    Assert.assertEquals(t, p.getPartType());
    Assert.assertNotNull(p.getPartTypeId());

    p = new Part();
    p.setName("ahahah and more");
    p.setPrice(BigDecimal.valueOf(5));
    p.setPartTypeId(t.getId());

    p = partRepository.save(p);

    Assert.assertNotNull(p.getId());
    Assert.assertEquals(t, p.getPartType());
    Assert.assertNotNull(p.getPartTypeId());

  }

}
