package org.eclipse.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.jakarta.dto.ReportDto;
import org.eclipse.jakarta.infrastracture.repository.ReportRepository;
import org.junit.jupiter.api.Test;

class ReportListBeanTest {

    @Test
    void initializesAndUpdatesReports() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportDto original = new ReportDto("Original", "Detail");
        ReportDto other = new ReportDto("Other", "Detail");
        repository.create(original);
        repository.create(other);

        ReportListBean bean = new ReportListBean();
        setField(bean, "reportRepository", repository);

        bean.init();

        assertSame(repository.findAll(), bean.getReports());

        List<ReportDto> customReports = List.of(new ReportDto("Custom", "List"));
        bean.setReports(customReports);
        assertSame(customReports, bean.getReports());

        bean.prepareView(0);
        ReportDto selectedForView = bean.getSelectedReport();
        assertNotSame(original, selectedForView);
        assertEquals("Original", selectedForView.getTitle());
        assertEquals("Detail", selectedForView.getDetail());

        bean.prepareUpdate(0);
        ReportDto selectedForUpdate = bean.getSelectedReport();
        selectedForUpdate.setTitle("Updated");
        selectedForUpdate.setDetail("Changed");
        bean.update();

        assertEquals("Updated", repository.findByIndex(0).getTitle());
        assertEquals("Changed", repository.findByIndex(0).getDetail());
        assertSame(repository.findAll(), bean.getReports());
        assertNull(bean.getSelectedReport());

        bean.setSelectedReport(new ReportDto("Manual", "Selection"));
        assertEquals("Manual", bean.getSelectedReport().getTitle());

        bean.delete(1);
        assertEquals(1, bean.getReports().size());
    }

    @Test
    void handlesMissingSelection() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportListBean bean = new ReportListBean();
        setField(bean, "reportRepository", repository);

        bean.prepareView(0);
        assertNull(bean.getSelectedReport());

        bean.prepareUpdate(-1);
        assertNull(bean.getSelectedReport());

        bean.update();
        assertNull(bean.getSelectedReport());
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
