import React, { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit';
import { login } from '../service/LoginServiceAdmin';
import './LoginPage.css';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  // Створення рефів для полів вводу і кнопки
  const passwordRef = useRef(null);
  const loginButtonRef = useRef(null);

  const handleLogin = async () => {
    try {
      if (!username || !password) {
        setError('Please enter both username and password.');
        return;
      }

      console.log('Sending login request...');
      const response = await login(username, password);
      console.log('Login successful:', response);

      // Store tokens and user role
      localStorage.setItem('accessToken', response.accessToken);
      localStorage.setItem('refreshToken', response.refreshToken);
      localStorage.setItem('userRole', response.role);

      // Debugging
      console.log('User role from localStorage:', localStorage.getItem('userRole'));

      // Redirect based on user role
      if (response.role === 'ADMIN') {
        console.log('Redirecting to /super-admin');
        navigate('/admin-services');
      } else if(response.role === 'SUPER_ADMIN'){
        navigate('/super-admin');
      } else if(response.role === 'TECH_ADMIN'){
        navigate('/tech-admin');
      }
    } catch (error) {
      console.error('Login failed:', error.message);
      setError('Invalid login or password.');
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      if (event.target.id === 'login') {
        // Якщо фокус на полі логіна, перемикаємо на поле пароля
        passwordRef.current.focus();
      } else if (event.target.id === 'password') {
        // Якщо фокус на полі пароля, перемикаємо на кнопку
        loginButtonRef.current.focus();
      } else {
        handleLogin(); // Виклик функції handleLogin при натисканні Enter на кнопці
      }
    }
  };

  return (
    <div className="login-container d-flex justify-content-center align-items-center vh-100">
      <div className="login-box border rounded-lg p-4">
        <MDBContainer className="p-3">
          <h2 className="mb-4 text-center">Авторизація користувача</h2>
          <MDBInput 
            wrapperClass='mb-4' 
            placeholder='Логін' 
            id='login' 
            value={username} 
            type='text' 
            onChange={(e) => setUsername(e.target.value)} 
            onKeyDown={handleKeyDown} // Додайте обробник події onKeyDown
          />
          <MDBInput 
            wrapperClass='mb-4' 
            placeholder='Пароль' 
            id='password' 
            type='password' 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            onKeyDown={handleKeyDown} // Додайте обробник події onKeyDown
            ref={passwordRef} // Додаємо реф до поля пароля
          />
          {error && <p className="text-danger">{error}</p>}
          <button 
            className="btn-login mb-4 d-block btn-primary" 
            onClick={handleLogin} 
            ref={loginButtonRef} // Додаємо реф до кнопки
          >
            Увійти в систему
          </button>
        </MDBContainer>
      </div>
    </div>
  );
}

export default LoginPage;
