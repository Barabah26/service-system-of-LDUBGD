import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Button, Container, Row, Col, Form, Alert, Modal } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import '../App.css';

const ListStatementComponent = () => {
  // Стан для заявок на довідки та для заявок на забутий пароль
  const [statements, setStatements] = useState([]);
  const [forgotPasswords, setForgotPasswords] = useState([]);

  const [loading, setLoading] = useState(true);
  // selectedRequestType: "statements" для заявок на довідки, "forgotPassword" для заявок на забутий пароль
  const [selectedRequestType, setSelectedRequestType] = useState('statements');

  const [selectedFaculty, setSelectedFaculty] = useState('');
  const [selectedStatus, setSelectedStatus] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [noResults, setNoResults] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  const [file, setFile] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');
  const [showModal, setShowModal] = useState(false);
  // Для збереження тексту повідомлення для конкретної заявки
  const [messageMap, setMessageMap] = useState({});

  const navigate = useNavigate();

  useEffect(() => {
    if (selectedRequestType === 'statements') {
      fetchStatements();
    } else if (selectedRequestType === 'forgotPassword') {
      fetchForgotPassword();
    }
  }, [selectedRequestType, selectedFaculty, selectedStatus]);

  // Отримання заявок на довідки
  const fetchStatements = async () => {
    setLoading(true);
    const token = localStorage.getItem('accessToken');
    try {
      const response = await axios.get('http://localhost:9080/api/statements/statusAndFaculty', {
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

  // Отримання заявок на забутий пароль
  const fetchForgotPassword = async () => {
    setLoading(true);
    const token = localStorage.getItem('accessToken');
    try {
      const response = await axios.get('http://localhost:8095/api/forgot-password/statusAndFaculty', {
        params: {
          status: selectedStatus || undefined,
          faculty: selectedFaculty || undefined,
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
      if (selectedRequestType === 'statements') {
        await axios.put(`http://localhost:9080/api/statements/${id}/in-progress`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        fetchStatements();
      } else if (selectedRequestType === 'forgotPassword') {
        await axios.put(`http://localhost:8095/api/forgot-password/${id}/in-progress`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        fetchForgotPassword();
      }
      setSuccessMessage("Статус заявки змінено на 'В процесі' успішно!");
    } catch (error) {
      console.error('Error marking as IN_PROGRESS:', error);
    }
  };

  const handleReady = async (id) => {
    if (!window.confirm("Ви впевнені, що хочете змінити статус на 'Готово'?")) return;
    const token = localStorage.getItem('accessToken');
    try {
      if (selectedRequestType === 'statements') {
        await axios.put(`http://localhost:9080/api/statements/${id}/ready`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        fetchStatements();
      } else if (selectedRequestType === 'forgotPassword') {
        await axios.put(`http://localhost:8095/api/forgot-password/${id}/ready`, {}, {
          headers: { Authorization: `Bearer ${token}` },
        });
        fetchForgotPassword();
      }
      setSuccessMessage("Статус заявки змінено на 'Готово' успішно!");
    } catch (error) {
      console.error('Error marking as READY:', error);
    }
  };


  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleFileUpload = async (id) => {
    if (!file) {
      alert('Будь ласка, виберіть файл для завантаження.');
      return;
    }
    const formData = new FormData();
    formData.append('file', file);
    const token = localStorage.getItem('accessToken');
    try {
      // Припускаємо, що API для завантаження файлу однакове для обох типів
      await axios.post('http://localhost:8050/file/upload', formData, {
        params: { id },
        headers: {
          'Content-Type': 'multipart/form-data',
          Authorization: `Bearer ${token}`,
        },
      });
      setShowModal(true);
      if (selectedRequestType === 'statements') {
        fetchStatements();
      } else {
        fetchForgotPassword();
      }
    } catch (error) {
      alert('Виникла помилка при завантаженні файлу.');
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

  const handleSendMessage = async (id, userId) => {
    const message = messageMap[id];
    if (!message) {
      alert("Введіть текст повідомлення");
      return;
    }
    const token = localStorage.getItem('accessToken');
    try {
      await axios.post(`http://localhost:8095/api/notifications/ready`, null, {
        params: { userId, message },
        headers: { Authorization: `Bearer ${token}` },
      });
      setSuccessMessage("Повідомлення надіслано успішно!");
      setMessageMap(prevState => ({ ...prevState, [id]: '' }));
    } catch (error) {
      console.error("Error sending notification:", error);
    }
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


  // Фільтрація за ПІБ для заявок на довідки
  const filteredStatements = statements.filter(statement =>
    statement.fullName.toLowerCase().includes(searchQuery.toLowerCase())
  );
  // Фільтрація за ПІБ для заявок на забутий пароль
  const filteredForgotPasswords = forgotPasswords.filter(statement =>
    statement.typeOfForgotPassword === "Пароль до журналу"
  );

  // Перемикання типу заявок
  const handleRequestTypeChange = (type) => {
    setSelectedRequestType(type);
  };

  return (
    <Container className="students-container">
      <h2 className="my-4">Список заявок</h2>

      {/* Кнопки перемикання між типами заявок */}
      <Row className="mb-3">
        <Col>
          <Button
            variant={selectedRequestType === 'statements' ? 'primary' : 'secondary'}
            onClick={() => handleRequestTypeChange('statements')}
          >
            Заявки на довідки
          </Button>{' '}
          <Button
            variant={selectedRequestType === 'forgotPassword' ? 'primary' : 'secondary'}
            onClick={() => handleRequestTypeChange('forgotPassword')}
          >
            Заявки на забутий пароль
          </Button>
        </Col>
      </Row>

      {/* Контейнер для заявок на довідки */}
      {selectedRequestType === 'statements' && (
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
                <Table striped bordered hover className="wide-table">
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
      )}

      {/* Контейнер для заявок на забутий пароль */}
      {selectedRequestType === 'forgotPassword' && (
        <Container className="students-container">
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
                    {filteredForgotPasswords
                      .filter(forgotPassword =>
                        forgotPassword.fullName.toLowerCase().includes(searchQuery.toLowerCase())
                      )
                      .map((forgotPassword) => (
                        <tr key={forgotPassword.id}>
                          <td>{forgotPassword.fullName}</td>
                          <td>{forgotPassword.groupName}</td>
                          <td>{forgotPassword.faculty}</td>
                          <td>{forgotPassword.typeOfForgotPassword}</td>
                          <td>{forgotPassword.status}</td>
                          <td>
                            {forgotPassword.status === 'В очікуванні' && (
                              <Button variant="success" onClick={() => handleInProgress(forgotPassword.id)}>
                                В обробку
                              </Button>
                            )}

                            {forgotPassword.status === 'В процесі' && (
                              <Button variant="success" onClick={() => handleReady(forgotPassword.id)}>
                                Готово
                              </Button>
                            )}
                            {forgotPassword.status === 'Готово' && <span>Недоступно</span>}
                          </td>
                          <td>
                            {forgotPassword.status === 'В процесі' && (
                              <>
                                <Form.Control
                                  type="text"
                                  placeholder="Введіть логін"
                                  value={messageMap[forgotPassword.id]?.login || ''}
                                  onChange={(e) => handleMessageChange(e, forgotPassword.id, 'login')}
                                />
                                <Form.Control
                                  type="password"
                                  placeholder="Введіть пароль"
                                  value={messageMap[forgotPassword.id]?.password || ''}
                                  onChange={(e) => handleMessageChange(e, forgotPassword.id, 'password')}
                                />
                                <Button
                                  variant="primary"
                                  size="sm"
                                  className="mt-4"
                                  onClick={() =>
                                    handleSaveData(forgotPassword.id, messageMap[forgotPassword.id]?.login, messageMap[forgotPassword.id]?.password)
                                  }
                                >
                                  Надіслати
                                </Button>
                              </>
                            )}
                            {(forgotPassword.status === 'Готово' || forgotPassword.status === 'В очікуванні') && <span>Недоступно</span>}
                          </td>
                        </tr>
                      ))}
                  </tbody>
                </Table>
              )}
            </>
          )}
        </Container>
      )}

    </Container>
  );


};

export default ListStatementComponent;
