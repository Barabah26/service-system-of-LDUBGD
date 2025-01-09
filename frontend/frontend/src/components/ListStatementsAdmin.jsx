import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Button, Container, Row, Col, Form, Alert, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const ListStatementComponent = () => {
  const [statements, setStatements] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedFaculty, setSelectedFaculty] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [noResults, setNoResults] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [file, setFile] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');
  const [showModal, setShowModal] = useState(false);

  const navigate = useNavigate();

  useEffect(() => {
    fetchStatements();
  }, [selectedFaculty, selectedStatus]);

  const fetchStatements = async () => {
    setLoading(true);
    const token = localStorage.getItem('accessToken');
    try {
      const response = await axios.get('http://localhost:9000/api/statements/statusAndFaculty', {
        params: {
          status: selectedStatus || undefined,
          faculty: selectedFaculty || undefined,
        },
        headers: { Authorization: `Bearer ${token}` },
      });
      setStatements(response.data);
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

  const handleFacultyChange = (e) => {
    setSelectedFaculty(e.target.value);
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
      await axios.put(`http://localhost:9000/api/statements/${id}/in-progress`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchStatements();
    } catch (error) {
      console.error('Error marking statement as IN_PROGRESS:', error);
    }
  };

  const handleReady = async (id) => {
    if (!window.confirm("Ви впевнені, що хочете змінити статус на 'Готово'?")) return;
    const token = localStorage.getItem('accessToken');
    try {
      await axios.put(`http://localhost:9000/api/statements/${id}/ready`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchStatements();
    } catch (error) {
      console.error('Error marking statement as READY:', error);
    }
  };

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleFileUpload = async (statementId) => {
    if (!file) {
      alert('Будь ласка, виберіть файл для завантаження.');
      return;
    }

    const formData = new FormData();
    formData.append('file', file);

    const token = localStorage.getItem('accessToken');
    try {
      await axios.post(`http://localhost:9000/api/files/upload/${statementId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${token}`,
        },
      });
      setShowModal(true);
    } catch (error) {
      alert('Виникла помилка при завантаженні файлу.');
    }
  };

  const filteredStatements = statements.filter(statement =>
      statement.fullName.toLowerCase().includes(searchQuery.toLowerCase())
  );

  return (
      <Container className="students-container">
        <h2 className="my-4">Список заявок</h2>

        {/* Поле для пошуку */}
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

        {/* Фільтри для факультету і статусу */}
        <Row className="mb-3">
          <Col md={6}>
            <Form.Group controlId="facultyFilter">
              <Form.Label>Оберіть свій факультет:</Form.Label>
              <Form.Control as="select" value={selectedFaculty} onChange={handleFacultyChange}>
                <option value="">Оберіть факультет</option>
                <option value="Факультет цивільного захисту">Факультет цивільного захисту</option>
                <option value="Факультет пожежної та техногенної безпеки">Факультет пожежної та техногенної безпеки</option>
                <option value="Факультет психології і соціального захисту">Факультет психології і соціального захисту</option>
                <option value="Інститут післядипломної освіти">Інститут післядипломної освіти</option>
                <option value="Ад'юктура">Ад'юктура</option>
                <option value="Навчально-методичний центр">Навчально-методичний центр</option>
              </Form.Control>
            </Form.Group>
          </Col>
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
                      {selectedStatus !== 'READY' && <th>Дія</th>}
                      {selectedStatus === 'IN_PROGRESS' && <th className="wide-column">Завантажити файл</th>}
                    </tr>
                    </thead>
                    <tbody>
                    {filteredStatements.map((statement) => (
                        <tr key={statement.id}>
                          <td>{statement.fullName}</td>
                          <td>{statement.groupName}</td>
                          <td>{statement.yearBirthday}</td>
                          <td>{statement.phoneNumber}</td>
                          <td>{statement.typeOfStatement}</td>
                          <td>{statement.faculty}</td>
                          <td>{statement.status}</td>
                          {statement.status !== 'READY' && (
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
                              </td>
                          )}
                          {statement.status === 'В процесі' && (
                              <td>
                                <input type="file" onChange={handleFileChange} />
                                <Button variant="primary" onClick={() => handleFileUpload(statement.id)}>
                                  Завантажити
                                </Button>
                              </td>
                          )}
                        </tr>
                    ))}
                    </tbody>
                  </Table>
              )}
            </>
        )}

        {/* Модальне вікно для підтвердження успішного завантаження файлу */}
        <Modal show={showModal} onHide={() => setShowModal(false)}>
          <Modal.Header closeButton>
            <Modal.Title>Завантаження файлу</Modal.Title>
          </Modal.Header>
          <Modal.Body>Файл успішно завантажено!</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={() => setShowModal(false)}>
              Закрити
            </Button>
          </Modal.Footer>
        </Modal>
      </Container>
  );
};

export default ListStatementComponent;
