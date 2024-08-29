/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import mylib.MyTool;

/**
 *
 * @author MSI
 */
public class EquipmentManagement {

    private List<Equipment> equipments;
    private static final String EQUIPMENT_FILE = "GymData/equipment.dat";

    public EquipmentManagement() {
        equipments = new ArrayList();
        loadEquipments();
    }

    private void loadEquipments() {
        List<String> lines = MyTool.readLinesFromFile(EQUIPMENT_FILE);
        equipments.clear();
        for (String line : lines) {
            equipments.add(new Equipment(line));
        }
    }

    private void saveEquipments() {
        List<String> lines = new ArrayList<>();
        for (Equipment equipment : equipments) {
            lines.add(equipment.toString());
        }
        MyTool.writeFile(EQUIPMENT_FILE, lines);
    }

    private List<Equipment> searchEquipmentByID(String eID) {
        return equipments.stream()
                .filter(equipment -> equipment.geteID().equals(eID))
                .collect(Collectors.toList());
    }

    private List<Equipment> searchEquipmentContainingID(String eID) {
        return equipments.stream()
                .filter(e -> e.geteID().toUpperCase().contains(eID.toUpperCase()))
                .collect(Collectors.toList());
    }

    private boolean checkDuplicated(String eID) {
        for (Equipment equipment : equipments) {
            if (equipment.geteID().equals(eID)) {
                return true;
            }
        }
        return false;
    }

    public void addEquipment() {
        Scanner sc = new Scanner(System.in);

        String eID;
        do {
            eID = MyTool.readPattern("Enter Equipment ID (EQUyyyyy): ", Equipment.ID_PATTERN);
            if (checkDuplicated(eID)) {
                System.out.println(" [ Equipment ID already exists. Please enter a different ID ] ");
            }
        } while (checkDuplicated(eID));

        String name = MyTool.readAlphaString("Enter name: ");
        String type = MyTool.readAlphaString("Enter type: ");
        int quantity = MyTool.readNonBlankInt("Enter quantity: ");
        LocalDate mfgDate = MyTool.readValidDateNotEmpty("Enter manufacturing date (dd-MM-yyyy): ", "dd-MM-yyyy");
        String prdSite = MyTool.readAlphaString("Enter production site: ");
        String condition = MyTool.readAlphaString("Enter condition: ");
        String note = MyTool.readOptionalBlank("Enter note: ");
        Equipment equipment = new Equipment(eID, name, type, quantity, mfgDate, prdSite, condition, note);
        equipments.add(equipment);
        saveEquipments();
        System.out.println("[ Equipment added successfully ] ");
    }

    public void sortAndDisplayEquipment() {
        equipments.sort(Comparator.comparing(Equipment::geteName));

        System.out.println(" < SORTEN EQUIPMENT LIST > ");
        System.out.println(" [ID; Name; Type; Quantity; Manufacturing Date; Production Site; Condition; Note] ");
        for (Equipment equipment : equipments) {
            System.out.println(equipment);
        }
    }

    public void viewAndUpdateEquipment() {
        String id = MyTool.readNonBlank(" -> Search equipment By ID: ").toUpperCase();
        List<Equipment> foundEquipments = searchEquipmentContainingID(id);

        if (foundEquipments.isEmpty()) {
            System.out.println(" [ No equipment found with the specified ID ] ");
            return;
        }
        System.out.println(" -> Equipment(s) found: ");
        foundEquipments.forEach(System.out::println);

        String updateID = MyTool.readPattern(" -> Enter the Equipment ID to update (EQUyyyyy): ", Equipment.ID_PATTERN).toUpperCase();
        List<Equipment> equipmentsToUpdate = searchEquipmentByID(updateID);

        if (equipmentsToUpdate.isEmpty()) {
            System.out.println(" [No equipment found with the given ID] ");
            return;
        }

        Equipment equipmentToUpdate = equipmentsToUpdate.get(0);

        String newEName = MyTool.readOptionalAlphaString("Enter the new name (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newEName, equipmentToUpdate::seteName);

        String newType = MyTool.readOptionalAlphaString("Enter the new type (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newType, equipmentToUpdate::setType);

        Integer newQuantity = MyTool.readOptionalInt("Enter the new quantity (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newQuantity, equipmentToUpdate::setQuantity);

        LocalDate newMfgDate = MyTool.readValidDate("Enter new manufaturing date (leave blank to keep current): ", "dd-MM-yyyy", true);
        MyTool.updateIfNotNullOrEmpty(newMfgDate, equipmentToUpdate::setMfgDate);

        String newPrdSite = MyTool.readOptionalAlphaString("Enter the new product site (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newPrdSite, equipmentToUpdate::setPrdSite);

        String newCondtion = MyTool.readOptionalAlphaString("Enter the new condition (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newCondtion, equipmentToUpdate::setCondition);

        String newNote = MyTool.readOptionalAlphaString("Enter the new condition (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newNote, equipmentToUpdate::setNote);

        saveEquipments();
        System.out.println(" [Equipment information updated successfully] ");
    }

    public void deleteEquipment() {
        String id = MyTool.readNonBlank(" -> Search Equipment By ID: ").toUpperCase();
        List<Equipment> foundEquipments = searchEquipmentContainingID(id);

        if (foundEquipments.isEmpty()) {
            System.out.println(" [ No equipment found with the specified ID ] ");
            return;
        }
        System.out.println(" -> Equipment(s) found: ");
        foundEquipments.forEach(System.out::println);

        String removeID = MyTool.readPattern(" -> Enter the Equipment ID to delete (EQUyyyyy): ", Equipment.ID_PATTERN).toUpperCase();
        List<Equipment> equipmentsToRemove = searchEquipmentByID(removeID);

        if (equipmentsToRemove.isEmpty()) {
            System.out.println(" [ No equipment found with the given ID ] ");
            return;
        }

        Equipment equipmentToRemove = equipmentsToRemove.get(0);

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
            equipments.remove(equipmentToRemove);
            System.out.println(" [ Equipment removed successfully ]");
        }
        saveEquipments();
    }

}
