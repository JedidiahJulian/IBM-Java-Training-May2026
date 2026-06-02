package day9.org.eclipse.jakarta.infrastracture.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import day9.org.eclipse.jakarta.database.DBConnect;
import day9.org.eclipse.jakarta.dto.ReportDto;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportRepository {
    private DBConnect dbConnect = new DBConnect();

    public List<ReportDto> findAll() {
        List<ReportDto> reports = new ArrayList<>();
        String sql = "SELECT id, title, detail FROM day9.reports ORDER BY id";

        try (
            Connection conn = dbConnect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                reports.add(mapReport(rs));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return reports;
    }

    public void create(ReportDto report) {
        String sql = "INSERT INTO day9.reports (title, detail) VALUES (?, ?)";

        try (
            Connection conn = dbConnect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, report.getTitle());
            stmt.setString(2, report.getDetail());
            stmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ReportDto findById(int id) {
        String sql = "SELECT id, title, detail FROM day9.reports WHERE id = ?";

        try (
            Connection conn = dbConnect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapReport(rs);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public ReportDto findByIndex(int index) {
        List<ReportDto> reports = findAll();

        if (index < 0 || index >= reports.size()) {
            return null;
        }

        return reports.get(index);
    }

    public void update(int index, ReportDto report) {
        ReportDto savedReport = findByIndex(index);

        if (savedReport == null || savedReport.getId() == null) {
            return;
        }

        updateById(savedReport.getId(), report);
    }

    public void updateById(int id, ReportDto report) {
        String sql = "UPDATE day9.reports SET title = ?, detail = ? WHERE id = ?";

        try (
            Connection conn = dbConnect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, report.getTitle());
            stmt.setString(2, report.getDetail());
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int index) {
        ReportDto report = findByIndex(index);

        if (report == null || report.getId() == null) {
            return;
        }

        deleteById(report.getId());
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM day9.reports WHERE id = ?";

        try (
            Connection conn = dbConnect.connect();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ReportDto mapReport(ResultSet rs) throws Exception {
        ReportDto report = new ReportDto();
        report.setId(rs.getInt("id"));
        report.setTitle(rs.getString("title"));
        report.setDetail(rs.getString("detail"));

        return report;
    }
}
