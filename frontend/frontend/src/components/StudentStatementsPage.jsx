import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Card, Button, Collapse, Alert } from 'react-bootstrap';

const StatementsPage = () => {
  const [statements, setStatements] = useState([]);
  const [forgotPasswords, setForgotPasswords] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [openStatement, setOpenStatement] = useState(null);
  const [openForgotPassword, setOpenForgotPassword] = useState(null);

  const toggleStatement = (id) => setOpenStatement(openStatement === id ? null : id);
  const toggleForgotPassword = (id) => setOpenForgotPassword(openForgotPassword === id ? null : id);

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

  const fetchForgotPasswords = async () => { 
    const token = localStorage.getItem('accessToken'); // Отримуємо токен з localStorage
    if (!token) {
      setError('User is not authenticated');
      return;
    }
  
    const decodedToken = parseJwt(token); // Розбираємо токен для отримання даних користувача
    const userId = decodedToken ? decodedToken.userId : null; // Отримуємо userId з токену (замість fullName)
  
    if (!userId) {
      setError('Invalid token');
      return;
    }
  
    setLoading(true);
    try {
      // Запит до API для отримання довідок для користувача за його userId
      const response = await axios.get(`http://localhost:8095/api/forgot-password/findByUserId`, {
        params: { userId: userId }, // Передаємо userId як параметр запиту
        headers: {
          Authorization: `Bearer ${token}` // Додаємо токен до заголовка
        }
      });
      setForgotPasswords(response.data); // Зберігаємо отриману інформацію
    } catch (err) {
      setError('Failed to load forgot passwords');
    } finally {
      setLoading(false);
    }
  };
  


  useEffect(() => {
    fetchStatements();
    fetchForgotPasswords();
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
          {/* Виведення заявок */}
          <div>
            {statements.length === 0 ? (
              <p>No statements found</p>
            ) : (
              <div className="row">
                {statements.map((statement) => (
                  <div key={statement.id} className="col-sm-6 col-md-4 col-lg-3 mb-3">  {/* Використовуємо колонки для різних екранів */}
                    <Card>
                      <Card.Body>
                        <Card.Title>{statement.fullName}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{statement.groupName}</Card.Subtitle>
                        <Card.Text>Тип заявки: {statement.typeOfStatement}</Card.Text>
                        <Collapse in={openStatement === statement.id}>
                          <div>
                            <p>Дата народження: {statement.yearBirthday}</p>
                            <p>Номер телефону: {statement.phoneNumber}</p>
                            <p>Статус: {statement.status}</p>
                            <p>Факультет: {statement.faculty}</p>
                          </div>
                        </Collapse>
                        {/* Кнопка переміщається під інформацією */}
                        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                          <Button onClick={() => toggleStatement(statement.id)}>
                            {openStatement === statement.id ? 'Сховати інформацію' : 'Показати додатково'}
                          </Button>
                        </div>
                      </Card.Body>
                    </Card>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Виведення заявок на скидання пароля */}
          <div style={{ marginTop: '40px' }}>
            {forgotPasswords.length === 0 ? (
              <p>No forgot password requests found</p>
            ) : (
              <div className="row">
                {forgotPasswords.map((forgotPassword) => (
                  <div key={forgotPassword.id} className="col-sm-6 col-md-4 col-lg-3 mb-3">  {/* Використовуємо колонки для різних екранів */}
                    <Card>
                      <Card.Body>
                        <Card.Title>{forgotPassword.fullName}</Card.Title>
                        <Card.Subtitle className="mb-2 text-muted">{forgotPassword.groupName}</Card.Subtitle>
                        <Card.Text>Тип заявки: {forgotPassword.typeOfForgotPassword}</Card.Text>
                        <Collapse in={openForgotPassword === forgotPassword.id}>
                          <div>
                            <p>Статус: {forgotPassword.status}</p>
                            <p>Факультет: {forgotPassword.faculty}</p>
                          </div>
                        </Collapse>
                        {/* Кнопка переміщається під інформацією */}
                        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
                          <Button onClick={() => toggleForgotPassword(forgotPassword.id)}>
                            {openForgotPassword === forgotPassword.id ? 'Сховати інформацію' : 'Показати додатково'}
                          </Button>
                        </div>
                      </Card.Body>
                    </Card>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      )}
    </Container>
  );
};

export default StatementsPage;
