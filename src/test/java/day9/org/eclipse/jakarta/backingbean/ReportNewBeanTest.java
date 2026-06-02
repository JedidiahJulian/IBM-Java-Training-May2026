package day9.org.eclipse.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import day9.org.eclipse.jakarta.dto.ReportDto;
import day9.org.eclipse.jakarta.infrastracture.repository.ReportRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportNewBeanTest {

    @Test
    @DisplayName("Should create a new report and redirect to report list")
    void testCreate() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportNewBean bean = new ReportNewBean();
        setField(bean, "reportRepository", repository);

        bean.setTitle("Test Report");
        bean.setDetail("This is a test report");

        String result = bean.create();

        assertEquals("/reportList.xhtml?faces-redirect=true", result);
        assertEquals(1, repository.findAll().size());

        ReportDto createdReport = repository.findByIndex(0);
        assertEquals("Test Report", createdReport.getTitle());
        assertEquals("This is a test report", createdReport.getDetail());
    }

    @Test
    @DisplayName("Should update title and detail through accessors")
    void testAccessors() {
        ReportNewBean bean = new ReportNewBean();

        bean.setTitle("Accessor Report");
        bean.setDetail("Accessor detail");

        assertEquals("Accessor Report", bean.getTitle());
        assertEquals("Accessor detail", bean.getDetail());
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
