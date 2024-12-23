import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import MainLayout from './components/layout/MainLayout';
import DoctorList from './components/doctors/DoctorList';
import PatientList from './components/patients/PatientList';
import AppointmentList from './components/appointments/AppointmentList';

function App() {
    // Mock data for initial rendering
    const mockDoctors = [
        { id: '1', firstName: 'John', lastName: 'Doe', email: 'john.doe@clinic.com', specialty: 'Cardiology' },
        { id: '2', firstName: 'Jane', lastName: 'Smith', email: 'jane.smith@clinic.com', specialty: 'Neurology' },
    ];

    const mockPatients = [
        { id: '1', firstName: 'Alice', lastName: 'Brown', email: 'alice.brown@clinic.com', address: '123 Main St', dateOfBirth: '2001-12-16' },
        { id: '2', firstName: 'Bob', lastName: 'White', email: 'bob.white@clinic.com', address: '456 Elm St', dateOfBirth: '2000-12-16'},
    ];

    const mockAppointments = [
        { id: '1', doctorName: 'Dr. John Doe', patientName: 'Alice Brown', dateTime: '2024-12-21T10:00', status: 'Scheduled' },
        { id: '2', doctorName: 'Dr. Jane Smith', patientName: 'Bob White', dateTime: '2024-12-22T14:00', status: 'Completed' },
    ];

    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainLayout />}>
                    <Route
                        path="doctors"
                        element={<DoctorList doctors={mockDoctors} onEdit={() => {}} onDelete={() => {}} />}
                    />
                    <Route
                        path="patients"
                        element={<PatientList patients={mockPatients} onEdit={() => {}} onDelete={() => {}} />}
                    />
                    <Route
                        path="appointments"
                        element={<AppointmentList appointments={mockAppointments} doctors={mockDoctors} patients={mockPatients} onEdit={() => {}} onDelete={() => {}} />}
                    />
                </Route>
            </Routes>
        </Router>
    );
}

export default App;
