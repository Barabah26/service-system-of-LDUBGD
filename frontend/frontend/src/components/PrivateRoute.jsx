import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

const PrivateRoute = ({ children, requiredRole }) => {
  const accessToken = localStorage.getItem('accessToken');
  const userRole = localStorage.getItem('userRole');
  const location = useLocation();

  const isAuthenticated = !!accessToken;

  // Якщо користувач аутентифікований і має роль ADMIN, і намагається зайти на /statements, перенаправляємо на логін
  if (isAuthenticated && userRole === 'ADMIN' && location.pathname === '/statements') {
    return <Navigate to="/" />; // Перенаправляємо на сторінку логіну
  }

  // Якщо користувач намагається зайти на /admin без ролі ADMIN, перенаправляємо на головну сторінку
  if (location.pathname === '/admin' && userRole !== 'ADMIN') {
    return <Navigate to="/" />;
  }

  // Перевіряємо, чи аутентифікований користувач і чи має необхідну роль
  const hasRequiredRole = !requiredRole || userRole === requiredRole;

  return isAuthenticated && hasRequiredRole ? children : <Navigate to="/" />;
};

export default PrivateRoute;
