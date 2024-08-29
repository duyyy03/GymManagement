/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gymclass;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author MSI
 */
public class GymClass {

    public static final String SEPARATOR = "; ";
    public static final String ID_PATTERN = "CLA\\d{5}";

    private String cID;
    private String name;
    private int capacity;
    private LocalDate startDate;
    private LocalDate endDate;
    private String days;
    private LocalTime timeBegin;
    private LocalTime timeEnd;

    public GymClass(String cID, String name, int capacity, LocalDate startDate, LocalDate endDate, String days, LocalTime timeBegin, LocalTime timeEnd) {
        this.cID = cID;
        this.name = name;
        this.capacity = capacity;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.timeBegin = timeBegin;
        this.timeEnd = timeEnd;
    }

    public GymClass(String line) {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        //String[] parts = line.split("" + this.SEPARATOR);
         String[] parts = line.split(";\\s*");
        cID = parts[0].trim();
        name = parts[1].trim();
        capacity = Integer.parseInt(parts[2].trim());
        startDate = LocalDate.parse(parts[3].trim(), DATE_FORMATTER);
        endDate = LocalDate.parse(parts[4].trim(), DATE_FORMATTER);
        days = parts[5].trim();
        timeBegin = LocalTime.parse(parts[6].trim(), TIME_FORMATTER);
        timeEnd = LocalTime.parse(parts[7].trim(), TIME_FORMATTER);
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public LocalTime getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(LocalTime timeBegin) {
        this.timeBegin = timeBegin;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString() {
        return cID + SEPARATOR + name + SEPARATOR + capacity + SEPARATOR + startDate + SEPARATOR + endDate + SEPARATOR + days + SEPARATOR + timeBegin + SEPARATOR + timeEnd;
    }
}
