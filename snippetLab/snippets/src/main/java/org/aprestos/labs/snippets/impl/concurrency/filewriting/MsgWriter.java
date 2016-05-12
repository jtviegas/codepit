package org.aprestos.labs.snippets.impl.concurrency.filewriting;

import java.io.IOException;

public interface MsgWriter {

	void write(String msg) throws IOException;

}