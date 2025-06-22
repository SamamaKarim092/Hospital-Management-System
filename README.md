# 🏥 Project: Hospital Management System

## 🔍 Overview

The **Hospital Management System (HMS)** is a comprehensive desktop software solution designed to automate and optimize the core operations of healthcare facilities. Built using **Java (JavaFX)** and backed by **MySQL**, the system handles patient registration, appointment scheduling, medical record tracking, billing, and real-time dashboard updates.

With a modular and object-oriented approach, the HMS facilitates smoother staff communication, improved patient care, and accurate data management.

---

## 🧠 Key Data Structures Used

### 1. 🧾 `HashMap` – User Credential Management

The system uses HashMaps for:

- **🔐 Credential Validation:**  
  - Stores username (as key) and hashed password (as value).
  - Uses `hashPassword()` method for SHA-256 hashing.
- **⚡ Cached Lookup:**  
  - First-time logins are cached to speed up future validations.
- **🛡️ Brute-force Protection:**  
  - `loginAttempts` HashMap tracks failed attempts and locks accounts after 3 failures.
- **🔄 Database Optimization:**  
  - Reduces frequent DB hits by caching credentials in memory.

### 2. ⏳ `Queue` – Real-time Dashboard Update Handling

- **Class:** `LinkedBlockingQueue`
- **Purpose:** Manages multithreaded updates in the Reception dashboard.
- **FIFO Order:** Ensures events are processed in the exact order they arrive.
- **Benefits:**
  - ✅ Concurrency control without data corruption
  - ✅ Order preservation for update accuracy
  - ✅ Efficient resource usage in a producer-consumer pattern

### 3. 📋 `ArrayList` – Managing Reception Instances

- **Stores all Reception interfaces** for dashboard handling.
- **Fast Access (O(1))** to each Reception instance.
- **Dynamic Sizing:** Automatically expands as more Reception UIs are added.
- **Method `refreshAllDashboards()`:**  
  Loops through each instance to refresh and sync dashboard data across the system.

### 4. 🚑 `Binary Search Tree` – Ambulance Management

- **Fast Lookup:** Efficient search of ambulances by ID in O(log n) time.
- **Location-based Services:** Traverses to find nearest available ambulance.
- **Memory Efficiency:** Only active ambulances are stored.
- **Scalable Design:** Supports easy insertions and removals without performance degradation.

---

## ✨ Features

- **🩺 Patient Registration:** Add, edit, and view patient profiles.
- **📅 Appointment Scheduling:** Book and track appointments by doctor or department.
- **📂 Medical Records:** Maintain secure and searchable health histories.
- **💵 Billing System:** Generate and manage invoices and payments.
- **📊 Real-time Dashboards:** Auto-updated reception and admin dashboards using Queues.
- **🔐 Role-based Access:** Distinct interfaces for admin, doctor, and receptionist.
- **📡 Ambulance Locator:** Quickly find and dispatch nearest ambulances using BST logic.
- **🛡️ Secure Login System:** Password hashing and brute-force prevention using HashMaps.

---

## ⚙️ Technologies Used

- **Java (JavaFX):** UI development
- **MySQL / JDBC:** Persistent backend database
- **Data Structures:**
  - `HashMap`, `ArrayList`, `Queue`, `Binary Search Tree`
- **Multi-threading:** Used for live updates without UI freezing
- **OOP Principles:** Modular and maintainable structure

---

## 🖼️ Interface Snapshot

### Login page screenshot
![image](https://github.com/user-attachments/assets/a5a3e229-0320-4b72-82ed-3b54f08382e8)

### Reception page screenshot
![image](https://github.com/user-attachments/assets/3a863aae-2e80-4811-bcc2-8034b3c9b727)
---

## 🚀 Getting Started

### 1. Install IntelliJ IDEA
Download and install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) – the recommended IDE for running and managing this JavaFX project with ease.

### 2. Set Up MySQL Database
- Install MySQL Server and MySQL Workbench (or any other DB GUI).
- Create a database (e.g., `hospital_db`).
- Import the provided SQL script from the `/database` folder to create necessary tables and sample data.
- Update the database connection credentials in the Java code (DB URL, username, password).

### 3. Import Project into IntelliJ
- Open IntelliJ IDEA.
- Click on **File > Open** and select the root folder of the project.
- Let IntelliJ index and build the project.
- Make sure the Java SDK and JavaFX SDK (if using Java 11+) are configured correctly in **Project Structure**.

### 4. Run the Application
- Locate and open the `Main.java` file (or equivalent entry point).
- Click the **Run** button or press **Shift + F10**.

### ✅ That's it!
Enjoy exploring the Hospital Management System. All features should now be active and connected to the database.
