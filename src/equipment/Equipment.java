/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

/**
 *
 * @author MSI
 */
public class Equipment {

    public static final String SEPARATOR = "; ";
    public static final String ID_PATTERN = "EQU\\d{5}";

    private String eID;
    private String eName;
    private String type;
    private int quantity;
    private LocalDate mfgDate;
    private String prdSite;
    private String condition;
    private String note;

    public Equipment(String eID, String eName, String type, int quantity, LocalDate mfgDate, String prdSite, String condition, String note) {
        this.eID = eID;
        this.eName = eName;
        this.type = type;
        this.quantity = quantity;
        this.mfgDate = mfgDate;
        this.prdSite = prdSite;
        this.condition = condition;
        this.note = note;
    }

    public Equipment(String line) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String[] parts = line.split("" + this.SEPARATOR);
        eID = parts[0].trim();
        eName = parts[1].trim();
        type = parts[2].trim();
        quantity = Integer.parseInt(parts[3].trim());
        String mfgDateStr = parts[4].trim();
        if (!mfgDateStr.isEmpty()) {
            mfgDate = LocalDate.parse(mfgDateStr, formatter);
        } else {
            throw new IllegalArgumentException("Manufacturing date cannot be empty");
        }
        prdSite = parts[5].trim();
        condition = parts[6].trim();
        note = parts.length > 7 ? parts[7].trim() : "";
    }

    public String geteID() {
        return eID;
    }

    public void seteID(String eID) {
        if (!eID.matches(ID_PATTERN)) {
            throw new IllegalArgumentException("Invalid eID format: " + eID);
        }
        this.eID = eID;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getMfgDate() {
        return mfgDate;
    }

    public void setMfgDate(LocalDate mfgDate) {
        this.mfgDate = mfgDate;
    }

    public String getPrdSite() {
        return prdSite;
    }

    public void setPrdSite(String prdSite) {
        this.prdSite = prdSite;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static Comparator<Equipment> compareByName = new Comparator<Equipment>() {
        @Override
        public int compare(Equipment e1, Equipment e2) {
            return e1.eName.compareTo(e2.eName);
        }
    };

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return eID + SEPARATOR + eName + SEPARATOR + type
                + SEPARATOR + quantity + SEPARATOR + (mfgDate != null ? mfgDate.format(formatter) : "")
                + SEPARATOR + prdSite + SEPARATOR + condition + SEPARATOR + (note != null ? note : "");
    }

}
