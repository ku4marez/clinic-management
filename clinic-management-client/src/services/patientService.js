import apiClient from '../utils/apiClient';

const getPatients = async () => {
    const response = await apiClient.get('/patients');
    return response.data;
};

const getPatientById = async (id) => {
    const response = await apiClient.get(`/patients/${id}`);
    return response.data;
};

const addPatient = async (patient) => {
    const response = await apiClient.post('/patients', patient);
    return response.data;
};

const updatePatient = async (id, patient) => {
    const response = await apiClient.put(`/patients/${id}`, patient);
    return response.data;
};

const deletePatient = async (id) => {
    await apiClient.delete(`/patients/${id}`);
};

export default {
    getPatients,
    getPatientById,
    addPatient,
    updatePatient,
    deletePatient,
};
