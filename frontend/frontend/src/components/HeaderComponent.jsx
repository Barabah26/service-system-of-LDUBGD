import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar, Container, Nav, NavDropdown } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { FaUniversity } from 'react-icons/fa'; // Іконка університету
import { BsPersonCircle } from 'react-icons/bs'; // Іконка для аватара користувача

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

  useEffect(() => {
    // Отримання accessToken з localStorage
    const token = localStorage.getItem('accessToken');
    if (token) {
      const decodedToken = parseJwt(token); // Парсинг токена
      if (decodedToken && decodedToken.sub) { // Припускаємо, що "sub" містить ім'я користувача
        setUserName(decodedToken.sub); // Отримання імені користувача з токена
      }
    }
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    navigate('/');
  };

  return (
      <Navbar bg="dark" variant="dark" expand="lg" className="py-3 shadow-sm">
        <Container>
          <Navbar.Brand href="#" className="d-flex align-items-center">
            <FaUniversity className="me-2" size={30} /> {/* Іконка університету */}
            <span className="fw-bold text-uppercase">Університет безпеки життєдіяльності</span>
          </Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="ms-auto d-flex align-items-center">
              {userName && (
                  <span className="text-light me-3">{userName}</span> // Відображення імені користувача з токена
              )}
              <NavDropdown
                  title={<BsPersonCircle size={30} className="text-light" />} // Іконка аватара
                  id="basic-nav-dropdown"
                  align="end"
                  className="text-light"
              >
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={handleLogout}>Вихід</NavDropdown.Item>
              </NavDropdown>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
  );
};

export default HeaderComponent;
