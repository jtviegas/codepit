package org.aprestos.labs.data.jparelationships;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.aprestos.labs.data.jparelationships.model.Client;
import org.aprestos.labs.data.jparelationships.model.Customer;
import org.aprestos.labs.data.jparelationships.model.CustomersAssociation;
import org.aprestos.labs.data.jparelationships.model.Invoice;
import org.aprestos.labs.data.jparelationships.model.InvoiceLine;
import org.aprestos.labs.data.jparelationships.model.TerminationLevel;
import org.aprestos.labs.data.jparelationships.model.Vendor;
import org.aprestos.labs.data.jparelationships.repository.ClientRepository;
import org.aprestos.labs.data.jparelationships.repository.CustomerRepository;
import org.aprestos.labs.data.jparelationships.repository.CustomersAssociationRepository;
import org.aprestos.labs.data.jparelationships.repository.InvoiceRepository;
import org.aprestos.labs.data.jparelationships.repository.TerminationLevelRepository;
import org.aprestos.labs.data.jparelationships.repository.VendorRepository;
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
  private VendorRepository vendorRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private Literals literals;

  private static Customer customer;

  private static Invoice invoice;

  @Test
  public void test_00_customerSave() {

    customer = customerRepository.save(new Customer("john", "florence"));
    Assert.assertNotNull(literals);
    Assert.assertNotNull(customer.getId());

  }

  @Test
  public void test_01_customerInvoices() {

    invoice = new Invoice();
    invoice.setTimestamp(new Date().getTime());

    customer = new Customer("john", "florence");
    customer.getInvoices().add(invoice);
    customer = customerRepository.save(customer);
    Assert.assertNotNull(customer.getInvoices().stream().findFirst().get().getId());

  }

  @Test
  public void test_03_customerAssociations() {

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
  public void test_04_oracleAutoGeneratedId() {

    List<TerminationLevel> o = StreamSupport.stream(tlRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
    Assert.assertTrue(o.isEmpty());
    TerminationLevel o1 = new TerminationLevel();
    o1.setDescription("bla");
    tlRepository.save(o1);
    o = StreamSupport.stream(tlRepository.findAll().spliterator(), false).collect(Collectors.toList());
    Assert.assertFalse(o.isEmpty());

  }

  @Test
  public void test_05_one2many_bidirectional() {

    InvoiceLine il1 = new InvoiceLine(3);
    InvoiceLine il2 = new InvoiceLine(1);

    invoice.addInvoiceLine(il1);
    invoice.addInvoiceLine(il2);

    customer = customerRepository.save(customer);
    invoice = customer.getInvoices().stream().findFirst().get();
    Assert.assertFalse(invoice.getInvoiceLines().isEmpty());

  }

  @Test
  public void test_06_many2many_bidirectional() {

    Vendor v1 = new Vendor("one");
    Vendor v2 = new Vendor("two");

    v1 = vendorRepository.save(v1);
    v2 = vendorRepository.save(v2);

    Client c1 = new Client("one");
    Client c2 = new Client("two");

    c1 = clientRepository.save(c1);
    c2 = clientRepository.save(c2);

    c1.addVendor(v1);
    v1.addClient(c1);
    c2.addVendor(v2);
    v2.addClient(c2);

    v1 = vendorRepository.save(v1);
    v2 = vendorRepository.save(v2);

    Client c = v1.getClients().stream().findFirst().get();

    Assert.assertEquals(c, c1);
    Assert.assertEquals(c.hashCode(), c1.hashCode());
    Assert.assertTrue(v1.getClients().contains(c1));
    Assert.assertTrue(v2.getClients().contains(c2));
    Assert.assertTrue(c1.getVendors().contains(v1));
    Assert.assertTrue(c2.getVendors().contains(v2));

    v2 = vendorRepository.findOne(v2.getId());
    c1 = clientRepository.findOne(c1.getId());
    Assert.assertTrue(v2.getClients().contains(c2));
    Assert.assertTrue(c1.getVendors().contains(v1));

  }

}