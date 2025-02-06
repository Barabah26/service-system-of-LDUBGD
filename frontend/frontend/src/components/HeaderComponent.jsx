import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaUniversity } from 'react-icons/fa'; // Іконка університету
import { BsPersonCircle } from 'react-icons/bs'; // Іконка для аватара користувача
import { IoMdNotifications } from 'react-icons/io'; // Іконка для сповіщень
import { FaTrashAlt } from 'react-icons/fa'; // Іконка для видалення

// Функція для розбору токена
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

const HeaderComponent = () => {
  const navigate = useNavigate();
  const [userName, setUserName] = useState('');
  const [userId, setUserId] = useState(''); // Додаємо стан для зберігання userId
  const [notifications, setNotifications] = useState([]);
  const [processedNotifications, setProcessedNotifications] = useState(new Set()); // Масив оброблених ID

  useEffect(() => {
    // Отримання accessToken з localStorage
    const token = localStorage.getItem('accessToken');
    if (token) {
      const decodedToken = parseJwt(token); // Парсинг токена
      if (decodedToken && decodedToken.sub) {
        setUserName(decodedToken.sub); // Отримання імені користувача з токена
        setUserId(decodedToken.userId); // Отримання id користувача з токена
      }
    }
  }, []);

  useEffect(() => {
    if (userId) { // Перевіряємо, чи є userId перед відправкою запиту
      const interval = setInterval(() => {
        const fetchNotifications = async () => {
          try {
            const token = localStorage.getItem('accessToken');
            if (token && userId) {
              const response = await fetch(
                `http://localhost:8070/api/notifications?userId=${userId}`, // Використовуємо userId для запиту
                {
                  headers: {
                    Authorization: `Bearer ${token}`,
                  },
                }
              );

              if (response.ok) {
                const data = await response.json();
                console.log(data);

                // Фільтруємо нові сповіщення, щоб не надсилати одні й ті ж
                const newNotifications = data.filter(
                  (notification) => !processedNotifications.has(notification.id)
                );

                if (newNotifications.length > 0) {
                  setNotifications((prevNotifications) => [
                    ...prevNotifications,
                    ...newNotifications,
                  ]);
                  setProcessedNotifications((prevProcessed) => {
                    const updated = new Set(prevProcessed);
                    newNotifications.forEach(notification => updated.add(notification.id));
                    return updated;
                  });
                }
              } else {
                console.error('Помилка: статус не OK');
              }
            }
          } catch (error) {
            console.error('Помилка при отриманні сповіщень:', error);
          }
        };

        fetchNotifications();
      }, 100000); // Повторне виконання запиту кожну секунду

      return () => clearInterval(interval); // Очистити інтервал при розмонтуванні компонента
    }
  }, [userId, processedNotifications]); // Використовуємо userId в залежностях

  const handleLogout = () => {
    // Очищення даних при логауті
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    setNotifications([]); // Очищуємо сповіщення
    setProcessedNotifications(new Set()); // Очищаємо оброблені ID
    navigate('/'); // Перехід на головну сторінку
  };

  const handleSelectType = (type) => {
    if (
      type === 'Довідка з місця навчання' ||
      type === 'Довідка для військкомату (Форма 20)' ||
      type === 'Довідка (Форма 9)'
    ) {
      navigate(`/statement-registration?type=${type}`); // Перехід на сторінку для довідки
    } else if (
      type === 'Пароль до журналу' ||
      type === 'Пароль до віртуального університету'
    ) {
      navigate(`/forgot-password-registration?type=${type}`); // Перехід на сторінку для паролів
    }
  };


  const handleMyStatements = () => {
    navigate('/student-statements'); // Перехід на сторінку "Мої довідки"
  };

  const handleDeleteNotification = async (notificationId, index) => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      try {
        const response = await fetch(
          `http://localhost:8080/api/notifications/${notificationId}/read`,
          {
            method: 'POST',
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (response.ok) {
          // Видалення сповіщення з масиву за індексом
          const updatedNotifications = [...notifications];
          updatedNotifications.splice(index, 1);
          setNotifications(updatedNotifications);
        } else {
          console.error('Не вдалося позначити сповіщення як прочитане');
        }
      } catch (error) {
        console.error('Помилка при видаленні сповіщення:', error);
      }
    }
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="py-3 shadow-sm">
      <Container>
        <Navbar.Brand href="/user-info" className="d-flex align-items-center">
          <FaUniversity className="me-2" size={30} />
          <span className="fw-bold text-uppercase">Львівський державний університет безпеки життєдіяльності</span>
        </Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="ms-auto d-flex align-items-center">
            {/* Кнопка "Послуги" */}
            <NavDropdown
              title={<span className="custom-button text-light">Послуги</span>}
              id="services-dropdown"
              align="end"
              className="me-3"
            >
              <NavDropdown.Item onClick={() => handleSelectType('Довідка з місця навчання')}>
                Замовити довідку з місця навчання
              </NavDropdown.Item>
              <NavDropdown.Item onClick={() => handleSelectType('Довідка для військкомату (Форма 20)')}>
                Замовити довідку для військкомату (Форма 20)
              </NavDropdown.Item>
              <NavDropdown.Item onClick={() => handleSelectType('Довідка (Форма 9)')}>
                Довідка (Форма 9)
              </NavDropdown.Item>
              <NavDropdown.Item onClick={() => handleSelectType('Пароль до журналу')}>
                Пароль до журналу
              </NavDropdown.Item>
              <NavDropdown.Item onClick={() => handleSelectType('Пароль до віртуального університету')}>
                Пароль до віртуального університету
              </NavDropdown.Item>
            </NavDropdown>

            {/* Кнопка "Сповіщення" */}
            <NavDropdown
              title={
                <span className="custom-button text-light">
                  <IoMdNotifications size={20} className="me-2" />
                  Сповіщення {notifications.length > 0 && <span>({notifications.length})</span>}
                </span>
              }
              id="notifications-dropdown"
              align="end"
              className="me-3"
            >
              {notifications.length > 0 ? (
                notifications.map((notification, index) => (
                  <NavDropdown.Item key={index} className="d-flex justify-content-between">
                    <span>{notification.message}</span>
                    <FaTrashAlt
                      size={18}
                      className="text-danger"
                      style={{ cursor: 'pointer' }}
                      onClick={() => handleDeleteNotification(notification.id, index)}
                    />
                  </NavDropdown.Item>
                ))
              ) : (
                <NavDropdown.Item>Немає нових сповіщень</NavDropdown.Item>
              )}

            </NavDropdown>

            {/* Кнопка "Мої довідки" */}
            <Nav.Link href="#" className="custom-button text-light me-3" onClick={handleMyStatements}>
              Мої довідки
            </Nav.Link>

            {/* Кнопка профілю */}
            {userName && (
              <NavDropdown
                title={<BsPersonCircle size={30} className="text-light" />}
                id="basic-nav-dropdown"
                align="end"
                className="text-light"
              >
                <div className="d-flex align-items-center p-2">
                  <BsPersonCircle size={30} className="text-dark me-2" />
                  <span className="text-dark">{userName}</span>
                </div>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>Вихід</NavDropdown.Item>
              </NavDropdown>
            )}
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default HeaderComponent;
