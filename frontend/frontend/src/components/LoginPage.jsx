import React, { useState, useRef } from 'react';
import { useNavigate, Link } from 'react-router-dom'; // Додайте імпорт Link
import { MDBContainer, MDBInput } from 'mdb-react-ui-kit';
import { login } from '../service/LoginService';
import './LoginPage.css';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

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

      console.log('User role from localStorage:', localStorage.getItem('userRole'));

      if (response.role === 'STUDENT') {
        navigate('/user-info');
      }
    } catch (error) {
      console.error('Login failed:', error.message);
      setError('Invalid login or password. If you don’t have an account, please register.');
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter') {
      if (event.target.id === 'login') {
        passwordRef.current.focus();
      } else if (event.target.id === 'password') {
        loginButtonRef.current.focus();
      } else {
        handleLogin();
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
            onKeyDown={handleKeyDown}
          />
          <MDBInput 
            wrapperClass='mb-4' 
            placeholder='Пароль' 
            id='password' 
            type='password' 
            value={password} 
            onChange={(e) => setPassword(e.target.value)} 
            onKeyDown={handleKeyDown} 
            ref={passwordRef}
          />
          {error && <p className="text-danger">{error}</p>}
          <button 
            className="btn-login mb-2 d-block btn-primary" 
            onClick={handleLogin} 
            ref={loginButtonRef}
          >
            Увійти в систему
          </button>

  
          <div className="text-center">
            <p>Ще не зареєстровані? <Link to="/register" className="btn-register btn-secondary">Зареєструватися</Link></p>
          </div>
        </MDBContainer>
      </div>
    </div>
  );
}

export default LoginPage;
