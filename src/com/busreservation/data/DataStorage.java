package com.busreservation.data;

import com.busreservation.model.Booking;
import com.busreservation.model.Bus;
import com.busreservation.model.BusType;

import java.io.*;
import java.util.*;

public class DataStorage {
    private static final String DATA_DIR = "data";
    private static final String BUSES_FILE = DATA_DIR + File.separator + "buses.dat";
    private static final String BOOKINGS_FILE = DATA_DIR + File.separator + "bookings.dat";

    public static void initDataDirectory() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Bus> loadBuses() {
        initDataDirectory();
        File file = new File(BUSES_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Map<String, Bus>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Notice: Could not load saved bus data. Initializing default fleet...");
            }
        }
        return getDefaultBuses();
    }

    public static void saveBuses(Map<String, Bus> buses) {
        initDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BUSES_FILE))) {
            oos.writeObject(buses);
        } catch (IOException e) {
            System.err.println("Warning: Failed to save bus state: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Booking> loadBookings() {
        initDataDirectory();
        File file = new File(BOOKINGS_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Map<String, Booking>) ois.readObject();
            } catch (Exception e) {
                System.out.println("Notice: Could not load saved bookings. Initializing empty records...");
            }
        }
        return new LinkedHashMap<String, Booking>();
    }

    public static void saveBookings(Map<String, Booking> bookings) {
        initDataDirectory();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.err.println("Warning: Failed to save booking records: " + e.getMessage());
        }
    }

    private static Map<String, Bus> getDefaultBuses() {
        Map<String, Bus> map = new LinkedHashMap<String, Bus>();

        Bus b1 = new Bus("BUS101", "NY-04-A-1024", "Greyhound Luxury Express", 
                         "New York", "Boston", "06:30 AM", "11:00 AM", 
                         BusType.VOLVO_MULTI_AXLE, 45.0, 32);

        Bus b2 = new Bus("BUS102", "MA-09-B-5521", "MegaBus Intercity", 
                         "Boston", "New York", "02:00 PM", "06:30 PM", 
                         BusType.AC_SEATER, 35.0, 36);

        Bus b3 = new Bus("BUS201", "CA-12-C-8890", "Pacific Coast Lines", 
                         "Los Angeles", "San Francisco", "08:00 AM", "03:30 PM", 
                         BusType.AC_SLEEPER, 65.0, 28);

        Bus b4 = new Bus("BUS202", "CA-14-D-9901", "Golden State Voyager", 
                         "San Francisco", "Los Angeles", "10:30 PM", "06:00 AM", 
                         BusType.AC_SLEEPER, 70.0, 28);

        Bus b5 = new Bus("BUS301", "IL-07-E-3344", "Midwest City Cruiser", 
                         "Chicago", "Detroit", "07:15 AM", "12:45 PM", 
                         BusType.NON_AC_SEATER, 28.0, 40);

        Bus b6 = new Bus("BUS401", "WA-02-G-7711", "Emerald City Shuttle", 
                         "Seattle", "Portland", "09:00 AM", "12:30 PM", 
                         BusType.AC_SEATER, 32.0, 32);

        map.put(b1.getBusId(), b1);
        map.put(b2.getBusId(), b2);
        map.put(b3.getBusId(), b3);
        map.put(b4.getBusId(), b4);
        map.put(b5.getBusId(), b5);
        map.put(b6.getBusId(), b6);

        return map;
    }
}
