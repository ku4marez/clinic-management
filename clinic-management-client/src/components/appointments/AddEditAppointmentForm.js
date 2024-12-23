import {Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField} from "@mui/material";
import {useEffect, useState} from "react";

function AddEditAppointmentForm({ open, onClose, onSave, initialData, doctors, patients }) {
    const [appointment, setAppointment] = useState(initialData || { doctorId: '', patientId: '', dateTime: '', status: '' });

    useEffect(() => {
        if (initialData) {
            setAppointment(initialData);
        }
    }, [initialData]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setAppointment({ ...appointment, [name]: value });
    };

    const handleSubmit = () => {
        onSave(appointment);
    };

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>{appointment.id ? 'Edit Appointment' : 'Add Appointment'}</DialogTitle>
            <DialogContent>
                <TextField
                    select
                    fullWidth
                    margin="normal"
                    label="Doctor"
                    name="doctorId"
                    value={appointment.doctorId}
                    onChange={handleChange}
                    SelectProps={{ native: true }}
                >
                    <option value="" disabled>Select a doctor</option>
                    {doctors.map((doctor) => (
                        <option key={doctor.id} value={doctor.id}>
                            {doctor.firstName} {doctor.lastName}
                        </option>
                    ))}
                </TextField>
                <TextField
                    select
                    fullWidth
                    margin="normal"
                    label="Patient"
                    name="patientId"
                    value={appointment.patientId}
                    onChange={handleChange}
                    SelectProps={{ native: true }}
                >
                    <option value="" disabled>Select a patient</option>
                    {patients.map((patient) => (
                        <option key={patient.id} value={patient.id}>
                            {patient.firstName} {patient.lastName}
                        </option>
                    ))}
                </TextField>
                <TextField
                    fullWidth
                    margin="normal"
                    label="Date & Time"
                    type="datetime-local"
                    name="dateTime"
                    value={appointment.dateTime}
                    onChange={handleChange}
                />
                <TextField
                    fullWidth
                    margin="normal"
                    label="Status"
                    name="status"
                    value={appointment.status}
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

export default AddEditAppointmentForm;
