import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Card, Button, Collapse, Alert } from 'react-bootstrap';
import API_ENDPOINTS from './apiConfig'; 

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
    const token = localStorage.getItem('accessToken');
    if (!token) {
      setError('User is not authenticated');
      return;
    }
  
    const decodedToken = parseJwt(token);
    const userFullName = decodedToken ? decodedToken.sub : null;
  
    if (!userFullName) {
      setError('Invalid token');
      return;
    }
  
    setLoading(true);
    try {
      const response = await axios.get(API_ENDPOINTS.STATEMENTS.FIND_BY_FULL_NAME, {
        params: { fullName: userFullName },
        headers: { Authorization: `Bearer ${token}` },
      });
      setStatements(response.data);
    } catch (err) {
      setError('Failed to load statements');
    } finally {
      setLoading(false);
    }
  };
  

  const fetchForgotPasswords = async () => { 
    const token = localStorage.getItem('accessToken');
    if (!token) {
      setError('User is not authenticated');
      return;
    }
  
    const decodedToken = parseJwt(token);
    const userId = decodedToken ? decodedToken.userId : null;
  
    if (!userId) {
      setError('Invalid token');
      return;
    }
  
    setLoading(true);
    try {
      const response = await axios.get(API_ENDPOINTS.FORGOT_PASSWORD.FIND_BY_USER_ID, {
        params: { userId: userId },
        headers: { Authorization: `Bearer ${token}` },
      });
      setForgotPasswords(response.data);
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
              <p>Покищо запити на створення довідок відсутні</p>
            ) : (
              <div className="row">
                {statements.map((statement) => (
                  <div key={statement.id} className="col-sm-6 col-md-4 col-lg-3 mb-3">  {/* Використовуємо колонки для різних екранів */}
                    <Card style={{minWidth:"300px",minHeight:"200px"}}>
                      <Card.Body>
                        <Card.Title><strong>{statement.fullName}</strong></Card.Title>
                        <Card.Subtitle className="mb-2 text-muted"><strong>{statement.groupName}</strong></Card.Subtitle>
                        <Card.Text><strong>Тип заявки:</strong> {statement.typeOfStatement}</Card.Text>
                        <Collapse in={openStatement === statement.id}>
                          <div>
                            <p><strong>Дата народження:</strong> {statement.yearBirthday}</p>
                            <p><strong>Номер телефону:</strong> {statement.phoneNumber}</p>
                            <p><strong>Статус:</strong> {statement.status}</p>
                            <p><strong>Факультет:</strong> {statement.faculty}</p>
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
              <p>Покищо запити на скидання паролю відсутні</p>
            ) : (
              <div className="row">
                {forgotPasswords.map((forgotPassword) => (
                  <div key={forgotPassword.id} className="col-sm-6 col-md-4 col-lg-3 mb-3">  {/* Використовуємо колонки для різних екранів */}
                    <Card style={{minWidth:"300px",minHeight:"200px"}}>
                      <Card.Body>
                        <Card.Title><strong>{forgotPassword.fullName}</strong></Card.Title>
                        <Card.Subtitle className="mb-2 text-muted"><strong>{forgotPassword.groupName}</strong></Card.Subtitle>
                        <Card.Text><strong>Тип заявки:</strong> {forgotPassword.typeOfForgotPassword}</Card.Text>
                        <Collapse in={openForgotPassword === forgotPassword.id}>
                          <div>
                            <p><strong>Статус:</strong> {forgotPassword.status}</p>
                            <p><strong>Факультет:</strong> {forgotPassword.faculty}</p>
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
