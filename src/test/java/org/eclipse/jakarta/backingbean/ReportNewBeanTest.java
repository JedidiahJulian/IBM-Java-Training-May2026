package org.eclipse.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastracture.repository.ReportRepository;
import org.junit.jupiter.api.Test;

class ReportNewBeanTest {

    @Test
    void createStoresReportAndReturnsRedirect() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportNewBean bean = new ReportNewBean();
        setField(bean, "reportRepository", repository);

        bean.setTitle("Quarterly report");
        bean.setDetail("Revenue increased");

        String redirect = bean.create();

        assertEquals("/reportList.xhtml?faces-redirect=true", redirect);
        assertEquals("Quarterly report", bean.getTitle());
        assertEquals("Revenue increased", bean.getDetail());

        ReportDto created = repository.findByIndex(0);
        assertEquals("Quarterly report", created.getTitle());
        assertEquals("Revenue increased", created.getDetail());
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
