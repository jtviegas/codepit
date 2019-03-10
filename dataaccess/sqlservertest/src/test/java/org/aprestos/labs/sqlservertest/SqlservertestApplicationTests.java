package org.aprestos.labs.sqlservertest;

import org.aprestos.labs.sqlservertest.repository.RulesWeeksRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SqlservertestApplicationTests {

	@Autowired
	private RulesWeeksRepository repository;

	@Test
	public void contextLoads() {

		Assert.assertFalse(0 == repository.count());

	}

}

