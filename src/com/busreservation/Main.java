package com.busreservation;

import com.busreservation.model.*;
import com.busreservation.service.BookingService;
import com.busreservation.service.BusService;
import com.busreservation.util.ConsoleUtils;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final BusService busService = new BusService();
    private static final BookingService bookingService = new BookingService(busService);

    public static void main(String[] args) {
        printWelcomeBanner();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = ConsoleUtils.readInt("Enter your choice [1-9]: ", 1, 9);

            switch (choice) {
                case 1:
                    viewAllBuses();
                    break;
                case 2:
                    searchBuses();
                    break;
                case 3:
                    viewSeatLayout();
                    break;
                case 4:
                    bookTickets();
                    break;
                case 5:
                    cancelTicket();
                    break;
                case 6:
                    checkTicketStatus();
                    break;
                case 7:
                    viewAllBookings();
                    break;
                case 8:
                    addNewBus();
                    break;
                case 9:
                    running = false;
                    System.out.println("\nThank you for using the Bus Reservation Management System!");
                    System.out.println("Have a safe journey!\n");
                    break;
            }
        }
    }

    private static void printWelcomeBanner() {
        System.out.println("==============================================================");
        System.out.println("       * WELCOME TO BUS RESERVATION MANAGEMENT SYSTEM *       ");
        System.out.println("              Smart, Fast & Easy Ticket Booking               ");
        System.out.println("==============================================================");
    }

    private static void printMainMenu() {
        System.out.println("\n============== MAIN MENU ==============");
        System.out.println(" 1. View All Available Buses");
        System.out.println(" 2. Search Buses by Route");
        System.out.println(" 3. View Bus Seat Layout");
        System.out.println(" 4. Book Tickets");
        System.out.println(" 5. Cancel Ticket (by PNR)");
        System.out.println(" 6. Check Ticket / PNR Status");
        System.out.println(" 7. View All Booking Records");
        System.out.println(" 8. Add New Bus (Admin)");
        System.out.println(" 9. Exit Application");
        System.out.println("=======================================");
    }

    private static void viewAllBuses() {
        ConsoleUtils.printHeader("All Available Buses");
        busService.displayBusesTable(busService.getAllBuses());
        ConsoleUtils.pause();
    }

    private static void searchBuses() {
        ConsoleUtils.printHeader("Search Buses by Route");
        String source = ConsoleUtils.readString("Enter Source City (e.g. New York): ");
        String destination = ConsoleUtils.readString("Enter Destination City (e.g. Boston): ");

        List<Bus> matching = busService.searchBuses(source, destination);
        busService.displayBusesTable(matching);
        ConsoleUtils.pause();
    }

    private static void viewSeatLayout() {
        ConsoleUtils.printHeader("View Bus Seat Layout");
        String busId = ConsoleUtils.readString("Enter Bus ID (e.g. BUS101): ");
        Bus bus = busService.getBusById(busId);

        if (bus == null) {
            System.out.println("[!] Error: Bus with ID '" + busId + "' was not found.");
        } else {
            bus.displaySeatLayout();
        }
        ConsoleUtils.pause();
    }

    private static void bookTickets() {
        ConsoleUtils.printHeader("Book Bus Tickets");
        String busId = ConsoleUtils.readString("Enter Bus ID (e.g. BUS101): ");
        Bus bus = busService.getBusById(busId);

        if (bus == null) {
            System.out.println("[!] Error: Bus with ID '" + busId + "' does not exist.");
            ConsoleUtils.pause();
            return;
        }

        if (bus.getAvailableSeatCount() == 0) {
            System.out.println("[!] Sorry, this bus is completely booked!");
            ConsoleUtils.pause();
            return;
        }

        bus.displaySeatLayout();

        String travelDate = ConsoleUtils.readString("Enter Travel Date (e.g. YYYY-MM-DD): ");
        int passengerCount = ConsoleUtils.readInt(
                "Enter number of passengers (Max available: " + bus.getAvailableSeatCount() + "): ",
                1, bus.getAvailableSeatCount());

        List<Passenger> passengers = new ArrayList<Passenger>();
        for (int i = 1; i <= passengerCount; i++) {
            System.out.println("\n--- Passenger " + i + " Details ---");
            String name = ConsoleUtils.readString("Passenger Name: ");
            int age = ConsoleUtils.readInt("Age: ", 1, 120);
            String gender = ConsoleUtils.readString("Gender (Male/Female/Other): ");

            int seatNum = -1;
            while (true) {
                seatNum = ConsoleUtils.readInt("Select Seat Number [1 - " + bus.getTotalSeats() + "]: ",
                        1, bus.getTotalSeats());

                if (!bus.isSeatAvailable(seatNum)) {
                    System.out.println("[!] Seat #" + seatNum + " is already booked! Please pick another seat.");
                    continue;
                }

                // Check if already selected by earlier passenger in current booking
                boolean alreadyChosen = false;
                for (Passenger p : passengers) {
                    if (p.getSeatNumber() == seatNum) {
                        alreadyChosen = true;
                        break;
                    }
                }

                if (alreadyChosen) {
                    System.out.println("[!] You already selected seat #" + seatNum + " for another passenger.");
                    continue;
                }

                break;
            }

            passengers.add(new Passenger(name, age, gender, seatNum));
        }

        double totalAmount = bus.getFare() * passengerCount;
        System.out.println("\n-------------------------------------------");
        System.out.printf("Total Fare for %d passenger(s): $%.2f\n", passengerCount, totalAmount);
        System.out.println("-------------------------------------------");

        String confirm = ConsoleUtils.readString("Confirm booking? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            Booking booking = bookingService.createBooking(bus, travelDate, passengers);
            System.out.println("\n[✓] Booking Confirmed Successfully!");
            booking.printTicket();
        } else {
            System.out.println("\n[X] Booking cancelled by user.");
        }

        ConsoleUtils.pause();
    }

    private static void cancelTicket() {
        ConsoleUtils.printHeader("Cancel Ticket Reservation");
        String pnr = ConsoleUtils.readString("Enter PNR Number to cancel: ");
        Booking booking = bookingService.getBooking(pnr);

        if (booking == null) {
            System.out.println("[!] Error: No booking found with PNR: " + pnr);
            ConsoleUtils.pause();
            return;
        }

        booking.printTicket();
        String confirm = ConsoleUtils.readString("Are you sure you want to cancel this booking? (yes/no): ");
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            boolean success = bookingService.cancelBooking(pnr);
            if (success) {
                System.out.println("\n[✓] Ticket cancellation successful for PNR: " + pnr);
                System.out.println("[✓] Seats have been released. Refund processed as per cancellation policy.");
            }
        } else {
            System.out.println("[!] Cancellation aborted.");
        }

        ConsoleUtils.pause();
    }

    private static void checkTicketStatus() {
        ConsoleUtils.printHeader("Check Ticket Status");
        String pnr = ConsoleUtils.readString("Enter PNR Number: ");
        Booking booking = bookingService.getBooking(pnr);

        if (booking == null) {
            System.out.println("[!] No booking found with PNR: " + pnr);
        } else {
            booking.printTicket();
        }
        ConsoleUtils.pause();
    }

    private static void viewAllBookings() {
        ConsoleUtils.printHeader("All Booking Records");
        bookingService.displayBookingsSummary();
        ConsoleUtils.pause();
    }

    private static void addNewBus() {
        ConsoleUtils.printHeader("Add New Bus to Fleet");
        String busId = ConsoleUtils.readString("Enter New Bus ID (e.g. BUS501): ");
        if (busService.getBusById(busId) != null) {
            System.out.println("[!] Error: A bus with ID '" + busId + "' already exists!");
            ConsoleUtils.pause();
            return;
        }

        String number = ConsoleUtils.readString("Enter Vehicle Registration Number (e.g. TX-01-A-1234): ");
        String operator = ConsoleUtils.readString("Enter Operator / Agency Name: ");
        String source = ConsoleUtils.readString("Enter Source City: ");
        String dest = ConsoleUtils.readString("Enter Destination City: ");
        String depTime = ConsoleUtils.readString("Enter Departure Time (e.g. 10:00 AM): ");
        String arrTime = ConsoleUtils.readString("Enter Arrival Time (e.g. 04:30 PM): ");

        System.out.println("\nSelect Bus Type:");
        System.out.println(" 1. AC Sleeper");
        System.out.println(" 2. AC Seater");
        System.out.println(" 3. Non-AC Seater");
        System.out.println(" 4. Volvo Multi-Axle");
        int typeChoice = ConsoleUtils.readInt("Choice [1-4]: ", 1, 4);
        BusType busType;
        switch (typeChoice) {
            case 1: busType = BusType.AC_SLEEPER; break;
            case 2: busType = BusType.AC_SEATER; break;
            case 3: busType = BusType.NON_AC_SEATER; break;
            default: busType = BusType.VOLVO_MULTI_AXLE; break;
        }

        int seats = ConsoleUtils.readInt("Enter Total Seats (e.g. 28, 32, 40): ", 10, 60);
        int fare = ConsoleUtils.readInt("Enter Base Ticket Fare ($): ", 5, 500);

        Bus newBus = new Bus(busId, number, operator, source, dest, depTime, arrTime, busType, fare, seats);
        busService.addBus(newBus);

        System.out.println("\n[✓] New Bus successfully added to the fleet!");
        ConsoleUtils.pause();
    }
}
