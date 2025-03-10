import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert, Row, Col, Card } from 'react-bootstrap';
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
          dateBirth: response.data.dateBirth || '',
          group: response.data.group || '',
          phoneNumber: response.data.phoneNumber || '',
          faculty: response.data.faculty || '',
          typeOfStatement: selectedType,
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
      await axios.post(API_ENDPOINTS.STATEMENTS.CREATE_STATEMENT, formData, {
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
        <h2 className="mb-2 text-center text-uppercase">Реєстрація довідки</h2>
        {successMessage && <Alert variant="success">{successMessage}</Alert>}
        {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

        <Form onSubmit={handleSubmit}>
          <Row className="mb-2">
            <Col>
              <Form.Group controlId="fullName">
                <Form.Label>ПІБ</Form.Label>
                <Form.Control type="text" name="fullName" value={formData.fullName} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-2">
            <Col>
              <Form.Group controlId="dateBirth">
                <Form.Label>Дата народження</Form.Label>
                <Form.Control type="text" name="dateBirth" value={formData.dateBirth} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-2">
            <Col>
              <Form.Group controlId="group">
                <Form.Label>Група</Form.Label>
                <Form.Control type="text" name="group" value={formData.group} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-2">
            <Col>
              <Form.Group controlId="phoneNumber">
                <Form.Label>Номер телефону</Form.Label>
                <Form.Control type="text" name="phoneNumber" value={formData.phoneNumber} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-2">
            <Col>
              <Form.Group controlId="faculty">
                <Form.Label>Факультет</Form.Label>
                <Form.Control type="text" name="faculty" value={formData.faculty} disabled />
              </Form.Group>
            </Col>
          </Row>
          <Row className="mb-2">
            <Col>
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
                  <option value="Довідка для військкомату (Форма 20)">Довідка для військкомату (Форма 20)</option>
                  <option value="Довідка з місця навчання">Довідка з місця навчання</option>
                  <option value="Довідка (Форма 9)">Довідка (Форма 9)</option>
                </Form.Control>
              </Form.Group>
            </Col>
          </Row>
          <Button variant="success" type="submit" className="w-100 mt-2">
            Зареєструвати
          </Button>
        </Form>
      </Card>
    </Container>
  );
};

export default StatementRegistrationForm;
