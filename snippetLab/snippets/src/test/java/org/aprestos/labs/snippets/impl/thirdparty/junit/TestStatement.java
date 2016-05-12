package org.aprestos.labs.snippets.impl.thirdparty.junit;

import org.junit.runners.model.Statement;

public class TestStatement extends Statement {
	
	private Statement base;
	
	public TestStatement(Statement base) {
		this.base = base;
	}

	@Override
	public void evaluate() throws Throwable {
		base.evaluate();
	}

}
