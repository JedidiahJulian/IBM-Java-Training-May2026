package day9.org.eclipse.jakarta.backingbean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.List;

import day9.org.eclipse.jakarta.dto.ReportDto;
import day9.org.eclipse.jakarta.infrastracture.repository.ReportRepository;
import org.junit.jupiter.api.Test;

class ReportListBeanTest {

    @Test
    void testInit() throws Exception {
        ReportRepository repository = new ReportRepository();
        repository.create(new ReportDto("Original", "Detail"));
        ReportListBean bean = newBean(repository);

        bean.init();

        assertSame(repository.findAll(), bean.getReports());
    }

    @Test
    void testSetReports() {
        ReportListBean bean = new ReportListBean();
        List<ReportDto> customReports = List.of(new ReportDto("Custom", "List"));

        bean.setReports(customReports);

        assertSame(customReports, bean.getReports());
    }

    @Test
    void testRead() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportDto original = new ReportDto("Original", "Detail");
        repository.create(original);
        ReportListBean bean = newBean(repository);

        bean.init();

        bean.prepareView(0);

        ReportDto selectedForView = bean.getSelectedReport();
        assertNotSame(original, selectedForView);
        assertEquals("Original", selectedForView.getTitle());
        assertEquals("Detail", selectedForView.getDetail());
    }

    @Test
    void testSetSelectedReport() {
        ReportListBean bean = new ReportListBean();

        bean.setSelectedReport(new ReportDto("Manual", "Selection"));

        assertEquals("Manual", bean.getSelectedReport().getTitle());
        assertEquals("Selection", bean.getSelectedReport().getDetail());
    }

    @Test
    void testPrepareUpdate() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportDto original = new ReportDto("Original", "Detail");
        repository.create(original);
        ReportListBean bean = newBean(repository);

        bean.prepareUpdate(0);

        ReportDto selectedForView = bean.getSelectedReport();
        assertNotSame(original, selectedForView);
        assertEquals("Original", selectedForView.getTitle());
        assertEquals("Detail", selectedForView.getDetail());
    }

    @Test
    void testUpdate() throws Exception {
        ReportRepository repository = new ReportRepository();
        repository.create(new ReportDto("Original", "Detail"));
        ReportListBean bean = newBean(repository);
        bean.init();

        bean.prepareUpdate(0);
        ReportDto selectedForUpdate = bean.getSelectedReport();
        selectedForUpdate.setTitle("Updated");
        selectedForUpdate.setDetail("Changed");

        bean.update();

        assertEquals("Updated", repository.findByIndex(0).getTitle());
        assertEquals("Changed", repository.findByIndex(0).getDetail());
        assertSame(repository.findAll(), bean.getReports());
        assertNull(bean.getSelectedReport());
    }

    @Test
    void testDelete() throws Exception {
        ReportRepository repository = new ReportRepository();
        repository.create(new ReportDto("Original", "Detail"));
        repository.create(new ReportDto("Other", "Detail"));
        ReportListBean bean = newBean(repository);
        bean.init();

        bean.delete(1);

        assertEquals(1, bean.getReports().size());
        assertEquals("Original", bean.getReports().get(0).getTitle());
        assertNull(repository.findByIndex(1));
    }

    @Test
    void testReadMissingReport() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportListBean bean = newBean(repository);

        bean.prepareView(0);

        assertNull(bean.getSelectedReport());
    }

    @Test
    void testPrepareUpdateMissingReport() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportListBean bean = newBean(repository);

        bean.prepareUpdate(-1);

        assertNull(bean.getSelectedReport());
    }

    @Test
    void testUpdateMissingSelection() throws Exception {
        ReportRepository repository = new ReportRepository();
        ReportListBean bean = newBean(repository);

        bean.update();

        assertNull(bean.getSelectedReport());
    }

    private static ReportListBean newBean(ReportRepository repository) throws Exception {
        ReportListBean bean = new ReportListBean();
        setField(bean, "reportRepository", repository);
        return bean;
    }

    private static void setField(Object target, String fieldName, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(target, value);
    }
}
