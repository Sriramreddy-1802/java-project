package com.busreservation.service;

import com.busreservation.data.DataStorage;
import com.busreservation.model.Booking;
import com.busreservation.model.BookingStatus;
import com.busreservation.model.Bus;
import com.busreservation.model.Passenger;

import java.text.SimpleDateFormat;
import java.util.*;

public class BookingService {
    private final Map<String, Booking> bookings;
    private final BusService busService;

    public BookingService(BusService busService) {
        this.busService = busService;
        this.bookings = DataStorage.loadBookings();
    }

    public Booking createBooking(Bus bus, String travelDate, List<Passenger> passengers) {
        // Book seats on bus
        for (Passenger p : passengers) {
            bus.bookSeat(p.getSeatNumber());
        }

        String pnr = generatePNR();
        double totalFare = bus.getFare() * passengers.size();
        String bookingTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Booking booking = new Booking(
                pnr,
                bus.getBusId(),
                bus.getOperatorName(),
                bus.getSource(),
                bus.getDestination(),
                bus.getDepartureTime(),
                travelDate,
                passengers,
                totalFare,
                bookingTimestamp
        );

        bookings.put(pnr, booking);

        // Persist both
        busService.save();
        DataStorage.saveBookings(bookings);

        return booking;
    }

    public boolean cancelBooking(String pnr) {
        if (pnr == null) return false;
        Booking booking = bookings.get(pnr.trim().toUpperCase());
        if (booking == null) {
            return false;
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            System.out.println("[!] This booking is already cancelled.");
            return false;
        }

        // Release seats on bus
        Bus bus = busService.getBusById(booking.getBusId());
        if (bus != null) {
            for (Passenger p : booking.getPassengers()) {
                bus.releaseSeat(p.getSeatNumber());
            }
            busService.save();
        }

        booking.setStatus(BookingStatus.CANCELLED);
        DataStorage.saveBookings(bookings);
        return true;
    }

    public Booking getBooking(String pnr) {
        if (pnr == null) return null;
        return bookings.get(pnr.trim().toUpperCase());
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<Booking>(bookings.values());
    }

    public void displayBookingsSummary() {
        if (bookings.isEmpty()) {
            System.out.println("\n[!] No booking history recorded yet.");
            return;
        }

        System.out.println("\n+------------+---------+-----------------+-----------------+------------+----------+-----------+");
        System.out.printf("| %-10s | %-7s | %-15s | %-15s | %-10s | %-8s | %-9s |\n",
                "PNR", "Bus ID", "Source", "Destination", "Date", "Fare ($)", "Status");
        System.out.println("+------------+---------+-----------------+-----------------+------------+----------+-----------+");

        for (Booking b : bookings.values()) {
            System.out.printf("| %-10s | %-7s | %-15s | %-15s | %-10s | %-8.2f | %-9s |\n",
                    b.getPnr(),
                    b.getBusId(),
                    truncate(b.getSource(), 15),
                    truncate(b.getDestination(), 15),
                    b.getTravelDate(),
                    b.getTotalFare(),
                    b.getStatus());
        }
        System.out.println("+------------+---------+-----------------+-----------------+------------+----------+-----------+");
    }

    private String generatePNR() {
        int randomNum = 100000 + new Random().nextInt(900000);
        return "PNR" + randomNum;
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 2) + "..";
    }
}
