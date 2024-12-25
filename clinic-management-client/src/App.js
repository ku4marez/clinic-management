import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import DoctorList from './components/doctors/DoctorList';
import PatientList from './components/patients/PatientList';
import AppointmentList from './components/appointments/AppointmentList';
import LoginPage from "./pages/LoginPage";

function App() {
    const isAuthenticated = !!localStorage.getItem('token');

    return (
        <Router>
            <Routes>
                {/* Login Route */}
                <Route path="/login" element={<LoginPage />} />

                {/* Protected Routes */}
                <Route
                    path="/"
                    element={isAuthenticated ? <MainLayout /> : <Navigate to="/login" />}
                >
                    <Route path="doctors" element={<DoctorList />} />
                    <Route path="patients" element={<PatientList />} />
                    <Route path="appointments" element={<AppointmentList />} />
                </Route>

                {/* Default Redirect */}
                <Route path="*" element={<Navigate to={isAuthenticated ? "/" : "/login"} />} />
            </Routes>
        </Router>
    );
}

export default App;
