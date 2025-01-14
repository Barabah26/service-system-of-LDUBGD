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
    faculty: '',
    specialty: '',
    degree: '',
    group: '',
    dateBirth: '',
    phoneNumber: '',
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

    if (!formData.dateBirth) newErrors.dateBirth = 'Birth date cannot be blank';
    if (!formData.phoneNumber) {
      newErrors.phoneNumber = 'Phone number cannot be blank';
    } else if (!/^\+?[0-9]{10,15}$/.test(formData.phoneNumber)) {
      newErrors.phoneNumber = 'Phone number must be valid (e.g., +380123456789)';
    }

    if (!formData.faculty) newErrors.faculty = 'Faculty cannot be blank';
    if (!formData.specialty) newErrors.specialty = 'Specialty cannot be blank';
    if (!formData.degree) newErrors.degree = 'Degree cannot be blank';
    if (!formData.group) newErrors.group = 'Group cannot be blank';

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
          navigate('/student-login');
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
      <div className="card shadow p-4" style={{ width: '100%', maxWidth: '800px' }}> {/* Adjusted maxWidth for two-column layout */}
        <h3 className="text-center mb-4">Реєстрація</h3>
        {serverMessage && (
          <div
            className={`alert ${serverMessage.includes('successful') ? 'alert-success' : 'alert-danger'}`}
          >
            {serverMessage}
          </div>
        )}
        <form onSubmit={handleSubmit}>
          <div className="row">
            {/* Name and Email */}
            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="name">ПІБ</label>
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
            </div>

            <div className="col-md-6">
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
            </div>
          </div>

          <div className="row">
            {/* Login and Password */}
            <div className="col-md-6">
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
            </div>

            <div className="col-md-6">
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
            </div>
          </div>

          <div className="row">
            {/* Role, Faculty, Specialty, Degree */}
            <div className="col-md-6">
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
            </div>

            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="birthDate">Дата народження</label>
                <input
                  type="text"
                  name="dateBirth"
                  className={`form-control`}
                  id="dateBirth"
                  value={formData.birthDate}
                  onChange={handleChange}
                />
              </div>
            </div>
          </div>

          <div className="row">
            {/* Phone Number */}
            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="phoneNumber">Номер телефону</label>
                <input
                  type="text"
                  name="phoneNumber"
                  className={`form-control ${errors.phoneNumber ? 'is-invalid' : ''}`}
                  id="phoneNumber"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  placeholder="+380123456789"
                />
                {errors.phoneNumber && <div className="invalid-feedback">{errors.phoneNumber}</div>}
              </div>
            </div>

            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="faculty">Факультет</label>
                <select
                  name="faculty"
                  className={`form-control ${errors.faculty ? 'is-invalid' : ''}`}
                  id="faculty"
                  value={formData.faculty}
                  onChange={handleChange}
                >
                  <option value="">Оберіть факультет</option>
                  <option value="Факультет пожежної та техногенної безпеки">
                    Факультет пожежної та техногенної безпеки
                  </option>
                  <option value="Факультет психології та соціального захисту">
                    Факультет психології та соціального захисту
                  </option>
                  <option value="Факультет цивільного захисту">Факультет цивільного захисту</option>
                </select>
                {errors.faculty && <div className="invalid-feedback">{errors.faculty}</div>}
              </div>
            </div>
          </div>

          <div className="row">
            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="specialty">Спеціальність</label>
                <select
                  name="specialty"
                  className={`form-control ${errors.specialty ? 'is-invalid' : ''}`}
                  id="specialty"
                  value={formData.specialty}
                  onChange={handleChange}
                >
                  <option value="">Оберіть спеціальність</option>
              <option value="ЕКОЛОГІЯ ТА ОХОРОНА НАВКОЛИШНЬОГО СЕРЕДОВИЩА">
                ЕКОЛОГІЯ ТА ОХОРОНА НАВКОЛИШНЬОГО СЕРЕДОВИЩА
              </option>
              <option value="КОМП'ЮТЕРНІ НАУКИ">КОМП'ЮТЕРНІ НАУКИ</option>
              <option value="МЕНЕДЖМЕНТ ОРГАНІЗАЦІЙ ТА АДМІНІСТРУВАННЯ">
                МЕНЕДЖМЕНТ ОРГАНІЗАЦІЙ ТА АДМІНІСТРУВАННЯ
              </option>
              <option value="ПОЖЕЖНА БЕЗПЕКА">ПОЖЕЖНА БЕЗПЕКА</option>
              <option value="ПОЖЕЖОГАСІННЯ ТА АВАРІЙНО-РЯТУВАЛЬНІ РОБОТИ">
                ПОЖЕЖОГАСІННЯ ТА АВАРІЙНО-РЯТУВАЛЬНІ РОБОТИ
              </option>
              <option value="АУДИТ ПОЖЕЖНОЇ ТА ТЕХНОГЕННОЇ БЕЗПЕКИ">
                АУДИТ ПОЖЕЖНОЇ ТА ТЕХНОГЕННОЇ БЕЗПЕКИ
              </option>
              <option value="ПРАКТИЧНА ПСИХОЛОГІЯ">ПРАКТИЧНА ПСИХОЛОГІЯ</option>
              <option value="ЕКСТРЕМАЛЬНА ТА КРИЗОВА ПСИХОЛОГІЯ">
                ЕКСТРЕМАЛЬНА ТА КРИЗОВА ПСИХОЛОГІЯ
              </option>
              <option value="СОЦІАЛЬНА РОБОТА">СОЦІАЛЬНА РОБОТА</option>
              <option value="СОЦІАЛЬНИЙ ЗАХИСТ В СЕКТОРІ БЕЗПЕКИ ТА ОБОРОНИ. СОЦІАЛЬНИЙ СУПРОВІД">
                СОЦІАЛЬНИЙ ЗАХИСТ В СЕКТОРІ БЕЗПЕКИ ТА ОБОРОНИ. СОЦІАЛЬНИЙ СУПРОВІД
              </option>
              <option value="ТРАНСПОРТНІ ТЕХНОЛОГІЇ (НА АВТОМОБІЛЬНОМУ ТРАНСПОРТІ)">
                ТРАНСПОРТНІ ТЕХНОЛОГІЇ (НА АВТОМОБІЛЬНОМУ ТРАНСПОРТІ)
              </option>
              <option value="УПРАВЛІННЯ ІНФОРМАЦІЙНОЮ БЕЗПЕКОЮ">
                УПРАВЛІННЯ ІНФОРМАЦІЙНОЮ БЕЗПЕКОЮ
              </option>
              <option value="ПЕРЕКЛАД З АНГЛІЙСЬКОЇ МОВИ">ПЕРЕКЛАД З АНГЛІЙСЬКОЇ МОВИ</option>
              <option value="ІНЖЕНЕРНЕ ЗАБЕЗПЕЧЕННЯ САПЕРНИХ, ПІРОТЕХНІЧНИХ ТА ВИБУХОВИХ РОБІТ">
                ІНЖЕНЕРНЕ ЗАБЕЗПЕЧЕННЯ САПЕРНИХ, ПІРОТЕХНІЧНИХ ТА ВИБУХОВИХ РОБІТ
              </option>
              <option value="ЦИВІЛЬНИЙ ЗАХИСТ">ЦИВІЛЬНИЙ ЗАХИСТ</option>
              <option value="ОХОРОНА ПРАЦІ ТА ТЕХНОГЕННА БЕЗПЕКА">ОХОРОНА ПРАЦІ ТА ТЕХНОГЕННА БЕЗПЕКА</option>
                </select>
                {errors.specialty && <div className="invalid-feedback">{errors.specialty}</div>}
              </div>
            </div>

            <div className="col-md-6">
              <div className="form-group mb-3">
                <label htmlFor="degree">Ступінь</label>
                <select
                  name="degree"
                  className={`form-control ${errors.degree ? 'is-invalid' : ''}`}
                  id="degree"
                  value={formData.degree}
                  onChange={handleChange}
                >
                  <option value="">Оберіть ступінь</option>
                  <option value="Бакалавр">Бакалавр</option>
                  <option value="Магістр">Магістр</option>
                </select>
                {errors.degree && <div className="invalid-feedback">{errors.degree}</div>}
              </div>
            </div>
          </div>

          <div className="row">
            {/* Group */}
            <div className="col-md-12">
              <div className="form-group mb-3">
                <label htmlFor="group">Група</label>
                <input
                  type="text"
                  name="group"
                  className={`form-control ${errors.group ? 'is-invalid' : ''}`}
                  id="group"
                  value={formData.group}
                  onChange={handleChange}
                  placeholder="Введіть групу"
                />
                {errors.group && <div className="invalid-feedback">{errors.group}</div>}
              </div>
            </div>
          </div>

          <div className="form-group">
            <button type="submit" className="btn btn-primary btn-block w-100">
              Зареєструватися
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegistrationForm;
