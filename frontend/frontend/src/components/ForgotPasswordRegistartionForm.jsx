import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, Card } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';
import API_ENDPOINTS from './apiConfig'; 

const ForgotPasswordRegistrationForm = () => {
  const [formData, setFormData] = useState({
    fullName: '',
    group: '',
    faculty: '',
    typeOfForgotPassword: '',
    userId: '',
  });

  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const location = useLocation();
  const navigate = useNavigate();

  const queryParams = new URLSearchParams(location.search);
  const selectedType = queryParams.get('type') || '';

  useEffect(() => {
    const fetchUserInfo = async () => {
      try {
        const token = localStorage.getItem('accessToken');
        const response = await axios.get(API_ENDPOINTS.AUTH.PROFILE, {
          headers: { Authorization: `Bearer ${token}` },
        });

        setFormData((prevData) => ({
          ...prevData,
          fullName: response.data.fullName || '',
          group: response.data.group || '',
          faculty: response.data.faculty || '',
          typeOfForgotPassword: selectedType,
          userId: response.data.userId,
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
    try {
      const token = localStorage.getItem('accessToken');
      await axios.post(API_ENDPOINTS.FORGOT_PASSWORD.CREATE_STATEMENT, formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      
      setSuccessMessage('Заявку успішно створено!');
      setErrorMessage('');
      setTimeout(() => navigate('/user-info'), 1000);
    } catch (error) {
      console.error('Помилка при створенні заявки:', error);
      setErrorMessage('Не вдалося створити заявку. Спробуйте ще раз.');
    }
  };

  return (
    <Container className="d-flex justify-content-center align-items-center vh-100">
      <Card className="p-4 shadow-lg w-100" style={{ maxWidth: '600px' }}>
        <h2 className="mb-4 text-center text-uppercase">Заявка на відновлення паролю</h2>
        {successMessage && <Alert variant="success">{successMessage}</Alert>}
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
        
        <Form onSubmit={handleSubmit}>
          <Row className="mb-3">
            <Col>
              <Form.Group controlId="fullName">
                <Form.Label>ПІБ</Form.Label>
                <Form.Control type="text" name="fullName" value={formData.fullName} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-3">
            <Col>
              <Form.Group controlId="group">
                <Form.Label>Група</Form.Label>
                <Form.Control type="text" name="group" value={formData.group} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-3">
            <Col>
              <Form.Group controlId="faculty">
                <Form.Label>Факультет</Form.Label>
                <Form.Control type="text" name="faculty" value={formData.faculty} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-3">
            <Col>
              <Form.Group controlId="typeOfForgotPassword">
                <Form.Label>Тип відновлення паролю</Form.Label>
                <Form.Control as="select" name="typeOfForgotPassword" value={formData.typeOfForgotPassword} onChange={handleChange} required>
                  <option value="">Оберіть тип відновлення</option>
                  <option value="Пароль до журналу">Пароль до журналу</option>
                  <option value="Пароль до віртуального університету">Пароль до віртуального університету</option>
                </Form.Control>
              </Form.Group>
            </Col>
          </Row>
          <Button variant="success" type="submit" className="w-100 mt-4">
            Подати заявку
          </Button>
        </Form>
      </Card>
    </Container>
  );
};

export default ForgotPasswordRegistrationForm;
