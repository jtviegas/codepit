package org.aprestos.labs.data.jparelationships.repository;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.AbstractPostInsertGenerator;
import org.hibernate.id.IdentifierGeneratorHelper;
import org.hibernate.id.PostInsertIdentityPersister;
import org.hibernate.id.SequenceIdentityGenerator.NoCommentsInsert;
import org.hibernate.id.insert.AbstractReturningDelegate;
import org.hibernate.id.insert.IdentifierGeneratingInsert;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;

import oracle.jdbc.OraclePreparedStatement;

public class Ora12IdentityGenerator extends AbstractPostInsertGenerator {

  @Override
  public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister,
      Dialect dialect, boolean isGetGeneratedKeysEnabled) throws HibernateException {
    return new Delegate(persister, dialect);
  }

  public static class Delegate extends AbstractReturningDelegate {

    private Dialect dialect;

    private String[] keyColumns;

    private int keyId;

    public Delegate(PostInsertIdentityPersister persister, Dialect dialect) {
      super(persister);
      this.dialect = dialect;
      this.keyColumns = getPersister().getRootTableKeyColumnNames();
      if (keyColumns.length > 1) {
        throw new HibernateException("trigger assigned identity generator cannot be used with multi-column keys");
      }
    }

    @SuppressWarnings("deprecation")
    @Override
    public IdentifierGeneratingInsert prepareIdentifierGeneratingInsert() {
      return new NoCommentsInsert(dialect);
    }

    protected OraclePreparedStatement prepare(String insertSQL, SessionImplementor session) throws SQLException {
      insertSQL = insertSQL + " returning " + keyColumns[0] + " into ?";

      PreparedStatement os = session.connection().prepareStatement(insertSQL);
      keyId = insertSQL.split("\\?").length;
      OraclePreparedStatement ops = os.unwrap(OraclePreparedStatement.class);
      ops.registerReturnParameter(keyId, Types.DECIMAL);
      return ops;
    }

    protected Serializable executeAndExtract(PreparedStatement insert, SessionImplementor session) throws SQLException {

      OraclePreparedStatement os = (OraclePreparedStatement) insert;
      os.executeUpdate();

      ResultSet generatedKeys = os.getReturnResultSet();
      if (generatedKeys == null) {
        throw new HibernateException("Nullable Resultset");
      }
      try {
        return IdentifierGeneratorHelper.getGeneratedIdentity(generatedKeys, keyColumns[0],
            getPersister().getIdentifierType());
      } finally {
        generatedKeys.close();
      }
    }

  }
}