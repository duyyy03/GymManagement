/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylib;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 *
 * @author MSI
 */
public class MyTool {

    public static final Scanner sc = new Scanner(System.in);

    public static List<String> readLinesFromFile(String filename) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static void writeFile(String filename, List<String> list) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String item : list) {
                writer.write(item);
                writer.newLine(); // Ghi một dòng mới sau mỗi đối tượng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readListString(String input) {
        List<String> dataList = new ArrayList<>();
        String[] values = input.split(",\\s+");
        for (String value : values) {
            dataList.add(value.trim());
        }
        return dataList;
    }

    public static boolean validStr(String str, String regEx) {
        return str.matches(regEx);
    }

    public static String readPattern(String message, String pattern) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message);
            input = sc.nextLine().trim();
            valid = validStr(input, pattern);
            if (!valid) {
                System.out.println("[Wrong format. Please enter again]");
            }
        } while (!valid);
        return input;
    }

    public static String readNonBlank(String message) {
        String input = "";
        do {
            System.out.print(message);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(" [This field cannot be empty. Enter again] ");
            }
        } while (input.isEmpty());
        return input;
    }

    public static String readOptionalBlank(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    public static int readNonBlankInt(String message) {
        int value;
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            try {
                value = Integer.parseInt(input);
                if (value < 0) {
                    System.out.println(" [The quantity cannot be negative. Please enter again] ");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("[Invalid input. Please enter a valid integer]");
            }
        }
        return value;
    }

    public static String readAlphaString(String message) {
        String input = "";
        boolean valid;
        do {
            System.out.print(message);
            input = sc.nextLine().trim();
            // Kiểm tra chuỗi chỉ chứa các ký tự chữ cái và khoảng trắng, không chứa số
            valid = input.matches("[a-zA-Z ]+");
            if (!valid) {
                System.out.println(" [Invalid input. Please enter a string with alphabetic characters only, without numbers.] ");
            }
        } while (!valid);
        return input;
    }

    public static LocalDate readValidDate(String message, String dateFormat, boolean optional) {
        LocalDate date = null;
        boolean isValid = false;
        do {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty() && optional) {
                isValid = true;
            } else {
                try {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern(dateFormat));
                    isValid = true;
                } catch (DateTimeParseException e) {
                    System.out.println(" [Invalid date format. Enter again (" + dateFormat + "), or leave it empty.] ");
                }
            }
        } while (!isValid);
        return date;
    }

    public static LocalDate readValidDateNotEmpty(String message, String dateFormat) {
        LocalDate date = null;
        boolean isValid = false;
        do {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(" [Date cannot be empty. Please enter a valid date.] ");
            } else {
                try {
                    date = LocalDate.parse(input, DateTimeFormatter.ofPattern(dateFormat));
                    isValid = true;
                } catch (DateTimeParseException e) {
                    System.out.println(" [Invalid date format. Enter again (" + dateFormat + ").] ");
                }
            }
        } while (!isValid);
        return date;
    }

    public static LocalTime readValidTime(String message, String timeFormat, boolean optional) {
        LocalTime time = null;
        boolean isValid = false;
        do {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty() && optional) {
                isValid = true;
            } else {
                try {
                    time = LocalTime.parse(input, DateTimeFormatter.ofPattern(timeFormat));
                    isValid = true;
                } catch (DateTimeParseException e) {
                    System.out.println(" [Invalid date format. Enter again (" + timeFormat + "), or leave it empty.] ");
                }
            }
        } while (!isValid);
        return time;
    }

    public static LocalTime readValidTimeNotEmpty(String message, String timeFormat) {
        LocalTime time = null;
        boolean isValid = false;
        do {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println(" [Time cannot be empty. Please enter a valid time.] ");
            } else {
                try {
                    time = LocalTime.parse(input, DateTimeFormatter.ofPattern(timeFormat));
                    isValid = true;
                } catch (DateTimeParseException e) {
                    System.out.println(" [Invalid time format. Enter again (" + timeFormat + ").] ");
                }
            }

        } while (!isValid);
        return time;
    }

    public static <T> void updateIfNotNullOrEmpty(T value, Consumer<T> updater) {
        if (value != null && !value.toString().trim().isEmpty()) {
            updater.accept(value);
        }
    }

    public static String readOptionalAlphaString(String message) {
        String input;
        boolean valid;
        do {
            System.out.print(message);
            input = sc.nextLine().trim();
            if (input.isEmpty()) {
                return input; // Allow empty input to skip updating
            }
            // Kiểm tra chuỗi chỉ chứa các ký tự chữ cái và khoảng trắng, không chứa số
            valid = input.matches("[a-zA-Z ]+");
            if (!valid) {
                System.out.println(" [Invalid input. Please enter a string with alphabetic characters only, without numbers.] ");
            }
        } while (!valid);
        return input;
    }

    public static Integer readOptionalInt(String message) {
        Integer value = null;
        while (true) {
            System.out.print(message);
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                break; // Allow empty input to skip updating
            }
            try {
                value = Integer.parseInt(input);
                break;
            } catch (NumberFormatException e) {
                System.out.println("[Invalid input. Please enter a valid integer or leave it blank to skip]");
            }
        }
        return value;
    }

}
