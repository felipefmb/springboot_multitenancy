package com.felipebatista.multitenancia.multitenant;


import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static com.felipebatista.multitenancia.multitenant.TenantConstant.DEFAULT_TENANT_ID;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    private Logger LOGGER = LoggerFactory.getLogger(TenantIdentifierResolver.class);

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        if (tenantId != null) {
            LOGGER.info("dentro do resolveCurrentTenantIdentifier :: tenantId >> " + tenantId);
            return tenantId;
        }
        LOGGER.info("dentro do resolveCurrentTenantIdentifier :: tenantId >> " + DEFAULT_TENANT_ID);
        return DEFAULT_TENANT_ID;

    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
