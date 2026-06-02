package org.eclipse.jakarta.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class ReportDtoTest {

    @Test
    void defaultConstructorAndSettersStoreValues() {
        ReportDto report = new ReportDto();

        assertNull(report.getTitle());
        assertNull(report.getDetail());

        report.setTitle("Title");
        report.setDetail("Detail");

        assertEquals("Title", report.getTitle());
        assertEquals("Detail", report.getDetail());
    }

    @Test
    void allArgsConstructorStoresValues() {
        ReportDto report = new ReportDto("Incident", "Resolved");

        assertEquals("Incident", report.getTitle());
        assertEquals("Resolved", report.getDetail());
    }
}
