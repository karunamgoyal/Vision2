package vision.karunamgoyal.vision;

public class RegisterAdminUser {
    private String name;

    private String mail;
    private String phno;
    private String userType;
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public RegisterAdminUser(){

    }
    public RegisterAdminUser(String name,String mail,String phno,String userType){
        this.name=name;


        this.mail=mail;
        this.phno=phno;
        this.userType=userType;


    }
    public String getName(){
        return this.name;
    }
    public String getMail(){
        return this.mail;
    }
    public String getPhno(){
        return this.phno;
    }
    public String  getUserType(){
        return this.userType;
    }


}
