import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col } from 'react-bootstrap';
import { useLocation, useNavigate } from 'react-router-dom';

const StatementRegistrationForm = () => {
  const [formData, setFormData] = useState({
    fullName: '',
    dateBirth: '',
    group: '',
    phoneNumber: '',
    faculty: '',
    typeOfStatement: '',
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
        const response = await axios.get('http://localhost:9000/api/auth/profile', {
          headers: { Authorization: `Bearer ${token}` },
        });

        setFormData((prevData) => ({
          ...prevData,
          fullName: response.data.fullName || '',
          dateBirth: response.data.dateBirth || '',
          group: response.data.group || '',
          phoneNumber: response.data.phoneNumber || '',
          faculty: response.data.faculty || '',
          typeOfStatement: selectedType, // Set the selected type of statement
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
      await axios.post('http://localhost:9080/api/statements/createStatement', formData, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccessMessage('Заявку успішно створено!');
      setErrorMessage('');
      setFormData({
        fullName: '',
        dateBirth: '',
        group: '',
        phoneNumber: '',
        faculty: '',
        typeOfStatement: '',
      });

      setTimeout(() => {
        navigate('/user-info');
      }, 1000);

    } catch (error) {
      setErrorMessage('Помилка при створенні заявки.');
      console.error(error);
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
                onChange={handleChange}
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
