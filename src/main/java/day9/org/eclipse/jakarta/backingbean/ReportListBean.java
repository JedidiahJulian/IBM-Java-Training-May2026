package day9.org.eclipse.jakarta.backingbean;

import java.io.Serializable;
import java.util.List;

import day9.org.eclipse.jakarta.dto.ReportDto;
import day9.org.eclipse.jakarta.infrastracture.repository.ReportRepository;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ReportListBean implements Serializable {
    private static final long serialVersionUID = 1L;
	
    private List<ReportDto> reports;
    private ReportDto selectedReport;
    private int selectedIndex = -1;

    @Inject
    private ReportRepository reportRepository;

    @PostConstruct
    public void init() {
        reports = reportRepository.findAll();
    }
    
    public List<ReportDto> getReports() {
        return reports;
    }

    public void setReports(List<ReportDto> reports) {
        this.reports = reports;
    }

    public ReportDto getSelectedReport() {
        return selectedReport;
    }

    public void setSelectedReport(ReportDto selectedReport) {
        this.selectedReport = selectedReport;
    }

    public void prepareView(int index) {
        selectedIndex = index;
        ReportDto report = reportRepository.findByIndex(index);
        selectedReport = copyReport(report);
    }

    public void prepareUpdate(int index) {
        selectedIndex = index;
        ReportDto report = reportRepository.findByIndex(index);
        selectedReport = copyReport(report);
    }

    public void update() {
        if (selectedReport == null || selectedReport.getId() == null) {
            return;
        }

        reportRepository.updateById(selectedReport.getId(), selectedReport);
        reports = reportRepository.findAll();
        selectedReport = null;
        selectedIndex = -1;
    }

    public void delete(int index) {
        reportRepository.delete(index);
        reports = reportRepository.findAll();
    }

    private ReportDto copyReport(ReportDto report) {
        if (report == null) {
            return null;
        }

        return new ReportDto(report.getId(), report.getTitle(), report.getDetail());
    }
}
