import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import HeaderComponent from './components/HeaderComponent';
import ListStatementComponent from './components/ListStatementComponent';
import LoginPage from './components/LoginPage';
import RegisterPage from './components/RegisterPage';
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
                            <>
                                <HeaderComponent />
                                <ListStatementComponent />
                            </>
                        </PrivateRoute>
                    }
                />
                <Route
                    path="/user-info"
                    element={
                        <PrivateRoute>
                            <>
                                <HeaderComponent />
                                <StudentInfoPage /> {/* Використання компонента StudentInfoPage */}
                            </>
                        </PrivateRoute>
                    }
                />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
