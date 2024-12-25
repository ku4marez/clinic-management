import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Paper, CircularProgress } from '@mui/material';
import doctorService from '../../services/doctorService';
import AddEditDoctorForm from './AddEditDoctorForm';

function DoctorList() {
    const [doctors, setDoctors] = useState([]);
    // const [totalPages, setTotalPages] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [openForm, setOpenForm] = useState(false);
    const [selectedDoctor, setSelectedDoctor] = useState(null);

    const fetchDoctors = async () => {
        try {
            const response = await doctorService.getDoctors();
            setDoctors(response.content);
            // setTotalPages(response.totalPages);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchDoctors();
    }, []);

    const handleEdit = (doctor) => {
        setSelectedDoctor(doctor);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedDoctor(null);
        setOpenForm(false);
    };

    const handleSave = async (doctor) => {
        try {
            if (doctor.id) {
                await doctorService.updateDoctor(doctor.id, doctor);
            } else {
                await doctorService.addDoctor(doctor);
            }
            await fetchDoctors(); // Refresh the list after save
            handleCloseForm();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async (id) => {
        try {
            await doctorService.deleteDoctor(id);
            await fetchDoctors(); // Refresh the list after delete
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
                                    <Button onClick={() => handleDelete(doctor.id)} color="error">
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
