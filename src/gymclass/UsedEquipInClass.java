package gymclass;

import java.util.Comparator;

public class UsedEquipInClass {

    public static final String SEPARATOR = "; ";
    public static final String CLASS_ID_PATTERN = "CLA\\d{5}";
    public static final String EQUIPMENT_ID_PATTERN = "EQU\\d{5}";

    private String cID;
    private String eID;
    private String eName;
    private int quantity;

    public UsedEquipInClass(String cID, String eID, String eName, int quantity) {
        this.cID = cID;
        this.eID = eID;
        this.eName = eName;
        this.quantity = quantity;
    }

    public UsedEquipInClass(String line) {
        String[] parts = line.split(SEPARATOR);
        cID = parts[0].trim();
        eID = parts[1].trim();
        eName = parts[2].trim();
        quantity = Integer.parseInt(parts[3].trim());
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String geteID() {
        return eID;
    }

    public void seteID(String eID) {
        this.eID = eID;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
     public static Comparator<UsedEquipInClass> compareByID = Comparator
            .comparing(UsedEquipInClass::getcID)
            .thenComparing(UsedEquipInClass::geteID);


    @Override
    public String toString() {
        return cID + SEPARATOR + eID + SEPARATOR + eName + SEPARATOR + quantity;
    }
}
