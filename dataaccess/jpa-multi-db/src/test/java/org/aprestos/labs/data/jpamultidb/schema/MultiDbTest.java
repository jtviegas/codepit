package org.aprestos.labs.data.jpamultidb.schema;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.RandomStringUtils;
import org.aprestos.labs.data.jpamultidb.schema.common.model.Adjective;
import org.aprestos.labs.data.jpamultidb.schema.common.repository.AdjectiveRepository;
import org.aprestos.labs.data.jpamultidb.schema.special.model.Remark;
import org.aprestos.labs.data.jpamultidb.schema.special.repository.RemarkRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiDbTest {

  @Autowired
  private AdjectiveRepository adjectiveRepository;

  @Autowired
  private RemarkRepository remarkRepository;

  @Test
  public void whenCreatingUser_thenCreated() {

    Adjective savedAdjective = adjectiveRepository.save(new Adjective(RandomStringUtils.random(8, true, false)));
    assertNotNull(savedAdjective.getId());

    Adjective readAdjective = adjectiveRepository.findOne(savedAdjective.getId());
    assertNotNull(readAdjective.getId());

    readAdjective.setName(RandomStringUtils.random(8, true, false));
    readAdjective = adjectiveRepository.save(readAdjective);

    Remark savedRemark = remarkRepository.save(new Remark(RandomStringUtils.random(8, true, false), readAdjective));
    assertNotNull(savedRemark.getId());

    String _text = RandomStringUtils.random(8, true, false);
    savedRemark.setText(_text);
    savedRemark = remarkRepository.save(savedRemark);
    assertNotNull(savedRemark.getId());

    Remark readRemark = remarkRepository.findOne(savedRemark.getId());
    assertEquals(_text, readRemark.getText());

  }

}
