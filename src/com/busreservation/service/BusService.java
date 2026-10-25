package com.busreservation.service;

import com.busreservation.data.DataStorage;
import com.busreservation.model.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BusService {
    private final Map<String, Bus> buses;

    public BusService() {
        this.buses = DataStorage.loadBuses();
    }

    public List<Bus> getAllBuses() {
        return new ArrayList<Bus>(buses.values());
    }

    public Bus getBusById(String busId) {
        if (busId == null) return null;
        return buses.get(busId.trim().toUpperCase());
    }

    public List<Bus> searchBuses(String source, String destination) {
        List<Bus> results = new ArrayList<Bus>();
        for (Bus b : buses.values()) {
            boolean matchSource = b.getSource().equalsIgnoreCase(source.trim());
            boolean matchDest = b.getDestination().equalsIgnoreCase(destination.trim());
            if (matchSource && matchDest) {
                results.add(b);
            }
        }
        return results;
    }

    public void addBus(Bus bus) {
        buses.put(bus.getBusId().toUpperCase(), bus);
        DataStorage.saveBuses(buses);
    }

    public void save() {
        DataStorage.saveBuses(buses);
    }

    public void displayBusesTable(List<Bus> list) {
        if (list.isEmpty()) {
            System.out.println("\n[!] No buses found for the given criteria.");
            return;
        }

        System.out.println("\n+---------+------------------------+-----------------+-----------------+----------+----------+-----------------------+-------+");
        System.out.printf("| %-7s | %-22s | %-15s | %-15s | %-8s | %-8s | %-21s | %-5s |\n",
                "Bus ID", "Operator", "Source", "Destination", "Dep.Time", "Arr.Time", "Type", "Avail");
        System.out.println("+---------+------------------------+-----------------+-----------------+----------+----------+-----------------------+-------+");

        for (Bus b : list) {
            System.out.printf("| %-7s | %-22s | %-15s | %-15s | %-8s | %-8s | %-21s | %-5d |\n",
                    b.getBusId(),
                    truncate(b.getOperatorName(), 22),
                    truncate(b.getSource(), 15),
                    truncate(b.getDestination(), 15),
                    b.getDepartureTime(),
                    b.getArrivalTime(),
                    truncate(b.getBusType().getDisplayName(), 21),
                    b.getAvailableSeatCount());
        }
        System.out.println("+---------+------------------------+-----------------+-----------------+----------+----------+-----------------------+-------+");
    }

    private String truncate(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 2) + "..";
    }
}
