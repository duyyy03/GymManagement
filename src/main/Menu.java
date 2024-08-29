/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import equipment.EquipmentManagement;
import member.MemberManagement;
import gymclass.GymClassManagement;
import gymclass.RegisMemManagement;
import gymclass.UsedEquipManagement;
import java.util.InputMismatchException;

import java.util.Scanner;

/**
 *
 * @author MSI
 */
public class Menu {

    private MemberManagement memberManagement;
    private EquipmentManagement equipManagement;
    private GymClassManagement gymClassManagement;
    private RegisMemManagement regisMemManagement;
    private UsedEquipManagement usedEquipManagement;

    public Menu() {
        memberManagement = new MemberManagement();
        equipManagement = new EquipmentManagement();
        gymClassManagement = new GymClassManagement();
        regisMemManagement = new RegisMemManagement();
        usedEquipManagement = new UsedEquipManagement();
    }

    public void displayMainMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ______________ GYM MANAGEMENT SYSTEM ______________ ");
            System.out.println("| 1. Manage Member                                  |");
            System.out.println("| 2. Manage Equipment                               |");
            System.out.println("| 3. Manage Class                                   |");
            System.out.println("| 0. Exit                                           |");
            System.out.println(" --------------------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    displayMemberMenu();
                    break;
                case 2:
                    displayEquipmentMenu();
                    break;
                case 3:
                    displayClassMenu();
                    break;
                case 0:
                    System.out.println("Exiting");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    public void displayMemberMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ___________  MANAGE MEMBER ___________ ");
            System.out.println("| 1. Create member                     |");
            System.out.println("| 2. Sort and display members          |");
            System.out.println("| 3. View and update member            |");
            System.out.println("| 4. Delete member                     |");
            System.out.println("| 0. Back to Main Menu                 |");
            System.out.println(" --------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    memberManagement.createMember();
                    break;
                case 2:
                    memberManagement.sortAndDisplayMembers();
                    break;
                case 3:
                    memberManagement.viewAndUpdateMember();
                    break;
                case 4:
                    memberManagement.deleteMember();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (true);
    }

    public void displayEquipmentMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ____________ MANAGE EQUIPMENT ____________ ");
            System.out.println("| 1. Create equipment                      |");
            System.out.println("| 2. Sort and display equipment            |");
            System.out.println("| 3. View and update rquipment             |");
            System.out.println("| 4. Delete equipment                      |");
            System.out.println("| 0. Back to main menu                     |");
            System.out.println(" ------------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    equipManagement.addEquipment();
                    break;
                case 2:
                    equipManagement.sortAndDisplayEquipment();
                    break;
                case 3:
                    equipManagement.viewAndUpdateEquipment();
                    break;
                case 4:
                    equipManagement.deleteEquipment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(" [Invalid choice. Please try again] ");
            }
        } while (true);
    }

    public void displayClassMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ____________ MANAGE CLASS ____________ ");
            System.out.println("| 1. Manage Classes                    |");
            System.out.println("| 2. Manage Register Member            |");
            System.out.println("| 3. Manage Used Equipment             |");
            System.out.println("| 0. Back to Main Menu                 |");
            System.out.println(" --------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    displayManageClassesMenu();
                    break;
                case 2:
                    displayManageRegisterClassMenu();
                    break;
                case 3:
                    displayManageUsedEquipmentMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.print("Invalid input. Please enter again: ");
            }
        } while (true);
    }

    public void displayManageClassesMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ____________ MANAGE CLASS ____________ ");
            System.out.println("| 1. Create class                      |");
            System.out.println("| 2. Sort and display classes          |");
            System.out.println("| 3. View and update class             |");
            System.out.println("| 4. Delete class                      |");
            System.out.println("| 0. Back to manage class menu         |");
            System.out.println(" --------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    gymClassManagement.createClass();
                    break;
                case 2:
                    gymClassManagement.sortAndDisplayClasses();
                    break;
                case 3:
                    gymClassManagement.viewAndUpdateClass();
                    break;
                case 4:
                    gymClassManagement.deleteClass();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(" [Invalid choice. Please try again] ");
            }
        } while (true);
    }

    public void displayManageRegisterClassMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ___________ MANAGE REGISTER MEMBER ___________ ");
            System.out.println("| 1. Add member to class                        |");
            System.out.println("| 2. Delete member from class                   |");
            System.out.println("| 3. Sort and display registered members        |");
            System.out.println("| 0. Back to manage class menu                  |");
            System.out.println(" ----------------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    regisMemManagement.addRegisMember();
                    break;
                case 2:
                    regisMemManagement.deleteRegisMember();
                    break;
                case 3:
                    regisMemManagement.sortAndDisplayRegisMems();
                    break;
                case 0:
                    return;
                default:
                    System.out.println(" [Invalid choice. Please try again] ");
            }
        } while (true);
    }

    public void displayManageUsedEquipmentMenu() {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println(" ____________ MANAGE USED EQUIPMENT ____________ ");
            System.out.println("| 1. Add equipment to class                     |");
            System.out.println("| 2. Delete Equipment from class                |");
            System.out.println("| 3. Sort and display used equipment            |");
            System.out.println("| 0. Back to manage class menu                  |");
            System.out.println(" ----------------------------------------------- ");
            System.out.print(" -> Enter your choice: ");
            while (true) {
                try {
                    choice = sc.nextInt();
                    sc.nextLine();
                    break;
                } catch (InputMismatchException e) {
                    System.out.print("Invalid input. Please enter again: ");
                    sc.nextLine();
                }
            }

            switch (choice) {
                case 1:
                    usedEquipManagement.addUsedEquipment();
                    break;
                case 2:
                    usedEquipManagement.deleteUsedEquipment();
                    break;
                case 3:
                    usedEquipManagement.sortAndDisplayUsedEquips();
                    break;
                case 0:
                    return;
                default:
                    System.out.print("Invalid input. Please enter again: ");
            }
        } while (true);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.displayMainMenu();
    }
}
