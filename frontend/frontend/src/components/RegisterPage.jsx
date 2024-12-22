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

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

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

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (validateForm()) {
      try {
        const response = await axios.post('http://localhost:9000/api/register', formData);
        setServerMessage('Registration successful!');
        setErrors({});
        setTimeout(() => {
          navigate('/');
        }, 1000);
      } catch (error) {
        setServerMessage(
          error.response?.data || 'An error occurred during registration. Please try again.'
        );
      }
    }
  };

  return (
    <div className="container d-flex justify-content-center align-items-center mt-5">
      <div className="card shadow p-4" style={{ width: '100%', maxWidth: '400px' }}>
        <h3 className="text-center mb-4">Реєстрація</h3>
        {serverMessage && (
          <div
            className={`alert ${
              serverMessage.includes('successful') ? 'alert-success' : 'alert-danger'
            }`}
          >
            {serverMessage}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          {/* Name */}
          <div className="form-group mb-3">
            <label htmlFor="name">Прізвище і ім'я</label>
            <input
              type="text"
              name="name"
              className={`form-control ${errors.name ? 'is-invalid' : ''}`}
              id="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="Введіть прізвище і ім'я"
            />
            {errors.name && <div className="invalid-feedback">{errors.name}</div>}
          </div>

          {/* Email */}
          <div className="form-group mb-3">
            <label htmlFor="email">Електронна пошта</label>
            <input
              type="email"
              name="email"
              className={`form-control ${errors.email ? 'is-invalid' : ''}`}
              id="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="Введіть свою електронну пошту"
            />
            {errors.email && <div className="invalid-feedback">{errors.email}</div>}
          </div>

          {/* Login */}
          <div className="form-group mb-3">
            <label htmlFor="login">Логін</label>
            <input
              type="text"
              name="login"
              className={`form-control ${errors.login ? 'is-invalid' : ''}`}
              id="login"
              value={formData.login}
              onChange={handleChange}
              placeholder="Введіть логін"
            />
            {errors.login && <div className="invalid-feedback">{errors.login}</div>}
          </div>

          {/* Password */}
          <div className="form-group mb-3">
            <label htmlFor="password">Пароль</label>
            <input
              type="password"
              name="password"
              className="form-control"
              id="password"
              value={formData.password}
              onChange={handleChange}
              placeholder="Введіть пароль"
            />
          </div>

          {/* Role */}
          <div className="form-group mb-3">
            <label htmlFor="role">Роль в системі</label>
            <select
              name="role"
              className={`form-control ${errors.role ? 'is-invalid' : ''}`}
              id="role"
              value={formData.role}
              onChange={handleChange}
            >
              <option value="STUDENT">STUDENT</option>
              <option value="EMPLOYEE">EMPLOYEE</option>
            </select>
            {errors.role && <div className="invalid-feedback">{errors.role}</div>}
          </div>

          <button type="submit" className="btn btn-primary w-100">
            Зареєструвати
          </button>
        </form>
      </div>
    </div>
  );
};

export default RegistrationForm;
