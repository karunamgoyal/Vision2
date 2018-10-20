package vision.karunamgoyal.vision;

public class Study {
    private String studyTag;
    private String studyName;
    private String studyUrl;
    private String studyDesc;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getStudyTag() {
        return studyTag;
    }

    public String getStudyName() {
        return studyName;
    }

    public String getStudyUrl() {
        return studyUrl;
    }

    public String getStudyDesc() {
        return studyDesc;
    }

    public Study(String studyTag, String studyName, String studyUrl, String studyDesc) {

        this.studyTag = studyTag;
        this.studyName = studyName;
        this.studyUrl = studyUrl;
        this.studyDesc = studyDesc;
    }

    public Study() {

    }
}
