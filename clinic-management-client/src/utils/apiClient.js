import axios from 'axios';

// Create an Axios instance for the main API
const apiClient = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL,
    timeout: 10000,
});

// Create an Axios instance for the authentication service
const authClient = axios.create({
    baseURL: process.env.REACT_APP_AUTHENTICATION_BASE_URL,
    timeout: 10000,
});

// Function to refresh the access token
const refreshAccessToken = async () => {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
        throw new Error('Refresh token not available');
    }

    const response = await authClient.post('/auth/refresh', { refreshToken });
    const { accessToken, refreshToken: newRefreshToken } = response.data;

    // Update tokens in localStorage
    localStorage.setItem('token', accessToken);
    localStorage.setItem('refreshToken', newRefreshToken);

    // Update default Authorization header
    apiClient.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

    return accessToken;
};

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
    async (error) => {
        const originalRequest = error.config;

        // If 401 (Unauthorized) and not a retry, attempt to refresh token
        if (error.response?.status === 401 && !originalRequest._retry) {
            originalRequest._retry = true;

            try {
                const newToken = await refreshAccessToken();
                originalRequest.headers['Authorization'] = `Bearer ${newToken}`;
                return apiClient(originalRequest); // Retry the original request
            } catch (err) {
                localStorage.removeItem('token');
                localStorage.removeItem('refreshToken');
                window.location.href = '/login';
                return Promise.reject(err);
            }
        }

        return Promise.reject(error);
    }
);

export default apiClient;
