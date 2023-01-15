package org.goafabric.spring.boot.examplebatch.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TenantIdBean {
    private static String TENANT_ID;

    @Value("${core.tenantid:0}")
    public void setTenantId(String tenantId) {
        TENANT_ID = tenantId;
    }

    public static String getTenantId() {
        return TENANT_ID;
    }
}
