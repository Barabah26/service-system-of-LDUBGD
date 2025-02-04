import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Button, Container, Row, Col, Form, Alert, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const TechAdminPage = () => {
  const [forgotPasswords, setForgotPasswords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedStatus, setSelectedStatus] = useState('');
  const [noResults, setNoResults] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [messageMap, setMessageMap] = useState({});

  const navigate = useNavigate();

  useEffect(() => {
    fetchStatements();
  }, [selectedStatus]);

  const fetchStatements = async () => {
    setLoading(true);
    const token = localStorage.getItem('accessToken');
    try {
      const response = await axios.get('http://localhost:8095/api/forgot-password/status', {
        params: {
          status: selectedStatus || undefined,
        },
        headers: { Authorization: `Bearer ${token}` },
      });
      setForgotPasswords(response.data);
      setNoResults(response.data.length === 0);
      setErrorMessage('');
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setNoResults(true);
        setErrorMessage('Заявки не знайдено для вказаних параметрів.');
      } else {
        setErrorMessage('Виникла помилка при отриманні заявок.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleStatusChange = (e) => {
    setSelectedStatus(e.target.value);
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleInProgress = async (id) => {
    if (!window.confirm("Ви впевнені, що хочете змінити статус на 'В обробці'?")) return;
    const token = localStorage.getItem('accessToken');
    try {
      await axios.put(`http://localhost:8095/api/forgot-password/${id}/in-progress`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccessMessage("Статус заявки змінено на 'В процесі' успішно!");
      fetchStatements();
    } catch (error) {
      console.error('Error marking forgot password as IN_PROGRESS:', error);
    }
  };

  const handleReady = async (id) => {
    if (!window.confirm("Ви впевнені, що хочете змінити статус на 'Готово'?")) return;
    const token = localStorage.getItem('accessToken');
    try {
      await axios.put(`http://localhost:8095/api/forgot-password/${id}/ready`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccessMessage("Статус заявки змінено на 'Готово' успішно!");
      fetchStatements();
    } catch (error) {
      console.error('Error marking forgot password as READY:', error);
    }
  };

  const handleMessageChange = (e, statementId, field) => {
    setMessageMap((prev) => ({
      ...prev,
      [statementId]: {
        ...prev[statementId],
        [field]: e.target.value,
      },
    }));
  };
  
  const handleSaveData = async (statementId, login, password) => {
    if (!window.confirm("Ви впевнені, що хочете надіслати ці дані?")) return;
  
    try {
      const response = await axios.put(`http://localhost:8095/api/forgot-password/${statementId}`, {
        login,
        password,
      });
  
      alert(response.data); 
    } catch (error) {
      console.error("Помилка оновлення:", error);
      alert("Логін і пароль вже збережені! Змініть статус заявки!");
    }
  };
  
  
  const filteredStatements = forgotPasswords.filter(statement =>
    statement.typeOfForgotPassword === "Пароль до віртуального університету"
  );

  return (
    <Container className="students-container">
      <h2 className="my-4">Список заявок на відновлення паролю до віртуального університету</h2>

      <Row className="mb-3">
        <Col md={12}>
          <Form.Group controlId="searchQuery">
            <Form.Label>Пошук за ПІБ:</Form.Label>
            <Form.Control
              type="text"
              placeholder="Введіть ПІБ"
              value={searchQuery}
              onChange={handleSearchChange}
            />
          </Form.Group>
        </Col>
      </Row>

      {/* Фільтр за статусом */}
      <Row className="mb-3">
        <Col md={6}>
          <Form.Group controlId="statusFilter">
            <Form.Label>Оберіть статус заявки:</Form.Label>
            <Form.Control as="select" value={selectedStatus} onChange={handleStatusChange}>
              <option value="">Оберіть статус</option>
              <option value="PENDING">Очікується</option>
              <option value="IN_PROGRESS">В процесі</option>
              <option value="READY">Готово</option>
            </Form.Control>
          </Form.Group>
        </Col>
      </Row>

      {/* Повідомлення про успіх */}
      {successMessage && (
        <Alert variant="success" onClose={() => setSuccessMessage('')} dismissible>
          {successMessage}
        </Alert>
      )}

      {/* Таблиця заявок */}
      {loading ? (
        <p>Завантаження...</p>
      ) : (
        <>
          {noResults ? (
            <p className="text-center">{errorMessage}</p>
          ) : (
            <Table striped bordered hover className="wide-table">
              <thead>
                <tr>
                  <th>ПІБ</th>
                  <th>Група</th>
                  <th>Факультет</th>
                  <th>Тип заявки</th>
                  <th>Статус</th>
                  <th>Дія</th>
                  <th>Повідомлення</th>
                </tr>
              </thead>
              <tbody>
                {filteredStatements
                  .filter(statement =>
                    statement.fullName.toLowerCase().includes(searchQuery.toLowerCase())
                  )
                  .map((statement) => (
                    <tr key={statement.id}>
                      <td>{statement.fullName}</td>
                      <td>{statement.groupName}</td>
                      <td>{statement.faculty}</td>
                      <td>{statement.typeOfForgotPassword}</td>
                      <td>{statement.status}</td>
                      <td>
                        {statement.status === 'В очікуванні' && (
                          <Button variant="success" onClick={() => handleInProgress(statement.id)}>
                            В обробку
                          </Button>
                        )}

                        {statement.status === 'В процесі' && (
                          <Button variant="success" onClick={() => handleReady(statement.id)}>
                            Готово
                          </Button>
                        )}
                        {statement.status === 'Готово' && <span>Недоступно</span>}
                      </td>
                      <td>
                        {statement.status === 'В процесі' && (
                          <>
                            <Form.Control
                              type="text"
                              placeholder="Введіть логін"
                              value={messageMap[statement.id]?.login || ''}
                              onChange={(e) => handleMessageChange(e, statement.id, 'login')}
                            />
                            <Form.Control
                              type="password"
                              placeholder="Введіть пароль"
                              value={messageMap[statement.id]?.password || ''}
                              onChange={(e) => handleMessageChange(e, statement.id, 'password')}
                              />
                              <Button
                              variant="primary"
                              size="sm"
                              className="mt-4"
                              onClick={() =>
                                handleSaveData(statement.id, messageMap[statement.id]?.login, messageMap[statement.id]?.password)
                              }
                            >
                              Надіслати
                            </Button>
                          </>
                        )}
                        {(statement.status === 'Готово' || statement.status === 'В очікуванні') && <span>Недоступно</span>}
                      </td>
                    </tr>
                  ))}
              </tbody>
            </Table>
          )}
        </>
      )}
    </Container>
  );
};

export default TechAdminPage;
