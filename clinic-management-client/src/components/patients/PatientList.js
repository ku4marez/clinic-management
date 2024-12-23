import React, {useState} from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Paper} from '@mui/material';
import AddEditPatientForm from "./AddEditPatientForm";

function PatientList({patients, onEdit, onDelete}) {
    const [openForm, setOpenForm] = useState(false);
    const [selectedPatient, setSelectedPatient] = useState(null);

    const handleEdit = (doctor) => {
        setSelectedPatient(doctor);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedPatient(null);
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
                            <TableCell>Name</TableCell>
                            <TableCell>Email</TableCell>
                            <TableCell>Date of birth</TableCell>
                            <TableCell>Address</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {patients.map((patient) => (
                            <TableRow key={patient.id}>
                                <TableCell>{`${patient.firstName} ${patient.lastName}`}</TableCell>
                                <TableCell>{patient.email}</TableCell>
                                <TableCell>{patient.dateOfBirth}</TableCell>
                                <TableCell>{patient.address}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(patient)}>Edit</Button>
                                    <Button onClick={() => onDelete(patient.id)} color="error">
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <AddEditPatientForm
                open={openForm}
                onClose={handleCloseForm}
                onSave={handleSave}
                initialData={selectedPatient}
            />
        </>
    );
}

export default PatientList;
