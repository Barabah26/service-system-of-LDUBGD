import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import ListStatementComponent from './components/StatementRegistrationForm';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
import StatementRegistrationForm from './components/StatementRegistrationForm'; // Імпорт форми реєстрації
import StudentInfoPage from './components/UserInfoPage'; // Додано імпорт StudentInfoPage
import PrivateRoute from './components/PrivateRoute';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                <Route
                    path="/statements"
                    element={
                        <PrivateRoute>
                            <HeaderComponent />
                            <ListStatementComponent />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/user-info"
                    element={
                        <PrivateRoute>
                            <HeaderComponent />
                            <StudentInfoPage />
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/statement-registration"
                    element={
                        <PrivateRoute>
                            <HeaderComponent />
                            <StatementRegistrationForm /> {/* Форма реєстрації */}
                        </PrivateRoute>
                    }
                />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
