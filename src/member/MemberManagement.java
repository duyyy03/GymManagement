/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package member;

import member.Member;
import mylib.MyTool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author MSI
 */
public class MemberManagement {

    private List<Member> members;
    private static final String MEMBER_FILE = "GymData/members.dat";

    public MemberManagement() {
        members = new ArrayList<>();
        loadMembers();
    }

    private void loadMembers() {
        List<String> lines = MyTool.readLinesFromFile(MEMBER_FILE);
        members.clear();
        for (String line : lines) {
            members.add(new Member(line));
        }
    }

    private void saveMembers() {
        List<String> lines = new ArrayList<>();
        for (Member member : members) {
            lines.add(member.toString());
        }
        MyTool.writeFile(MEMBER_FILE, lines);
    }

    private List<Member> searchMembersByID(String mID) {
        return members.stream()
                .filter(member -> member.getmID().equals(mID))
                .collect(Collectors.toList());
    }

    private List<Member> searchMembersContainingID(String id) {
        return members.stream()
                .filter(m -> m.getmID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean checkDuplicated(String mID) {
        for (Member member : members) {
            if (member.getmID().equals(mID)) {
                return true;
            }
        }
        return false;
    }

    public void createMember() {
        Scanner sc = new Scanner(System.in);
        String mID;
        do {
            mID = MyTool.readPattern("Enter Member ID (GYxxxx): ", Member.ID_PATTERN);
            if (checkDuplicated(mID)) {
                System.out.println(" [Member ID already exists. Please enter a different ID] ");
            }
        } while (checkDuplicated(mID));
        String fullname = MyTool.readAlphaString("Enter full name: ");
        String address = MyTool.readNonBlank("Enter address: ");
        String phoneNumber = MyTool.readPattern("Enter Phone Number: ", Member.PHONE_PATTERN);
        String email = MyTool.readPattern("Enter email: ", Member.EMAIL_PATTERN);
        String membershipType = MyTool.readOptionalBlank("Enter membership type: ");
        LocalDate msStart = MyTool.readValidDate("Enter membership start date (dd-MM-yyyy): ", "dd-MM-yyyy", true);
        LocalDate msEnd = MyTool.readValidDate("Enter membership end date (dd-MM-yyyy): ", "dd-MM-yyyy", true);
        Member member = new Member(mID, fullname, address, phoneNumber, email, membershipType, msStart, msEnd);
        members.add(member);
        saveMembers();
        System.out.println(" [Member added successfully] ");
    }

    public void viewAndUpdateMember() {
        String id = MyTool.readNonBlank(" -> Search Member By ID:  ").toUpperCase();
        List<Member> foundMembers = searchMembersContainingID(id);

        if (foundMembers.isEmpty()) {
            System.out.println(" [No member found with the specified ID] ");
            return;
        }
        System.out.println(" -> Member(s) found: ");
        foundMembers.forEach(System.out::println);

        String updateID = MyTool.readPattern(" -> Enter the Member ID to update (GYxxxx): ", Member.ID_PATTERN).toUpperCase();
        List<Member> membersToUpdate = searchMembersByID(updateID);

        if (membersToUpdate.isEmpty()) {
            System.out.println(" [No member found with the given ID] ");
            return;
        }

        Member memberToUpdate = membersToUpdate.get(0);
        String newFullName = MyTool.readOptionalAlphaString("Enter new full name (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newFullName, memberToUpdate::setFullname);

        String newAddress = MyTool.readOptionalBlank("Enter new address (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newAddress, memberToUpdate::setAddress);
        while (true) {
            
            String newPhoneNumber = MyTool.readOptionalBlank("Enter new phone number (leave blank to keep current): ");
            if (newPhoneNumber.isEmpty() || MyTool.validStr(newPhoneNumber, Member.PHONE_PATTERN)) {
                MyTool.updateIfNotNullOrEmpty(newPhoneNumber, memberToUpdate::setPhoneNumber);
                break;
            } else {
                System.out.println("[Invalid phone number format]");
            }
        }
        while (true) {
            String newEmail = MyTool.readOptionalBlank("Enter new email (leave blank to keep current): ");
            if (newEmail.isEmpty() || MyTool.validStr(newEmail, Member.EMAIL_PATTERN)) {
                MyTool.updateIfNotNullOrEmpty(newEmail, memberToUpdate::setEmail);
                break;
            } else {
                System.out.println(" [Invalid email format] ");
            }
        }

        String newMembershipType = MyTool.readOptionalAlphaString("Enter new membership type (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newMembershipType, memberToUpdate::setMembershipType);

        LocalDate newStartDate = MyTool.readValidDate("Enter new start date (leave blank to keep current): ", "dd-MM-yyyy", true);
        MyTool.updateIfNotNullOrEmpty(newStartDate, memberToUpdate::setMsStart);

        LocalDate newEndDate = MyTool.readValidDate("Enter new end date (leave blank to keep current): ", "dd-MM-yyyy", true);
        MyTool.updateIfNotNullOrEmpty(newEndDate, memberToUpdate::setMsEnd);

        saveMembers();
        System.out.println(" [Member information updated successfully] ");
    }

    public void deleteMember() {
        String searchID = MyTool.readNonBlank(" -> Search Member By ID ").toUpperCase();
        List<Member> foundMembers = searchMembersContainingID(searchID);

        if (foundMembers.isEmpty()) {
            System.out.println(" [No member found with the specified ID] ");
            return;
        }

        System.out.println(" -> Member found: ");
        foundMembers.forEach(System.out::println);

        String deleteID = MyTool.readPattern(" -> Enter Member ID To Delete (GYxxxx): ", Member.ID_PATTERN).toUpperCase();
        List<Member> membersToDelete = searchMembersByID(deleteID);

        if (membersToDelete.isEmpty()) {
            System.out.println(" [No member found with the given ID] ");
            return;
        }

        Member memberToDelete = membersToDelete.get(0);

        System.out.print(" -> Enter 'Y' to confirm deletion or any other key to cancel: ");
        String confirmDelete = MyTool.sc.nextLine().trim().toUpperCase();
        if (!confirmDelete.equals("Y")) {
            System.out.println(" [Deletion canceled] ");
            return;
        }

        members.remove(memberToDelete);
        saveMembers();
        System.out.println(" [Member deleted successfully] ");
    }

    public void sortAndDisplayMembers() {
        // Sắp xếp danh sách các thành viên theo tên
        members.sort(Comparator.comparing(Member::getFullname));
        // Hiển thị danh sách các thành viên sau khi sắp xếp
        System.out.println(" < SORTEN MEMBER LIST > ");
        System.out.println("[ ID;  FullName;  Address;  PhoneNumber;  Email;  MembershipType;  StartDate; EndDate ]");
        for (Member member : members) {
            System.out.println(member);
        }
    }
}
