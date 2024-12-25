import axios from 'axios';

// Create an Axios instance
const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    timeout: 10000,
});

// Add Authorization header if token exists
apiClient.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
});

// Handle response errors globally
apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        // If token is invalid or expired, clear local storage and redirect to login
        if (error.response?.status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default apiClient;
