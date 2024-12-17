import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';

const RegistrationForm = () => {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    login: '',
    password: '',
    role: 'STUDENT', // Default value
  });

  const [errors, setErrors] = useState({});
  const [serverMessage, setServerMessage] = useState('');

  const navigate = useNavigate();  // Ініціалізація navigate для перенаправлення

  // Обробка зміни полів форми
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Клієнтська валідація
  const validateForm = () => {
    const newErrors = {};

    if (!formData.name) newErrors.name = 'Name cannot be blank';
    else if (!/^[a-zA-Zа-яА-ЯёЁіІїЇєЄ\s'-]+$/.test(formData.name)) {
      newErrors.name = "Name can only contain letters, spaces, hyphens, and apostrophes";
    }

    if (!formData.email) newErrors.email = 'Email cannot be blank';
    else if (!/^[^@\s]+@[^@\s]+\.[^@\s]+$/.test(formData.email)) {
      newErrors.email = 'Email must be valid';
    }

    if (!formData.login) newErrors.login = 'Login cannot be blank';
    else if (!/^[a-zA-Z0-9._-]+$/.test(formData.login)) {
      newErrors.login = "Login can only contain letters, numbers, dots, underscores, and hyphens";
    }

    if (!formData.role) newErrors.role = 'Role cannot be blank';
    else if (!['STUDENT', 'EMPLOYEE'].includes(formData.role)) {
      newErrors.role = 'Role must be either STUDENT or EMPLOYEE';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  // Відправка даних
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (validateForm()) {
      try {
        const response = await axios.post('http://localhost:9000/api/register', formData);
        setServerMessage('Registration successful!');
        setErrors({});
        // Перенаправлення на сторінку логіна після успішної реєстрації
        setTimeout(() => {
          navigate('/');  // Перехід на сторінку логіна
        }, 2000);  // Пауза перед перенаправленням
      } catch (error) {
        setServerMessage(
          error.response?.data || 'An error occurred during registration. Please try again.'
        );
      }
    }
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4">Registration Form</h2>
      {serverMessage && (
        <div className={`alert ${serverMessage.includes('successful') ? 'alert-success' : 'alert-danger'}`}>
          {serverMessage}
        </div>
      )}
      <form onSubmit={handleSubmit} className="card p-4 shadow-sm">
        {/* Name */}
        <div className="form-group mb-3">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            name="name"
            className="form-control"
            id="name"
            value={formData.name}
            onChange={handleChange}
            placeholder="Enter your name"
          />
          {errors.name && <small className="text-danger">{errors.name}</small>}
        </div>

        {/* Email */}
        <div className="form-group mb-3">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            name="email"
            className="form-control"
            id="email"
            value={formData.email}
            onChange={handleChange}
            placeholder="Enter your email"
          />
          {errors.email && <small className="text-danger">{errors.email}</small>}
        </div>

        {/* Login */}
        <div className="form-group mb-3">
          <label htmlFor="login">Login</label>
          <input
            type="text"
            name="login"
            className="form-control"
            id="login"
            value={formData.login}
            onChange={handleChange}
            placeholder="Choose a login"
          />
          {errors.login && <small className="text-danger">{errors.login}</small>}
        </div>

        {/* Password */}
        <div className="form-group mb-3">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            name="password"
            className="form-control"
            id="password"
            value={formData.password}
            onChange={handleChange}
            placeholder="Enter a strong password"
          />
        </div>

        {/* Role */}
        <div className="form-group mb-3">
          <label htmlFor="role">Role</label>
          <select
            name="role"
            className="form-control"
            id="role"
            value={formData.role}
            onChange={handleChange}
          >
            <option value="STUDENT">STUDENT</option>
            <option value="EMPLOYEE">EMPLOYEE</option>
          </select>
          {errors.role && <small className="text-danger">{errors.role}</small>}
        </div>

        <button type="submit" className="btn btn-primary w-100">Register</button>
      </form>
    </div>
  );
};

export default RegistrationForm;
