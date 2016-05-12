package org.aprestos.labs.snippets.impl.thirdparty.junit;

import org.aprestos.labs.snippets.impl.thirdparty.junit.TestWithRules.Configuration;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class CustomRule implements TestRule {

	public CustomRule() {
		// TODO Auto-generated constructor stub
	}

	public Statement apply(Statement base, Description description) {
		
		Configuration annotation  = description.getAnnotation( Configuration.class );
		String value = annotation.value();
		System.setProperty("context", value);
		return new TestStatement(base);
	}

}
