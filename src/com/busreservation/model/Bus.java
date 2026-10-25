package com.busreservation.model;

import java.io.Serializable;
import java.util.Arrays;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String busId;
    private String busNumber;
    private String operatorName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private BusType busType;
    private double fare;
    private int totalSeats;
    private boolean[] seatBooked; // true if booked, 1-indexed for convenience

    public Bus(String busId, String busNumber, String operatorName, String source, 
               String destination, String departureTime, String arrivalTime, 
               BusType busType, double fare, int totalSeats) {
        this.busId = busId;
        this.busNumber = busNumber;
        this.operatorName = operatorName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.busType = busType;
        this.fare = fare;
        this.totalSeats = totalSeats;
        this.seatBooked = new boolean[totalSeats + 1]; // 1-based indexing
    }

    public String getBusId() {
        return busId;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public BusType getBusType() {
        return busType;
    }

    public double getFare() {
        return fare;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public boolean isSeatAvailable(int seatNumber) {
        if (seatNumber < 1 || seatNumber > totalSeats) {
            return false;
        }
        return !seatBooked[seatNumber];
    }

    public boolean bookSeat(int seatNumber) {
        if (isSeatAvailable(seatNumber)) {
            seatBooked[seatNumber] = true;
            return true;
        }
        return false;
    }

    public boolean releaseSeat(int seatNumber) {
        if (seatNumber >= 1 && seatNumber <= totalSeats && seatBooked[seatNumber]) {
            seatBooked[seatNumber] = false;
            return true;
        }
        return false;
    }

    public int getAvailableSeatCount() {
        int count = 0;
        for (int i = 1; i <= totalSeats; i++) {
            if (!seatBooked[i]) {
                count++;
            }
        }
        return count;
    }

    public void displaySeatLayout() {
        System.out.println("\n+-------------------------------------------------------------+");
        System.out.printf("| %-59s |\n", "SEAT LAYOUT: " + operatorName + " (" + busId + ")");
        System.out.printf("| Route: %-20s -> %-20s |\n", source, destination);
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|  [D] Driver Cabin                                           |");
        System.out.println("+-------------------------------------------------------------+");

        // 2x2 layout
        int rows = (int) Math.ceil((double) totalSeats / 4);
        for (int r = 0; r < rows; r++) {
            int s1 = r * 4 + 1;
            int s2 = r * 4 + 2;
            int s3 = r * 4 + 3;
            int s4 = r * 4 + 4;

            String c1 = (s1 <= totalSeats) ? formatSeat(s1) : "      ";
            String c2 = (s2 <= totalSeats) ? formatSeat(s2) : "      ";
            String c3 = (s3 <= totalSeats) ? formatSeat(s3) : "      ";
            String c4 = (s4 <= totalSeats) ? formatSeat(s4) : "      ";

            System.out.printf("|  %s  %s    [AISLE]    %s  %s   |\n", c1, c2, c3, c4);
        }

        System.out.println("+-------------------------------------------------------------+");
        System.out.println("| Legend: [01] = Available  |  [XX] = Booked                  |");
        System.out.println("+-------------------------------------------------------------+\n");
    }

    private String formatSeat(int seatNum) {
        if (seatBooked[seatNum]) {
            return "[XX]";
        } else {
            return String.format("[%02d]", seatNum);
        }
    }
}
