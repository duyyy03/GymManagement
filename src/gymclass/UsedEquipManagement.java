/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymclass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import mylib.MyTool;

/**
 *
 * @author MSI
 */
public class UsedEquipManagement {

    private List<UsedEquipInClass> useequips;
    private static final String USED_EQUIPMENT_FILE = "GymData/UsedEquipment.dat";

    public UsedEquipManagement() {
        useequips = new ArrayList<>();
        loadUsedEquipments();
    }

    private void loadUsedEquipments() {
        List<String> lines = MyTool.readLinesFromFile(USED_EQUIPMENT_FILE);
        useequips.clear();
        for (String line : lines) {
            if (line.trim().isEmpty()) {
                continue; // Skip empty lines
            }
            try {
                useequips.add(new UsedEquipInClass(line));
            } catch (IllegalArgumentException e) {
                System.err.println("Error loading equipment: " + e.getMessage());
            }
        }
    }

    private void saveUsedEquipments() {
        List<String> lines = new ArrayList<>();
        for (UsedEquipInClass usedequip : useequips) {
            lines.add(usedequip.toString());
        }
        MyTool.writeFile(USED_EQUIPMENT_FILE, lines);
    }

    private List<UsedEquipInClass> searchUsedEquipmentByID(String eID) {
        return useequips.stream()
                .filter(equip -> equip.geteID().equals(eID))
                .collect(Collectors.toList());
    }

    private List<UsedEquipInClass> searchUsedEquipmentContainingEquipID(String id) {
        return useequips.stream()
                .filter(equip -> equip.geteID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }

    private List<UsedEquipInClass> searchUsedEquipmentContainingClaID(String id) {
        return useequips.stream()
                .filter(equip -> equip.getcID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean checkDuplicated(String cID, String eID) {
        for (UsedEquipInClass usedEquipInClass : useequips) {
            if (usedEquipInClass.getcID().equals(cID) && usedEquipInClass.geteID().equals(eID)) {
                return true;
            }
        }
        return false;
    }

    public void addUsedEquipment() {
        Scanner scanner = new Scanner(System.in);

        String cID = MyTool.readPattern("Enter Class ID (CLAxxxx): ", UsedEquipInClass.CLASS_ID_PATTERN);
        String eID;
        do {
            eID = MyTool.readPattern("Enter Equipment ID (EQUyyyyy): ", UsedEquipInClass.EQUIPMENT_ID_PATTERN);
            if (checkDuplicated(cID, eID)) {
                System.out.println("  [ Equipment ID already exists in this class. Please enter a different ID ] ");
            }
        } while (checkDuplicated(cID, eID));
        String eName = MyTool.readAlphaString("Enter full name: ");
        int quantity = MyTool.readNonBlankInt("Enter quantity: ");
        UsedEquipInClass usedEquipInClass = new UsedEquipInClass(cID, eID, eName, quantity);
        useequips.add(usedEquipInClass);
        saveUsedEquipments();
        System.out.println(" [ Equipment added successfully ] ");
    }

    public void deleteUsedEquipment() {
        Scanner scanner = new Scanner(System.in);

        String searchID = MyTool.readNonBlank(" -> Search Class By ID: ").toUpperCase();
        List<UsedEquipInClass> foundEquipments = searchUsedEquipmentContainingClaID(searchID);

        if (foundEquipments.isEmpty()) {
            System.out.println(" [No Class found with the specified ID] ");
            return;
        }
        System.out.println(" -> Class(es) found: ");
        foundEquipments.forEach(System.out::println);

        String removeID = MyTool.readPattern(" -> Enter the Equipment ID to delete (EQUyyyyy): ", UsedEquipInClass.EQUIPMENT_ID_PATTERN).toUpperCase();
        List<UsedEquipInClass> equipmentsToRemove = searchUsedEquipmentByID(removeID);

        if (equipmentsToRemove.isEmpty()) {
            System.out.println(" [ No equipment found with the given ID ] ");
            return;
        }

        UsedEquipInClass equipmentToRemove = equipmentsToRemove.get(0);

        System.out.print(" -> Enter 'Y' to confirm delete or any key to cancel: ");
        String confirmDelete = MyTool.sc.nextLine().trim().toUpperCase();
        if (!confirmDelete.equals("Y")) {
            System.out.println(" [ Deletion canceled ]");
            return;
        }

        if (equipmentToRemove.getQuantity() > 1) {
            equipmentToRemove.setQuantity(equipmentToRemove.getQuantity() - 1);
            System.out.println(" [ Quantity decreased by 1. New quantity: " + equipmentToRemove.getQuantity() + " ]");
        } else {
            useequips.remove(equipmentToRemove);
            System.out.println(" [ Equipment removed successfully ]");
        }
        saveUsedEquipments();
    }

    public void sortAndDisplayUsedEquips() {
        useequips.sort(UsedEquipInClass.compareByID);
        System.out.println(" < SORTED USED EQUIPMENT LIST > ");
        System.out.println(" [Class ID;  Equipment ID;  NAME;  Quantity] ");
        for (UsedEquipInClass equip : useequips) {
            System.out.println(equip);
        }
    }
}
