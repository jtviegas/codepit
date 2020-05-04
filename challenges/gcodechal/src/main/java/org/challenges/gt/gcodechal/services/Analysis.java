package org.challenges.gt.gcodechal.services;

import java.io.OutputStream;

public interface Analysis<T> {
	void analyse(T entry);

	void printOutcome(OutputStream outSteam);

	void reset();
}
