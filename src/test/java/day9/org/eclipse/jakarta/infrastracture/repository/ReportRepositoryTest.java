package day9.org.eclipse.jakarta.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import day9.org.eclipse.jakarta.dto.ReportDto;
import org.junit.jupiter.api.Test;

class ReportRepositoryTest {

    @Test
    void testCreate() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        ReportDto second = new ReportDto("Second", "Detail");

        repository.create(first);
        repository.create(second);

        assertEquals(2, repository.findAll().size());
        assertSame(first, repository.findByIndex(0));
        assertSame(second, repository.findByIndex(1));
    }

    @Test
    void testRead() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        repository.create(first);

        assertSame(first, repository.findByIndex(0));
        assertNull(repository.findByIndex(-1));
        assertNull(repository.findByIndex(1));
    }

    @Test
    void testUpdate() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        ReportDto second = new ReportDto("Second", "Detail");
        ReportDto replacement = new ReportDto("Replacement", "Updated");
        repository.create(first);
        repository.create(second);

        repository.update(0, replacement);

        assertSame(replacement, repository.findByIndex(0));
        assertSame(second, repository.findByIndex(1));
    }

    @Test
    void testUpdateInvalidIndex() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        ReportDto replacement = new ReportDto("Replacement", "Updated");
        repository.create(first);

        repository.update(-1, replacement);
        assertNull(repository.findByIndex(2));
        repository.update(1, replacement);

        assertSame(first, repository.findByIndex(0));
    }

    @Test
    void testDelete() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        ReportDto second = new ReportDto("Second", "Detail");
        repository.create(first);
        repository.create(second);

        repository.delete(1);

        assertEquals(1, repository.findAll().size());
        assertSame(first, repository.findByIndex(0));
    }

    @Test
    void testDeleteInvalidIndex() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        repository.create(first);

        repository.delete(-1);
        repository.delete(1);

        assertEquals(1, repository.findAll().size());
        assertSame(first, repository.findByIndex(0));
    }
}
