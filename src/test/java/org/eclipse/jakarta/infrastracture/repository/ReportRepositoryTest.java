package org.eclipse.jakarta.infrastracture.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.eclipse.jakarta.dto.ReportDto;
import org.junit.jupiter.api.Test;

class ReportRepositoryTest {

    @Test
    void managesReportsAndIgnoresInvalidIndexes() {
        ReportRepository repository = new ReportRepository();
        ReportDto first = new ReportDto("First", "Detail");
        ReportDto second = new ReportDto("Second", "Detail");
        ReportDto replacement = new ReportDto("Replacement", "Updated");

        assertTrue(repository.findAll().isEmpty());
        assertNull(repository.findByIndex(-1));
        assertNull(repository.findByIndex(0));

        repository.create(first);
        repository.create(second);

        assertSame(first, repository.findByIndex(0));
        assertSame(second, repository.findByIndex(1));
        assertNull(repository.findByIndex(2));

        repository.update(-1, replacement);
        repository.update(2, replacement);
        assertSame(first, repository.findByIndex(0));
        assertSame(second, repository.findByIndex(1));

        repository.update(0, replacement);
        assertSame(replacement, repository.findByIndex(0));

        repository.delete(-1);
        repository.delete(2);
        assertEquals(2, repository.findAll().size());

        repository.delete(1);
        assertEquals(1, repository.findAll().size());
        assertSame(replacement, repository.findByIndex(0));
    }
}
