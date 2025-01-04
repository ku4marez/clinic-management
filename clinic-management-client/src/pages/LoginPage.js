import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { TextField, Button, Box, Typography, CircularProgress } from '@mui/material';
import apiClient from '../utils/apiClient';

function LoginPage() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const navigate = useNavigate();

    const handleLogin = async () => {
        setLoading(true);
        setError(null);

        try {
            const response = await apiClient.post('/auth/login', { email, password });

            const { accessToken, refreshToken } = response.data;

            localStorage.setItem('token', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            apiClient.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;

            navigate('/');
        } catch (err) {
            setError('Invalid credentials. Please try again.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ maxWidth: 400, mx: 'auto', mt: 8, p: 3, textAlign: 'center' }}>
            <Typography variant="h4" gutterBottom>
                Login
            </Typography>
            {error && (
                <Typography variant="body2" color="error" gutterBottom>
                    {error}
                </Typography>
            )}
            <TextField
                fullWidth
                margin="normal"
                label="Email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <TextField
                fullWidth
                margin="normal"
                label="Password"
                type="password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button
                fullWidth
                variant="contained"
                color="primary"
                onClick={handleLogin}
                disabled={loading}
                sx={{ mt: 2 }}
            >
                {loading ? <CircularProgress size={24} /> : 'Login'}
            </Button>
        </Box>
    );
}

export default LoginPage;
