package vision.karunamgoyal.vision;

public class Interest {
    boolean mathematics;
    boolean biology;
    boolean generalKnowledge;
    boolean economics;
    boolean technology;
    boolean computerScience;
    boolean cricket;
    boolean football;
    boolean badminton;
    boolean geography;
    boolean politics;
    boolean accounts;

    public Interest(boolean mathematics, boolean biology, boolean generalKnowledge, boolean economics, boolean technology, boolean computerScience, boolean cricket, boolean football, boolean badminton, boolean geography, boolean politics, boolean accounts) {
        this.mathematics = mathematics;
        this.biology = biology;
        this.generalKnowledge = generalKnowledge;
        this.economics = economics;
        this.technology = technology;
        this.computerScience = computerScience;
        this.cricket = cricket;
        this.football = football;
        this.badminton = badminton;
        this.geography = geography;
        this.politics = politics;
        this.accounts = accounts;
    }
    public Interest()
    {

    }

    public boolean isMathematics() {
        return mathematics;
    }

    public boolean isBiology() {
        return biology;
    }

    public boolean isGeneralKnowledge() {
        return generalKnowledge;
    }

    public boolean isEconomics() {
        return economics;
    }

    public boolean isTechnology() {
        return technology;
    }

    public boolean isComputerScience() {
        return computerScience;
    }

    public boolean isCricket() {
        return cricket;
    }

    public boolean isFootball() {
        return football;
    }

    public boolean isBadminton() {
        return badminton;
    }

    public boolean isGeography() {
        return geography;
    }

    public boolean isPolitics() {
        return politics;
    }

    public boolean isAccounts() {
        return accounts;
    }
}

