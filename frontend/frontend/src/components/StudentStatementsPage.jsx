import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Container, Row, Col, Form, Alert } from 'react-bootstrap';

const StatementsPage = () => {
  const [statements, setStatements] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  // Функція для отримання ідентифікатора користувача з токена
  const parseJwt = (token) => {
    try {
      const base64Url = token.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(
        atob(base64)
          .split('')
          .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
          .join('')
      );
      return JSON.parse(jsonPayload);
    } catch (error) {
      console.error('Помилка при розборі JWT:', error);
      return null;
    }
  };

  const fetchStatements = async () => {
    const token = localStorage.getItem('accessToken'); // Отримуємо токен з localStorage
    if (!token) {
      setError('User is not authenticated');
      return;
    }

    const decodedToken = parseJwt(token); // Розбираємо токен для отримання даних користувача
    const userFullName = decodedToken ? decodedToken.sub : null; // Отримуємо повне ім'я користувача (sub)

    if (!userFullName) {
      setError('Invalid token');
      return;
    }

    setLoading(true);
    try {
      // Запит до API для отримання довідок для користувача за його повним іменем
      const response = await axios.get(`http://localhost:9080/api/statements/findByFullName`, {
        params: { fullName: userFullName }, // Передаємо fullName як параметр запиту
        headers: {
          Authorization: `Bearer ${token}` // Додаємо токен до заголовка
        }
      });
      setStatements(response.data);
    } catch (err) {
      setError('Failed to load statements');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchStatements();
  }, []);

  return (
    <Container style={{ marginTop: '60px' }} className="students-container">
<h2 className="my-4" style={{ textAlign: 'center' }}>Список заявок</h2>

      {/* Повідомлення про помилки */}
      {error && <Alert variant="danger">{error}</Alert>}

      {loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          {statements.length === 0 ? (
            <p>No statements found</p>
          ) : (
            <Table striped bordered hover>
              <thead>
              <tr>
                    <th>ПІБ</th>
                    <th>Група</th>
                    <th>Дата народження</th>
                    <th>Номер телефону</th>
                    <th>Тип заявки</th>
                    <th>Факультет</th>
                    <th className="wide-column">Статус</th>
                  </tr>
              </thead>
              <tbody>
                {statements.map((statement) => (
                  <tr key={statement.id}>
                    <td>{statement.fullName}</td>
                    <td>{statement.groupName}</td>
                    <td>{statement.yearBirthday}</td>
                    <td>{statement.phoneNumber}</td>
                    <td>{statement.typeOfStatement}</td>
                    <td>{statement.faculty}</td>
                    <td>{statement.status}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          )}
        </div>
      )}
    </Container>
  );
};

export default StatementsPage;
