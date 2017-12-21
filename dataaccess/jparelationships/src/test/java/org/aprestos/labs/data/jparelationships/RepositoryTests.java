package org.aprestos.labs.data.jparelationships;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.aprestos.labs.data.jparelationships.model.schema.common.Customer;
import org.aprestos.labs.data.jparelationships.model.schema.common.CustomersAssociation;
import org.aprestos.labs.data.jparelationships.model.schema.common.Invoice;
import org.aprestos.labs.data.jparelationships.model.schema.common.TerminationLevel;
import org.aprestos.labs.data.jparelationships.repository.CustomerRepository;
import org.aprestos.labs.data.jparelationships.repository.CustomersAssociationRepository;
import org.aprestos.labs.data.jparelationships.repository.InvoiceRepository;
import org.aprestos.labs.data.jparelationships.repository.TerminationLevelRepository;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RepositoryTests {

  private static final Logger logger = LoggerFactory.getLogger(RepositoryTests.class);

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private InvoiceRepository orderRepository;

  @Autowired
  private CustomersAssociationRepository caRepository;

  @Autowired
  private TerminationLevelRepository tlRepository;

  @Autowired
  private Literals literals;

  @Test
  public void test_00() {
    logger.trace("[test] in");
    Customer c = customerRepository.save(new Customer("john", "florence"));
    Assert.assertNotNull(literals);
    Assert.assertNotNull(c.getId());
    logger.trace("[test] out");
  }

  @Test
  public void test_01() {
    logger.trace("[test] in");
    Customer c = customerRepository.save(new Customer("john", "florence"));
    Invoice o = orderRepository.save(new Invoice(c));
    Assert.assertNotNull(o.getId());
    Assert.assertNotNull(o.getCustomer().getId());
    logger.trace("[test] out");
  }

  @Test
  public void test_02() {
    logger.trace("[test] in");
    Customer c = customerRepository.save(new Customer("john", "florence"));
    Customer c2 = customerRepository.save(new Customer("Mario", "Sister"));

    Invoice o = orderRepository.save(new Invoice(c));
    Assert.assertNotNull(o.getId());
    Assert.assertNotNull(o.getCustomer().getId());

    Invoice o2 = orderRepository.save(new Invoice(c2));
    Assert.assertNotNull(o2.getId());
    Assert.assertNotNull(o2.getCustomer().getId());

    o.setCustomer(c2);
    orderRepository.save(o);
    Assert.assertNotNull(o.getId());
    Assert.assertNotNull(o.getCustomer().getId());

    o2.setCustomer(c);
    orderRepository.save(o2);
    Assert.assertNotNull(o2.getId());
    Assert.assertNotNull(o2.getCustomer().getId());

    logger.trace("[test] out");
  }

  @Test
  public void test_03() {

    Customer c1 = customerRepository.save(new Customer("john2", "florence2"));
    Customer c2 = customerRepository.save(new Customer("Mario2", "Sister2"));
    Customer c3 = customerRepository.save(new Customer("Mario3", "Sister3"));

    CustomersAssociation ca = new CustomersAssociation("the guild");
    ca = caRepository.save(ca);
    Long id = ca.getId();

    c1.setAssociation(ca);
    c2.setAssociation(ca);
    c3.setAssociation(ca);

    c1 = customerRepository.save(c1);
    c2 = customerRepository.save(c2);
    c3 = customerRepository.save(c3);

    Assert.assertNotNull(c1.getAssociation().getId());
    Assert.assertEquals(c1.getAssociation().getId(), ca.getId());
    Assert.assertNotNull(c2.getAssociation().getId());
    Assert.assertEquals(c2.getAssociation().getId(), ca.getId());
    Assert.assertNotNull(c3.getAssociation().getId());
    Assert.assertEquals(c3.getAssociation().getId(), ca.getId());

    caRepository.findOne(id).equals(ca);
    Assert.assertEquals(3, caRepository.findOne(id).getCustomers().size());

  }

  @Test
  public void test_04() {

    List<TerminationLevel> o = StreamSupport.stream(tlRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    Assert.assertTrue(o.isEmpty());
    TerminationLevel o1 = new TerminationLevel();
    o1.setDescription("bla");
    tlRepository.save(o1);
    o = StreamSupport.stream(tlRepository.findAll().spliterator(), false).collect(Collectors.toList());
    Assert.assertFalse(o.isEmpty());

  }

}
