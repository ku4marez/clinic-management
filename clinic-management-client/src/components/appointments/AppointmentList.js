import React, { useEffect, useState } from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Button,
    Paper,
    CircularProgress,
} from '@mui/material';
import appointmentService from '../../services/appointmentService';
import doctorService from '../../services/doctorService';
import patientService from '../../services/patientService';
import AddEditAppointmentForm from './AddEditAppointmentForm';

function AppointmentList() {
    const [appointments, setAppointments] = useState([]);
    // const [totalPages, setTotalPages] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [patients, setPatients] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [openForm, setOpenForm] = useState(false);
    const [selectedAppointment, setSelectedAppointment] = useState(null);

    const fetchAppointments = async () => {
        try {
            const response = await appointmentService.getAppointments();
            // setTotalPages(response.totalPages);
            setAppointments(response.content);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    const fetchDoctors = async () => {
        try {
            const response = await doctorService.getDoctors();
            setDoctors(response.content);
        } catch (err) {
            setError(err.message);
        }
    };

    const fetchPatients = async () => {
        try {
            const response = await patientService.getPatients();
            setPatients(response.content);
        } catch (err) {
            setError(err.message);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            await Promise.all([fetchAppointments(), fetchDoctors(), fetchPatients()]);
        };
        fetchData();
    }, []);

    const handleEdit = (appointment) => {
        setSelectedAppointment(appointment);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedAppointment(null);
        setOpenForm(false);
    };

    const handleSave = async (appointment) => {
        try {
            if (appointment.id) {
                await appointmentService.updateAppointment(appointment.id, appointment);
            } else {
                await appointmentService.addAppointment(appointment);
            }
            await fetchAppointments();
            handleCloseForm();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async (id) => {
        try {
            await appointmentService.deleteAppointment(id);
            await fetchAppointments();
        } catch (err) {
            setError(err.message);
        }
    };

    if (loading) return <CircularProgress />;
    if (error) return <div>Error: {error}</div>;

    return (
        <>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Doctor</TableCell>
                            <TableCell>Patient</TableCell>
                            <TableCell>Date & Time</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {appointments.map((appointment) => (
                            <TableRow key={appointment.id}>
                                <TableCell>{appointment.doctorName || 'N/A'}</TableCell>
                                <TableCell>{appointment.patientName || 'N/A'}</TableCell>
                                <TableCell>{appointment.dateTime}</TableCell>
                                <TableCell>{appointment.status}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(appointment)}>Edit</Button>
                                    <Button onClick={() => handleDelete(appointment.id)} color="error">
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <AddEditAppointmentForm
                open={openForm}
                onClose={handleCloseForm}
                onSave={handleSave}
                initialData={selectedAppointment}
                doctors={doctors}
                patients={patients}
            />
        </>
    );
}

export default AppointmentList;
