package vision.karunamgoyal.vision;

public class Interest {
    boolean  Mathematics;
    boolean Biology;
    boolean GeneralKnowledge;
    boolean Economics;
    boolean Technology;
    boolean ComputerScience;
    boolean Cricket;
    boolean Football;
    boolean Badminton;
    boolean Geography;
    boolean Politics;
    boolean Accounts;

    public Interest(boolean mathematics, boolean biology, boolean generalKnowledge, boolean economics, boolean technology, boolean computerScience, boolean cricket, boolean football, boolean badminton, boolean geography, boolean politics, boolean accounts) {
        Mathematics = mathematics;
        Biology = biology;
        GeneralKnowledge = generalKnowledge;
        Economics = economics;
        Technology = technology;
        ComputerScience = computerScience;
        Cricket = cricket;
        Football = football;
        Badminton = badminton;
        Geography = geography;
        Politics = politics;
        Accounts = accounts;
    }

    public Interest() {

    }

    public boolean isMathematics() {
        return Mathematics;
    }

    public boolean isBiology() {
        return Biology;
    }

    public boolean isGeneralKnowledge() {
        return GeneralKnowledge;
    }

    public boolean isEconomics() {
        return Economics;
    }

    public boolean isTechnology() {
        return Technology;
    }

    public boolean isComputerScience() {
        return ComputerScience;
    }

    public boolean isCricket() {
        return Cricket;
    }

    public boolean isFootball() {
        return Football;
    }

    public boolean isBadminton() {
        return Badminton;
    }

    public boolean isGeography() {
        return Geography;
    }

    public boolean isPolitics() {
        return Politics;
    }

    public boolean isAccounts() {
        return Accounts;
    }
}
