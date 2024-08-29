package gymclass;

import java.util.Comparator;

public class RegisteredMember {

    public static final String SEPARATOR = ";  ";
    public static final String CLASS_ID_PATTERN = "CLA\\d{5}";
    public static final String MEMBER_ID_PATTERN = "GY\\d{4}";

    private String cID;
    private String mID;
    private String fullname;

    public RegisteredMember(String cID, String mID, String fullname) {
        this.cID = cID;
        this.mID = mID;
        this.fullname = fullname;
    }

    public RegisteredMember(String line) {
        String[] parts = line.split(SEPARATOR);
        cID = parts[0].trim();
        mID = parts[1].trim();
        fullname = parts[2].trim();
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getmID() {
        return mID;
    }

    public void setmID(String mID) {
        this.mID = mID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public static Comparator<RegisteredMember> compareByID = Comparator
            .comparing(RegisteredMember::getcID)
            .thenComparing(RegisteredMember::getmID);

    @Override
    public String toString() {
        return cID + SEPARATOR + mID + SEPARATOR + fullname;
    }
}
