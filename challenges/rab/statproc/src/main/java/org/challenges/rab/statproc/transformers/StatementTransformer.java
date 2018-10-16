package org.challenges.rab.statproc.transformers;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.statement.Statement;

@FunctionalInterface
public interface StatementTransformer<T> {
    Statement toStatement(T source) throws StatementFormatException;
}
