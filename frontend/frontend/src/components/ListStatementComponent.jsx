import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert } from 'react-bootstrap';

const StatementRegistrationForm = () => {
  const [formData, setFormData] = useState({
    fullName: '',
    yearBirthday: '',
    group: '',
    phoneNumber: '',
    faculty: '',
    typeOfStatement: '',
  });

  const [successMessage, setSuccessMessage] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

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
        yearBirthday: '',
        group: '',
        phoneNumber: '',
        faculty: '',
        typeOfStatement: '',
      });
    } catch (error) {
      setErrorMessage('Помилка при створенні заявки.');
      console.error(error);
    }
  };

  return (
    <Container>
      <h2>Реєстрація довідки</h2>
      {successMessage && <Alert variant="success">{successMessage}</Alert>}
      {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}

      <Form onSubmit={handleSubmit}>
        <Form.Group controlId="fullName">
          <Form.Label>ПІБ</Form.Label>
          <Form.Control
            type="text"
            name="fullName"
            placeholder="Введіть ПІБ (Прізвище Ім'я По батькові)"
            value={formData.fullName}
            onChange={handleChange}
            pattern="^[А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+ [А-ЯІЇЄҐ][а-яіїєґ]+$"
            title="ПІБ має бути у форматі 'Прізвище Ім'я По батькові'"
            required
          />
        </Form.Group>

        <Form.Group controlId="yearBirthday">
          <Form.Label>Дата народження</Form.Label>
          <Form.Control
            type="text"
            name="yearBirthday"
            placeholder="Введіть дату народження (dd-MM-yyyy)"
            value={formData.yearBirthday}
            onChange={handleChange}
            // pattern="^(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[0-2])-(\\d{4})$"
            title="Дата має бути у форматі 'dd-MM-yyyy'"
            required
          />
        </Form.Group>

        <Form.Group controlId="group">
          <Form.Label>Група</Form.Label>
          <Form.Control
            type="text"
            name="group"
            placeholder="Введіть групу"
            value={formData.group}
            onChange={handleChange}
            // pattern="^[А-ЯІЇЄҐ][A-Za-z0-9]+$"
            title="Група не повинна містити спеціальні символи"
            required
          />
        </Form.Group>

        <Form.Group controlId="phoneNumber">
          <Form.Label>Номер телефону</Form.Label>
          <Form.Control
            type="text"
            name="phoneNumber"
            placeholder="Введіть номер телефону (+380xxxxxxxxx)"
            value={formData.phoneNumber}
            onChange={handleChange}
            pattern="^\+380[0-9]{9}$"
            title="Номер телефону має бути у форматі '+380xxxxxxxxx'"
            required
          />
        </Form.Group>

        <Form.Group controlId="faculty">
          <Form.Label>Факультет</Form.Label>
          <Form.Control
            as="select"
            name="faculty"
            value={formData.faculty}
            onChange={handleChange}
            required
          >
            <option value="">Оберіть факультет</option>
            <option value="Факультет цивільного захисту">Факультет цивільного захисту</option>
            <option value="Факультет пожежної та техногенної безпеки">Факультет пожежної та техногенної безпеки</option>
            <option value="Факультет психології і соціального захисту">Факультет психології і соціального захисту</option>
            <option value="Інститут післядипломної освіти">Інститут післядипломної освіти</option>
            <option value="Ад'юктура">Ад'юктура</option>
            <option value="Навчально-методичний центр">Навчально-методичний центр</option>
          </Form.Control>
        </Form.Group>

        <Form.Group controlId="typeOfStatement">
          <Form.Label>Тип довідки</Form.Label>
          <Form.Control
            type="text"
            name="typeOfStatement"
            placeholder="Введіть тип довідки"
            value={formData.typeOfStatement}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Button variant="primary" type="submit">
          Зареєструвати
        </Button>
      </Form>
    </Container>
  );
};

export default StatementRegistrationForm;
