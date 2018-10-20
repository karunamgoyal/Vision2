package vision.karunamgoyal.vision;

public class Institutes {
    private String institureName;
    private String instituteTag;
    private String instituteDesc;
    private String Url;

    public String getUrl() {
        return Url;
    }

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getInstitureName() {
        return institureName;
    }

    public String getInstituteTag() {
        return instituteTag;
    }

    public String getInstituteDesc() {
        return instituteDesc;
    }

    public Institutes() {

    }

    public Institutes(String institureName, String instituteTag, String instituteDesc,String Url) {

        this.institureName = institureName;
        this.instituteTag = instituteTag;
        this.instituteDesc = instituteDesc;
        this.Url=Url;
    }
}