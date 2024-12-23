import React, {useState} from 'react';
import {Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Paper} from '@mui/material';
import AddEditDoctorForm from "./AddEditDoctorForm";

function DoctorList({doctors, onEdit, onDelete}) {
    const [openForm, setOpenForm] = useState(false);
    const [selectedDoctor, setSelectedDoctor] = useState(null);

    const handleEdit = (doctor) => {
        setSelectedDoctor(doctor);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedDoctor(null);
        setOpenForm(false);
    };

    const handleSave = (doctor) => {
        onEdit(doctor);
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
                            <TableCell>Specialty</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {doctors.map((doctor) => (
                            <TableRow key={doctor.id}>
                                <TableCell>{`${doctor.firstName} ${doctor.lastName}`}</TableCell>
                                <TableCell>{doctor.email}</TableCell>
                                <TableCell>{doctor.specialty}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(doctor)}>Edit</Button>
                                    <Button onClick={() => onDelete(doctor.id)} color="error">
                                        Delete
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <AddEditDoctorForm
                open={openForm}
                onClose={handleCloseForm}
                onSave={handleSave}
                initialData={selectedDoctor}
            />
        </>
    );
}

export default DoctorList;
