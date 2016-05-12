package org.aprestos.labs.snippets.impl.thirdparty.junit;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class TestWithRules {

	@Rule
	public CustomRule rule = new CustomRule();
	
	@Test
	@Configuration( value = "linux" )
	public void test1() {
		Assert.assertEquals("linux", System.getProperty("context"));
	}
	
	@Test
	@Configuration( value = "windows" )
	public void test2() {
		Assert.assertEquals("windows", System.getProperty("context"));
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.METHOD})
	public @interface Configuration {
	  String value();
	}
	
}

