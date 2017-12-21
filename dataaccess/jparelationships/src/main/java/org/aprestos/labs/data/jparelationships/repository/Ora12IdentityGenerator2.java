package org.aprestos.labs.data.jparelationships.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.GetGeneratedKeysDelegate;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentityGenerator;
import org.hibernate.id.PostInsertIdentityPersister;
import org.hibernate.id.insert.InsertGeneratedIdentifierDelegate;

public class Ora12IdentityGenerator2 extends IdentityGenerator {
  @Override
  public InsertGeneratedIdentifierDelegate getInsertGeneratedIdentifierDelegate(PostInsertIdentityPersister persister,
      Dialect dialect, boolean isGetGeneratedKeysEnabled) throws HibernateException {
    if (isGetGeneratedKeysEnabled) {
      return (InsertGeneratedIdentifierDelegate) new Oracle12GetGeneratedKeysDelegate(persister, dialect);
    } else {
      return super.getInsertGeneratedIdentifierDelegate(persister, dialect, isGetGeneratedKeysEnabled);
    }
  }

  public static class Oracle12GetGeneratedKeysDelegate extends GetGeneratedKeysDelegate {
    private String[] keyColumns;

    public Oracle12GetGeneratedKeysDelegate(PostInsertIdentityPersister persister, Dialect dialect) {
      super(persister, dialect);
      this.keyColumns = getPersister().getRootTableKeyColumnNames();
      if (keyColumns.length > 1) {
        throw new HibernateException("Identity generator cannot be used with multi-column keys");
      }
    }

    protected PreparedStatement prepare(String insertSQL, SessionImplementor session) throws SQLException {
      return session.getJdbcCoordinator().getStatementPreparer().prepareStatement(insertSQL, keyColumns);
    }
  }
}