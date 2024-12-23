import React, {useEffect, useState} from 'react';
import { TextField, Button, Dialog, DialogActions, DialogContent, DialogTitle } from '@mui/material';

function AddEditDoctorForm({ open, onClose, onSave, initialData }) {
    const [doctor, setDoctor] = useState(
        initialData || { firstName: '', lastName: '', email: '', specialty: '' }
    );

    useEffect(() => {
        if (initialData) {
            setDoctor(initialData);
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setDoctor({ ...doctor, [name]: value });
    };

    const handleSubmit = () => {
        onSave(doctor);
    };
    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>{doctor.id ? 'Edit Doctor' : 'Add Doctor'}</DialogTitle>
            <DialogContent>
                <TextField
                    fullWidth
                    margin="normal"
                    label="First Name"
                    name="firstName"
                    value={doctor.firstName}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Last Name"
                    name="lastName"
                    value={doctor.lastName}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Email"
                    name="email"
                    value={doctor.email}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Specialty"
                    name="specialty"
                    value={doctor.specialty}
                    onChange={handleChange}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={onClose}>Cancel</Button>
                <Button onClick={handleSubmit} color="primary">
                    Save
                </Button>
            </DialogActions>
        </Dialog>
    );
}

export default AddEditDoctorForm;
