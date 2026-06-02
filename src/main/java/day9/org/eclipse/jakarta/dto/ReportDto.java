package day9.org.eclipse.jakarta.dto;

public class ReportDto {
    private Integer id;
    private String title;
    private String detail;

    public ReportDto() {}

    public ReportDto(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public ReportDto(Integer id, String title, String detail) {
        this.id = id;
        this.title = title;
        this.detail = detail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
