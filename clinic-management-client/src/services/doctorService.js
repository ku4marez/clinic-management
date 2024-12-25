import apiClient from '../utils/apiClient';

const getDoctors = async () => {
    const response = await apiClient.get('/doctors');
    return response.data;
};

const getDoctorById = async (id) => {
    const response = await apiClient.get(`/doctors/${id}`);
    return response.data;
};

const addDoctor = async (doctor) => {
    const response = await apiClient.post('/doctors', doctor);
    return response.data;
};

const updateDoctor = async (id, doctor) => {
    const response = await apiClient.put(`/doctors/${id}`, doctor);
    return response.data;
};

const deleteDoctor = async (id) => {
    await apiClient.delete(`/doctors/${id}`);
};

export default {
    getDoctors,
    getDoctorById,
    addDoctor,
    updateDoctor,
    deleteDoctor,
};
