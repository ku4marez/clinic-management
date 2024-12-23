import {Button, Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import React, {useState} from "react";
import AddEditAppointmentForm from "./AddEditAppointmentForm";

function AppointmentList({appointments, doctors, patients, onEdit, onDelete}) {
    const [openForm, setOpenForm] = useState(false);
    const [selectedAppointment, setSelectedAppointment] = useState(null);

    const handleEdit = (appointment) => {
        setSelectedAppointment(appointment);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedAppointment(null);
        setOpenForm(false);
    };

    const handleSave = (patient) => {
        onEdit(patient);
        handleCloseForm();
    };
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
                                <TableCell>{appointment.doctorName}</TableCell>
                                <TableCell>{appointment.patientName}</TableCell>
                                <TableCell>{appointment.dateTime}</TableCell>
                                <TableCell>{appointment.status}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(appointment)}>Edit</Button>
                                    <Button onClick={() => onDelete(appointment.id)} color="error">
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
