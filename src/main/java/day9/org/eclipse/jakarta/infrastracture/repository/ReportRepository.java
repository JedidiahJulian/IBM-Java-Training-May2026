package day9.org.eclipse.jakarta.infrastracture.repository;

import java.util.ArrayList;
import java.util.List;

import day9.org.eclipse.jakarta.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
    private List<ReportDto> reports = new ArrayList<>();

    public List<ReportDto> findAll() {
        return reports;
    }

    public void create(ReportDto report) {
        reports.add(report);
    }

    public ReportDto findByIndex(int index) {
        if (index < 0 || index >= reports.size()) {
            return null;
        }

        return reports.get(index);
    }

    public void update(int index, ReportDto report) {
        if (index < 0 || index >= reports.size()) {
            return;
        }

        reports.set(index, report);
    }

    public void delete(int index) {
        if (index < 0 || index >= reports.size()) {
            return;
        }

        reports.remove(index);
    }
}
