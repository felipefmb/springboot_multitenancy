package com.felipebatista.multitenancia.multitenant;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.engine.jdbc.connections.internal.DatasourceConnectionProviderImpl;
import org.hibernate.engine.jdbc.connections.spi.AbstractMultiTenantConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MultiTenantConnectionProviderImpl extends DatasourceConnectionProviderImpl implements MultiTenantConnectionProvider {

    private static final String SELECT_CONTEXT = "select set_config('context.tenant' ,? , false)";
    private static final Log log = LogFactory.getLog(ConnectionProvider.class);

    public MultiTenantConnectionProviderImpl() {
    }

    @Autowired
    private DataSource dataSource;

    public Connection getAnyConnection() throws SQLException {
        return this.getConnection(null);
    }

    @Override
    public Connection getConnection() {
        throw new UnsupportedOperationException("Não é possível adquirir uma conexão sem definir um tenant.");
    }

    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    public Connection getConnection(String tenantIdentifier) throws SQLException {
        String entityIdentifier = "-1";
        if (StringUtils.isNotBlank(tenantIdentifier)) {
            entityIdentifier = tenantIdentifier;
        }
        if (log.isTraceEnabled()) {
            log.trace("getConnection -> tenant(" + entityIdentifier + ")");
        }
        try {
            Connection connection = super.getConnection();
            if (log.isTraceEnabled()) {
                log.trace("getConnection -> setDatabase(" + entityIdentifier + ")");
            }
            setContext(entityIdentifier, connection);
            return connection;

        } catch (Exception e) {

        }
        return null;
    }

    private void setContext(String entityIdentifier,
                            Connection connection) throws SQLException {
        try {
            PreparedStatement cstmt = null;

            try {
                cstmt = connection.prepareStatement(SELECT_CONTEXT);
                if (entityIdentifier != null) {
                    cstmt.setString(1, entityIdentifier);
                } else {
                    cstmt.setNull(1, -1);
                }
                cstmt.execute();
            } finally {
                if (cstmt != null) {
                    cstmt.close();
                }

            }
        } catch (Exception var11) {
            this.releaseAnyConnection(connection);
            throw new SQLException("Problemas ao definir tenante.", var11);
        }
    }


    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    public boolean isUnwrappableAs(Class unwrapType) {
        return MultiTenantConnectionProvider.class.equals(unwrapType) || AbstractMultiTenantConnectionProvider.class
                .isAssignableFrom(unwrapType);
    }

    public <T> T unwrap(Class<T> unwrapType) {
        if (this.isUnwrappableAs(unwrapType)) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException(unwrapType);
        }
    }
}
