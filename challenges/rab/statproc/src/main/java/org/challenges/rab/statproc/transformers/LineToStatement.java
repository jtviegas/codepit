package org.challenges.rab.statproc.transformers;

import org.challenges.rab.statproc.exceptions.StatementFormatException;
import org.challenges.rab.statproc.statement.Statement;

public class LineToStatement implements StatementTransformer<String> {

  @Override
  public Statement toStatement(String t) throws StatementFormatException {
    String[] s = t.split(",");
    Statement o = new Statement();
    try {
      o.setReference(Integer.parseInt(s[0].trim()));
      o.setAccountNumber(s[1].trim());
      o.setDescription(s[2].trim());
      o.setStartBalance(Double.parseDouble(s[3].trim()));
      o.setMutation(Double.parseDouble(s[4].trim()));
      o.setEndBalance(Double.parseDouble(s[5].trim()));
    } catch (Exception e) {
      throw new StatementFormatException(e);
    }
    return o;
  }



}
