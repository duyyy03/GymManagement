/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymclass;

import mylib.MyTool;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *
 * @author MSI
 */
public class GymClassManagement {

    private List<GymClass> gymClasses;
    private static final String CLASS_FILE = "GymData/GymClass.dat";

    public GymClassManagement() {
        gymClasses = new ArrayList<>();
        loadClasses();
    }

    private void loadClasses() {
        List<String> lines = MyTool.readLinesFromFile(CLASS_FILE);
        gymClasses.clear();
        for (String line : lines) {
            gymClasses.add(new GymClass(line));
        }
    }

    private void saveClasses() {
        List<String> lines = new ArrayList<>();
        for (GymClass gymClass : gymClasses) {
            lines.add(gymClass.toString());
        }
        MyTool.writeFile(CLASS_FILE, lines);
    }


    private List<GymClass> searchClassesByID(String cID) {
        return gymClasses.stream()
                .filter(gymClass -> gymClass.getcID().equals(cID))
                .collect(Collectors.toList());
    }

    private List<GymClass> searchClassesContainingID(String id) {
        return gymClasses.stream()
                .filter(gymClass -> gymClass.getcID().toUpperCase().contains(id.toUpperCase()))
                .collect(Collectors.toList());
    }
    
       private boolean checkDuplicated(String classId) {
        for (GymClass gymClass : gymClasses) {
            if (gymClass.getcID().equals(classId)) {
                return true;
            }
        }
        return false;
    }

    public void createClass() {

        Scanner sc = new Scanner(System.in);
        String cID;
        do {
            cID = MyTool.readPattern("Enter Class ID (CLAxxxx): ", GymClass.ID_PATTERN);
            if (checkDuplicated(cID)) {
                System.out.println(" [ Class ID already exists. Please enter a different ID ]");
            }
        } while (checkDuplicated(cID));

        String name = MyTool.readAlphaString("Enter class name: ");
        int capacity = MyTool.readNonBlankInt("Enter class capacity: ");
        LocalDate startDate = MyTool.readValidDateNotEmpty("Enter start date (dd-MM-yyyy): ", "dd-MM-yyyy");
        LocalDate endDate = MyTool.readValidDateNotEmpty("Enter end date (dd-MM-yyyy): ", "dd-MM-yyyy");
        String days = MyTool.readNonBlank("Enter class days: ");
        LocalTime timeBegin = MyTool.readValidTimeNotEmpty("Enter start time (HH:mm): ", "HH:mm");
        LocalTime timeEnd = MyTool.readValidTimeNotEmpty("Enter end time (HH:mm): ", "HH:mm");

        GymClass gymClass = new GymClass(cID, name, capacity, startDate, endDate, days, timeBegin, timeEnd);
        gymClasses.add(gymClass);
        saveClasses();
        System.out.println(" [ Class added successfully ] ");
    }

    public void viewAndUpdateClass() {
        String id = MyTool.readNonBlank(" -> Search Class By ID:  ").toUpperCase();
        List<GymClass> foundClasses = searchClassesContainingID(id);

        if (foundClasses.isEmpty()) {
            System.out.println(" [ No class found with the specified ID ] ");
            return;
        }
        System.out.println(" -> Class(es) found: ");
        foundClasses.forEach(System.out::println);

        String updateID = MyTool.readPattern(" -> Enter the Class ID to update (CLxxxx): ", GymClass.ID_PATTERN).toUpperCase();
        List<GymClass> classesToUpdate = searchClassesByID(updateID);

        if (classesToUpdate.isEmpty()) {
            System.out.println(" [ No class found with the given ID ] ");
            return;
        }

        GymClass classToUpdate = classesToUpdate.get(0);

        String newName = MyTool.readOptionalAlphaString("Enter new class name (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newName, classToUpdate::setName);

        Integer newCapacity = MyTool.readOptionalInt("Enter new class capacity (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newCapacity, classToUpdate::setCapacity);

        LocalDate newStartDate = MyTool.readValidDate("Enter new start date (leave blank to keep current): ", "dd-MM-yyyy", true);
        MyTool.updateIfNotNullOrEmpty(newStartDate, classToUpdate::setStartDate);

        LocalDate newEndDate = MyTool.readValidDate("Enter new end date (leave blank to keep current): ", "dd-MM-yyyy", true);
        MyTool.updateIfNotNullOrEmpty(newEndDate, classToUpdate::setEndDate);

        String newDays = MyTool.readOptionalBlank("Enter new class days (leave blank to keep current): ");
        MyTool.updateIfNotNullOrEmpty(newDays, classToUpdate::setDays);

        LocalTime newTimeBegin = MyTool.readValidTime("Enter new start time (leave blank to keep current): ", "HH:mm", true);
        MyTool.updateIfNotNullOrEmpty(newTimeBegin, classToUpdate::setTimeBegin);

        LocalTime newTimeEnd = MyTool.readValidTime("Enter new end time (leave blank to keep current): ", "HH:mm", true);
        MyTool.updateIfNotNullOrEmpty(newTimeEnd, classToUpdate::setTimeEnd);

        saveClasses();
        System.out.println(" [ Class information updated successfully ] ");
    }

    public void deleteClass() {
        String searchID = MyTool.readNonBlank(" -> Search Class By ID: ").toUpperCase();
        List<GymClass> foundClasses = searchClassesContainingID(searchID);

        if (foundClasses.isEmpty()) {
            System.out.println(" [ No class found with the specified ID ] ");
            return;
        }

        System.out.println(" -> Class(es) found: ");
        foundClasses.forEach(System.out::println);

        String deleteID = MyTool.readPattern(" -> Enter Class ID To Delete (CLAzzzzz): ", GymClass.ID_PATTERN).toUpperCase();
        List<GymClass> classesToDelete = searchClassesByID(deleteID);

        if (classesToDelete.isEmpty()) {
            System.out.println(" [ No class found with the given ID ] ");
            return;
        }

        GymClass classToDelete = classesToDelete.get(0);

        System.out.print(" Enter 'Y' to confirm deletion or any other key to cancel: ");
        String confirmDelete = MyTool.sc.nextLine().trim().toUpperCase();
        if (!confirmDelete.equals("Y")) {
            System.out.println(" [ Deletion canceled ] ");
            return;
        }

        gymClasses.remove(classToDelete);
        saveClasses();
        System.out.println(" [ Class deleted successfully ] ");
    }

    public void sortAndDisplayClasses() {
        gymClasses.sort(Comparator.comparing(GymClass::getName));
        System.out.println(" < SORTED CLASS LIST > ");
        System.out.println("[ ID;  Name;  Capacity; StartDate; EndDate; Days; TimeBegin; TimeEnd ]");
        for (GymClass gymClass : gymClasses) {
            System.out.println(gymClass);
        }
    }
}
