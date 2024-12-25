import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Button, Paper, CircularProgress } from '@mui/material';
import patientService from '../../services/patientService';
import AddEditPatientForm from './AddEditPatientForm';

function PatientList() {
    const [patients, setPatients] = useState([]);
    // const [totalPages, setTotalPages] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [openForm, setOpenForm] = useState(false);
    const [selectedPatient, setSelectedPatient] = useState(null);

    const fetchPatients = async (page = 0, pageSize = 20) => {
        try {
            const response = await patientService.getPatients(page, pageSize);
            setPatients(response.content);
            // setTotalPages(response.totalPages);
            setLoading(false);
        } catch (err) {
            setError(err.message);
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchPatients();
    }, []);

    const handleEdit = (patient) => {
        setSelectedPatient(patient);
        setOpenForm(true);
    };

    const handleCloseForm = () => {
        setSelectedPatient(null);
        setOpenForm(false);
    };

    const handleSave = async (patient) => {
        try {
            if (patient.id) {
                await patientService.updatePatient(patient.id, patient);
            } else {
                await patientService.addPatient(patient);
            }
            await fetchPatients();
            handleCloseForm();
        } catch (err) {
            setError(err.message);
        }
    };

    const handleDelete = async (id) => {
        try {
            await patientService.deletePatient(id);
            await fetchPatients();
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
                            <TableCell>Address</TableCell>
                            <TableCell>Actions</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {patients.map((patient) => (
                            <TableRow key={patient.id}>
                                <TableCell>{`${patient.firstName} ${patient.lastName}`}</TableCell>
                                <TableCell>{patient.email}</TableCell>
                                <TableCell>{patient.address}</TableCell>
                                <TableCell>
                                    <Button onClick={() => handleEdit(patient)}>Edit</Button>
                                    <Button onClick={() => handleDelete(patient.id)} color="error">
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
