import apiClient from '../utils/apiClient';

const getAppointments = async () => {
    const response = await apiClient.get('/appointments');
    return response.data;
};

const getAppointmentById = async (id) => {
    const response = await apiClient.get(`/appointments/${id}`);
    return response.data;
};

const addAppointment = async (appointment) => {
    const response = await apiClient.post('/appointments', appointment);
    return response.data;
};

const updateAppointment = async (id, appointment) => {
    const response = await apiClient.put(`/appointments/${id}`, appointment);
    return response.data;
};

const deleteAppointment = async (id) => {
    await apiClient.delete(`/appointments/${id}`);
};

export default {
    getAppointments,
    getAppointmentById,
    addAppointment,
    updateAppointment,
    deleteAppointment,
};
