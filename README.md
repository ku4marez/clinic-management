# 🏥 Clinic Management System

The **Clinic Management System** is a web application designed to efficiently manage **doctors, patients, and appointments**.  
It provides **RESTful APIs** for backend operations and a **React-based frontend** for user interaction.

## ✨ Features

### 🩺 Doctor Management
- Add, edit, and delete doctor records.
- View the list of available doctors.

### 🏥 Patient Management
- Add, edit, and delete patient records.
- View the list of registered patients.

### 📅 Appointment Scheduling
- Book appointments with available doctors.
- Prevent overlapping appointments.
- Cancel or reschedule appointments.

### 🔐 Authentication & Authorization
- **JWT-based authentication** for secure access.
- **Role-based access control** (Admin, Doctor, Patient).

### 📩 Notification System *(Implemented as a separate service)*
- Send **appointment reminders** via **email/SMS**.

---

## 🛠 Technologies Used

### **Backend (Spring Boot + MongoDB)**
- 🚀 Java 21
- 🏗 Spring Boot 3.1.4
- 📂 Spring Data MongoDB
- 🔒 Spring Security (JWT Authentication)
- 🏗 Lombok & ModelMapper
- 📦 Maven

### **Frontend (React + MUI)**
- ⚛️ React 18
- 🎨 MUI (Material UI) for styling
- 🌍 Axios for API calls
- 🔀 React Router for navigation

### **Database & Infrastructure**
- 📦 **MongoDB** (NoSQL database for doctors, patients, and appointments)
- 🐳 **Docker** (Hosted in a container for local development)
- 🏗 **Docker Compose** (For managing dependencies)
- 🌐 **Nginx** (For serving static frontend files)

### **CI/CD & Dependency Management**
- ⚙ **Jenkins / GitHub Actions** (For CI/CD pipeline)
- 📦 **Nexus Repository** (For managing dependencies)

---

## 🚀 Getting Started

### 🔽 Clone the Repository
```sh
git clone https://github.com/your-username/clinic-management.git
cd clinic-management
```

### 🐳 Start Services with Docker Compose
- Ensure Docker and Docker Compose are installed, then run:
```sh
docker compose up -d
```

### 🚀 Run Backend (Spring Boot Application)
```sh
cd clinic-management-server
mvn clean package
java -jar target/clinic-management.jar
```
- The backend will be available at http://localhost:8080.

### 🌐 Run Frontend (React Application)
```sh
cd clinic-management-client
npm install
npm start
```
- The frontend will be available at http://localhost:3000.

