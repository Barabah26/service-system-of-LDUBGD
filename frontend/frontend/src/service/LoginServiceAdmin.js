import axios from 'axios';
import API_ENDPOINTS from '../components/apiConfig'; 

const API_BASE_URL = API_ENDPOINTS.AUTH.AUTH;
const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
});

axiosInstance.interceptors.request.use((config) => {
  const token = localStorage.getItem('accessToken');
  if (token) {
    config.headers['Authorization'] = 'Bearer ' + token;
  }
  return config;
}, (error) => {
  return Promise.reject(error);
});

export const login = async (login, password) => {
  try {
    const response = await axiosInstance.post('/admin/login', { login, password });
    console.log('Login response:', response.data);

    if (response.data.accessToken && response.data.refreshToken) {
      localStorage.setItem('accessToken', response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);

      // Ensure the role is set
      const role = response.data.role;
      localStorage.setItem('userRole', role);
      console.log('Role stored:', role);

      return response.data;
    } else {
      throw new Error('Invalid response format');
    }
  } catch (error) {
    console.error('Login error:', error.response ? error.response.data : error.message);
    throw new Error('Login failed. Please try again.');
  }
};

export default axiosInstance;
