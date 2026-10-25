package com.busreservation.model;

import java.io.Serializable;
import java.util.List;

public class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String pnr;
    private String busId;
    private String busOperator;
    private String source;
    private String destination;
    private String departureTime;
    private String travelDate;
    private List<Passenger> passengers;
    private double totalFare;
    private BookingStatus status;
    private String bookingDate;

    public Booking(String pnr, String busId, String busOperator, String source, 
                   String destination, String departureTime, String travelDate, 
                   List<Passenger> passengers, double totalFare, String bookingDate) {
        this.pnr = pnr;
        this.busId = busId;
        this.busOperator = busOperator;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.travelDate = travelDate;
        this.passengers = passengers;
        this.totalFare = totalFare;
        this.status = BookingStatus.CONFIRMED;
        this.bookingDate = bookingDate;
    }

    public String getPnr() {
        return pnr;
    }

    public String getBusId() {
        return busId;
    }

    public String getBusOperator() {
        return busOperator;
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

    public String getTravelDate() {
        return travelDate;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void printTicket() {
        System.out.println("\n==============================================================");
        System.out.println("                 BUS RESERVATION TICKET");
        System.out.println("==============================================================");
        System.out.printf(" PNR Number       : %s\n", pnr);
        System.out.printf(" Status           : %s\n", status);
        System.out.printf(" Booking Date     : %s\n", bookingDate);
        System.out.println("--------------------------------------------------------------");
        System.out.printf(" Bus Service      : %s (ID: %s)\n", busOperator, busId);
        System.out.printf(" Route            : %s to %s\n", source, destination);
        System.out.printf(" Travel Date      : %s\n", travelDate);
        System.out.printf(" Departure Time   : %s\n", departureTime);
        System.out.println("--------------------------------------------------------------");
        System.out.println(" PASSENGER DETAILS:");
        System.out.printf(" %-5s %-22s %-6s %-8s %-6s\n", "#", "Name", "Age", "Gender", "Seat");
        System.out.println(" -------------------------------------------------------------");
        for (int i = 0; i < passengers.size(); i++) {
            Passenger p = passengers.get(i);
            System.out.printf(" %-5d %-22s %-6d %-8s %-6d\n", 
                (i + 1), p.getName(), p.getAge(), p.getGender(), p.getSeatNumber());
        }
        System.out.println("--------------------------------------------------------------");
        System.out.printf(" Total Fare Paid  : $%.2f\n", totalFare);
        System.out.println("==============================================================");
        System.out.println(" * Please arrive at the boarding point 15 mins prior to departure.");
        System.out.println("==============================================================\n");
    }
}
