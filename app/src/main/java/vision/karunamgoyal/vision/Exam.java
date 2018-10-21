package vision.karunamgoyal.vision;

public class Exam {
    private String examName;
    private String examConductor;
    private String examURL;
    private String id;
    private String examTag;

    public String getExamTag() {
        return examTag;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Exam(String examName, String examConductor, String examURL, String examTag) {
        this.examName = examName;
        this.examConductor = examConductor;
        this.examURL = examURL;
        this.examTag = examTag;
    }

    public Exam() {

    }

    public String getExamName() {
        return examName;
    }

    public String getExamConductor() {
        return examConductor;
    }

    public String getExamURL() {
        return examURL;
    }
}
