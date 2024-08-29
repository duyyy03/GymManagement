/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author MSI
 */
public class Member {

    public static final String SEPARATOR = ";  ";

    public static final String ID_PATTERN = "GY\\d{4}";
    public static final String PHONE_PATTERN = "\\d{9}|\\d{11}";
    public static final String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private String mID;
    private String fullname;
    private String address;
    private String phoneNumber;
    private String email;
    private String membershipType;
    private LocalDate msStart;
    private LocalDate msEnd;

    public Member(String mID, String fullname, String address, String phoneNumber, String email, String membershipType, LocalDate msStart, LocalDate msEnd) {
        this.mID = mID;
        this.fullname = fullname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.membershipType = membershipType;
        this.msStart = msStart;
        this.msEnd = msEnd;
    }
    
    

    public Member(String line) {

        String[] parts = line.split("" + this.SEPARATOR);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        mID = parts[0].trim();
        fullname = parts[1].trim();
        address = parts[2].trim();
        phoneNumber = parts[3].trim();
        email = parts[4].trim();
        if (parts.length > 5 && !parts[5].trim().isEmpty()) {
            membershipType = parts[5].trim();
        } else {
            membershipType = "";
        }

        if (parts.length > 6) {
            String startDateStr = parts[6].trim();
            if (!startDateStr.isEmpty()) {
                try {
                    msStart = LocalDate.parse(startDateStr, formatter);
                } catch (DateTimeParseException e) {
                    msStart = null;
                }
            }
        } else {
            this.msStart = null;
        }

        if (parts.length > 7) {
            String endDateStr = parts[7].trim();
            if (!endDateStr.isEmpty()) {
                try {
                    msEnd = LocalDate.parse(endDateStr, formatter);
                } catch (DateTimeParseException e) {
                    msEnd = null;
                }
            }
        } else {
            this.msEnd = null;
        }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public LocalDate getMsStart() {
        return msStart;
    }

    public void setMsStart(LocalDate msStart) {
        this.msStart = msStart;
    }

    public LocalDate getMsEnd() {
        return msEnd;
    }

    public void setMsEnd(LocalDate msEnd) {
        this.msEnd = msEnd;
    }

    public static Comparator<Member> getCompareByName() {
        return compareByName;
    }

    public static void setCompareByName(Comparator<Member> compareByName) {
        Member.compareByName = compareByName;
    }

    public static Comparator<Member> compareByName = new Comparator<Member>() {
        @Override
        public int compare(Member m1, Member m2) {
            return m1.fullname.compareTo(m2.fullname);
        }
    };

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String membershipInfo = (membershipType != null && !membershipType.isEmpty()) ? membershipType : "";
        String startInfo = (msStart != null) ? msStart.format(formatter) : "";
        String endInfo = (msEnd != null) ? msEnd.format(formatter) : "";

        return mID + SEPARATOR + fullname + SEPARATOR + address
                + SEPARATOR + phoneNumber + SEPARATOR + email + SEPARATOR + membershipInfo
                + SEPARATOR + startInfo + SEPARATOR + endInfo;
    }

}
