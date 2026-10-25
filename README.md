# 🚌 Bus Reservation Management System (Java)

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20Linux%20%7C%20macOS-lightgrey?style=for-the-badge)](https://github.com)
[![Build](https://img.shields.io/badge/Build-Passing-brightgreen?style=for-the-badge)](https://github.com)

A robust, modular, and object-oriented **Bus Ticket Reservation and Fleet Management System** developed in Java. This application features an intuitive console user interface, visual 2x2 interactive seat allocation maps, ticket generation with unique PNR tracking, automated cancellation & seat release, and local data persistence.

---

## 🌟 Key Features

- **🚌 Fleet Management**: Browse scheduled buses, routes, departure/arrival schedules, bus types (AC Sleeper, AC Seater, Volvo Multi-Axle), and ticket pricing.
- **🔍 Route Search**: Filter available buses dynamically by origin (source) and destination cities.
- **💺 Visual Seat Layout Matrix**: View real-time 2x2 seat layouts showing driver cabin, aisle, booked (`[XX]`), and available (`[01]`) seats.
- **🎟️ Multi-Passenger Booking**: Book single or multiple tickets in a single transaction with customized seat selection and instant price computation.
- **🎫 Digital PNR Ticket Generation**: Generates official formatted tickets complete with PNR code, passenger details, route metadata, and total fare.
- **❌ Ticket Cancellation & Seat Release**: Cancel bookings using PNR, instantly freeing up reserved seats for other travelers.
- **💾 Automatic Data Persistence**: Uses local file storage to ensure booking records and bus seat statuses persist across system restarts.
- **🛡️ Robust Input Validation**: Hardened against `InputMismatchException` and crash bugs from erroneous user terminal inputs.

---

## 🏗️ System Architecture & OOP Design

The project is structured according to strict Object-Oriented Programming (OOP) and Separation of Concerns (SoC) principles:

```
BusReservationSystem/
├── src/
│   └── com/
│       └── busreservation/
│           ├── Main.java               # Application CLI Controller & Main Menu Loop
│           ├── model/                  # Domain Data Entities (POJOs)
│           │   ├── Bus.java            # Bus entity with seat matrix management
│           │   ├── BusType.java        # Enum for bus classifications
│           │   ├── Passenger.java      # Passenger demographics & seat assignment
│           │   ├── Booking.java        # Booking transaction & ticket formatter
│           │   └── BookingStatus.java  # Booking status enum (CONFIRMED / CANCELLED)
│           ├── service/                # Business Logic Services
│           │   ├── BusService.java     # Bus search, filtering, and table rendering
│           │   └── BookingService.java # PNR generation, reservations & refunds
│           ├── data/                   # Data Access & File Persistence
│           │   └── DataStorage.java    # Serialization & seed data provider
│           └── util/                   # Common Utilities
│               └── ConsoleUtils.java   # Safe input scanners & terminal formatters
├── build.bat                           # 1-Click build script for Windows
├── run.bat                             # 1-Click execution script for Windows
├── pom.xml                             # Maven project descriptor
├── .gitignore                          # Standard Java gitignore
└── README.md                           # Documentation
```

---

## 🖥️ Interactive Seat Layout Preview

```text
+-------------------------------------------------------------+
| SEAT LAYOUT: Greyhound Luxury Express (BUS101)              |
| Route: New York             -> Boston                       |
+-------------------------------------------------------------+
|  [D] Driver Cabin                                           |
+-------------------------------------------------------------+
|  [01]  [02]    [AISLE]    [XX]  [04]                        |
|  [05]  [06]    [AISLE]    [07]  [08]                        |
|  [09]  [10]    [AISLE]    [11]  [12]                        |
|  [13]  [14]    [AISLE]    [15]  [16]                        |
+-------------------------------------------------------------+
| Legend: [01] = Available  |  [XX] = Booked                  |
+-------------------------------------------------------------+
```

---

## 🚀 Getting Started

### Prerequisites
- **Java SE Development Kit (JDK) 8 or higher**:
  Check your Java version in the terminal:
  ```bash
  java -version
  javac -version
  ```

---

### Method 1: Quick Run with Windows Batch Scripts (Easiest)

1. Double-click `build.bat` (or run in terminal):
   ```cmd
   build.bat
   ```
2. Double-click `run.bat` to launch the application:
   ```cmd
   run.bat
   ```

---

### Method 2: Manual Terminal Compilation

1. Clone or open the project folder in terminal:
   ```bash
   cd BusReservationSystem
   ```
2. Create the output directory:
   ```bash
   mkdir bin
   ```
3. Compile all Java source files:
   ```bash
   javac -d bin src/com/busreservation/model/*.java src/com/busreservation/util/*.java src/com/busreservation/data/*.java src/com/busreservation/service/*.java src/com/busreservation/Main.java
   ```
4. Run the application:
   ```bash
   java -cp bin com.busreservation.Main
   ```

---

### Method 3: Run with Apache Maven

```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.busreservation.Main"
```

---

## 📤 Step-by-Step GitHub Upload Guide

Follow these steps to upload this project to your GitHub account:

### Step 1: Initialize Git and Commit Files (Already prepared locally)
If you haven't already:
```bash
git init -b main
git add .
git commit -m "feat: initial commit for Bus Reservation Management System in Java"
```

### Step 2: Create a New Repository on GitHub
1. Log in to [GitHub](https://github.com/).
2. Click the **`+`** icon in the top right corner and select **New repository**.
3. Name your repository (e.g., `Bus-Reservation-Management-System`).
4. **Important**: Leave `Add a README file`, `.gitignore`, and `License` **UNCHECKED** (we already created them locally).
5. Click **Create repository**.

### Step 3: Link and Push to GitHub
Copy your repository URL from GitHub and run:
```bash
# Link your local repo to GitHub (replace USERNAME and REPO_NAME with yours)
git remote add origin https://github.com/USERNAME/REPO_NAME.git

# Push code to GitHub
git branch -M main
git push -u origin main
```

---

## 📋 Sample Run Walkthrough

1. **View Fleet**: Select Option `1` to see all pre-configured buses, schedules, and fares.
2. **Search by Route**: Select Option `2`, enter Source (e.g. `New York`) and Destination (e.g. `Boston`).
3. **View Seat Matrix**: Select Option `3` and enter `BUS101` to view which seats are open.
4. **Book Ticket**: Select Option `4`, input passenger name, age, gender, and pick seat numbers (e.g., `1`, `2`).
5. **Get Instant Ticket**: Receive an official ASCII printable ticket with a unique 6-digit PNR code (e.g. `PNR102948`).
6. **Cancellation**: If your plan changes, choose Option `5`, enter your PNR, and the seats will be automatically made available for other travelers.

---

## 📄 License
This project is open-source and available under the [MIT License](LICENSE).
