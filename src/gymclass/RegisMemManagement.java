/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import mylib.MyTool;

/**
 *
 * @author MSI
 */
public class RegisMemManagement {

    private List<RegisteredMember> regismembers;
    private static final String REGIS_MEMBER_FILE = "GymData/RegisMember.dat";

    private void loadRegisMembers() {
        List<String> lines = MyTool.readLinesFromFile(REGIS_MEMBER_FILE);
        regismembers.clear();

        for (String line : lines) {
            regismembers.add(new RegisteredMember(line));

        }
    }

    private void saveRegisMembers() {
        List<String> lines = new ArrayList<>();
        for (RegisteredMember member : regismembers) {
            lines.add(member.toString());
        }
        MyTool.writeFile(REGIS_MEMBER_FILE, lines);
    }

    public RegisMemManagement() {
        regismembers = new ArrayList<>();
        loadRegisMembers();
    }

    private List<RegisteredMember> searchRegisMemsByClaID(String cID) {
        return regismembers.stream()
                .filter(member -> member.getcID().equals(cID))
                .collect(Collectors.toList());
    }

    private List<RegisteredMember> searchRegisMemsContainingMemID(String id) {
        return regismembers.stream()
                .filter(member -> member.getmID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }

    private List<RegisteredMember> searchRegisMemsContainingClaID(String id) {
        return regismembers.stream()
                .filter(member -> member.getcID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean checkDuplicated(String cID, String mID) {
        for (RegisteredMember registeredMember : regismembers) {
            if (registeredMember.getcID().equals(cID) && registeredMember.getmID().equals(mID)) {
                return true;
            }
        }
        return false;
    }

    public void addRegisMember() {
        Scanner scanner = new Scanner(System.in);
        String cID = MyTool.readPattern("Enter Class ID (CLxxxx): ", RegisteredMember.CLASS_ID_PATTERN);
        String mID;
        do {
            mID = MyTool.readPattern("Enter Member ID (GYxxxx): ", RegisteredMember.MEMBER_ID_PATTERN);
            if (checkDuplicated(cID, mID)) {
                System.out.println(" [ Member ID already exists in this class. Please enter a different ID ] ");
            }
        } while (checkDuplicated(cID, mID));
        String fullname = MyTool.readAlphaString("Enter full name: ");
        RegisteredMember registeredMember = new RegisteredMember(cID, mID, fullname);
        regismembers.add(registeredMember);
        saveRegisMembers();
        System.out.println(" [ Member added successfully ] ");
    }

    public void deleteRegisMember() {
        Scanner scanner = new Scanner(System.in);

        // Step 1: Search for GymClass
        String searchID = MyTool.readNonBlank(" -> Search Class By ID (CLAzzzzz): ").toUpperCase();
        List<RegisteredMember> foundMembers = searchRegisMemsContainingClaID(searchID);

        if (foundMembers.isEmpty()) {
            System.out.println(" [ No class found with the specified ID ] ");
            return;
        }

        System.out.println(" -> Class(s) found: ");
        foundMembers.forEach(System.out::println);
        String gymClassID = searchID;
        String deleteID = MyTool.readPattern(" -> Enter Member ID To Delete (GYxxxx): ", RegisteredMember.MEMBER_ID_PATTERN).toUpperCase();

        List<RegisteredMember> membersToDelete = foundMembers.stream()
                .filter(member -> member.getmID().equalsIgnoreCase(deleteID))
                .collect(Collectors.toList());

        if (membersToDelete.isEmpty()) {
            System.out.println(" [ No member found with the given ID in the searched GymClass ] ");
            return;
        }

        System.out.print(" Enter 'Y' to confirm deletion or any other key to cancel: ");
        String confirmDelete = MyTool.sc.nextLine().trim().toUpperCase();
        if (!confirmDelete.equals("Y")) {
            System.out.println(" [ Deletion canceled ] ");
            return;
        }
        regismembers.removeAll(membersToDelete);
        saveRegisMembers();
        System.out.println(" [ Member deleted successfully ] ");
    }

    public void sortAndDisplayRegisMems() {
        regismembers.sort(RegisteredMember.compareByID);
        System.out.println(" < SORTED REGISTERED MEMBERS LIST > ");
        System.out.println(" [Class ID;  Member ID;  FullName] ");
        for (RegisteredMember member : regismembers) {
            System.out.println(member);
        }
    }
}
