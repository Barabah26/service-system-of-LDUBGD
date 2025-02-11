import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';
import API_ENDPOINTS from './apiConfig'; 


const StatementRegistrationForm = () => {
  const [formData, setFormData] = useState({
    fullName: '',
    dateBirth: '',
    group: '',
    phoneNumber: '',
    faculty: '',
    typeOfStatement: '',
    userId: '',  
  });
  

  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const selectedType = queryParams.get('type') || ''; // Get type from query params

  const navigate = useNavigate(); // Use navigate for redirect

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem('accessToken');
        const response = await axios.get(API_ENDPOINTS.AUTH.PROFILE, {
          headers: { Authorization: `Bearer ${token}` },
        });
  
        // Додаємо userId до стейту
        setFormData((prevData) => ({
          ...prevData,
          fullName: response.data.fullName || '',
          dateBirth: response.data.dateBirth || '',
          group: response.data.group || '',
          phoneNumber: response.data.phoneNumber || '',
          faculty: response.data.faculty || '',
          typeOfStatement: selectedType, // Set the selected type of statement
          userId: response.data.userId,  // Передаємо userId з профілю
        }));
      } catch (error) {
        console.error('Не вдалося отримати інформацію про користувача:', error);
      }
    };
  
    fetchUserInfo();
  }, [selectedType]);
  
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Form Data before submitting:', formData);  // Log data before submitting
  
    try {
      const token = localStorage.getItem('accessToken');
      const response = await axios.post(API_ENDPOINTS.STATEMENTS.CREATE_STATEMENT, formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      console.log('Response from backend:', response);  // Log the backend response
  
      setSuccessMessage('Заявку успішно створено!');
      setErrorMessage('');
      setFormData({
        fullName: '',
        dateBirth: '',
        group: '',
        phoneNumber: '',
        faculty: '',
        typeOfStatement: '',
        userId: '', // Clear userId after submission
      });
  
      setTimeout(() => {
        navigate('/user-info');
      }, 1000);
    } catch (error) {
      console.error('Error during form submission:', error);
      setErrorMessage('Помилка при створенні заявки.');
    }
  };
  
  
  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-center text-uppercase">Реєстрація довідки</h2>
      {successMessage && <Alert variant="success">{successMessage}</Alert>}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

      <Form onSubmit={handleSubmit}>
        <Row className="mb-3">
          <Col sm={12} md={6}>
            <Form.Group controlId="fullName">
              <Form.Label>ПІБ</Form.Label>
              <Form.Control
                type="text"
                name="fullName"
                value={formData.fullName}
                onChange={false}
              />
            </Form.Group>
          </Col>
          <Col sm={12} md={6}>
            <Form.Group controlId="yearBirthday">
              <Form.Label>Дата народження</Form.Label>
              <Form.Control
                type="text"
                name="dateBirth"
                value={formData.dateBirth}
                onChange={handleChange}
              />
            </Form.Group>
          </Col>
        </Row>
        <Row className="mb-3">
          <Col sm={12} md={6}>
            <Form.Group controlId="group">
              <Form.Label>Група</Form.Label>
              <Form.Control
                type="text"
                name="group"
                value={formData.group}
                onChange={handleChange}
              />
            </Form.Group>
          </Col>
          <Col sm={12} md={6}>
            <Form.Group controlId="phoneNumber">
              <Form.Label>Номер телефону</Form.Label>
              <Form.Control
                type="text"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
              />
            </Form.Group>
          </Col>
        </Row>
        <Row className="mb-3">
          <Col sm={12} md={6}>
            <Form.Group controlId="faculty">
              <Form.Label>Факультет</Form.Label>
              <Form.Control
                type="text"
                name="faculty"
                value={formData.faculty}
                onChange={handleChange}
              />
            </Form.Group>
          </Col>
          <Col sm={12} md={6}>
            <Form.Group controlId="typeOfStatement">
              <Form.Label>Тип довідки</Form.Label>
              <Form.Control
                as="select"
                name="typeOfStatement"
                value={formData.typeOfStatement}
                onChange={handleChange}
                required
              >
                <option value="">Оберіть тип довідки</option>
                <option value="Довідка для військкомату (Форма 20)">
                  Довідка для військкомату (Форма 20)
                </option>
                <option value="Довідка з місця навчання">Довідка з місця навчання</option>
                <option value="Довідка (Форма 9)">Довідка (Форма 9)</option>
              </Form.Control>
            </Form.Group>
          </Col>
        </Row>
        <Button variant="success" type="submit" className="w-100 mt-4">
          Зареєструвати
        </Button>
      </Form>
    </Container>
  );
};

export default StatementRegistrationForm;
