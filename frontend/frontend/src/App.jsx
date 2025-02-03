import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import HeaderComponent from "./components/HeaderComponent";
import HeaderComponentAdmin from "./components/HeaderComponentAdmin";
import ListStatementComponent from "./components/StatementRegistrationForm";
import LoginPage from "./components/LoginPage"; 
import RegisterPage from "./components/RegisterPage"; 
import StatementRegistrationForm from "./components/StatementRegistrationForm";
import StudentInfoPage from "./components/UserInfoPage";
import PrivateRoute from "./components/PrivateRoute";
import MainPage from "./components/MainPage"; 
import LoginPageAdmin from "./components/LoginPageAdmin";
import ListStatementsAdmin from './components/ListStatementsAdmin'; 
import StudentStatementsPage from './components/StudentStatementsPage';
import SuperAdminPage from './components/SuperAdminPage';
import ForgotPasswordRegistrationForm from "./components/ForgotPasswordRegistartionForm";



function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<MainPage />} />
                <Route path="/student-login" element={<LoginPage />} />
                <Route path="/register" element={<RegisterPage />} />
                
                <Route 
                    path="/student-statements" 
                    element={
                        <PrivateRoute>
                            <HeaderComponent />
                            <StudentStatementsPage />
                        </PrivateRoute>
                    } />

                
                {/* Private routes */}
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
                            <StatementRegistrationForm />
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/forgot-password-registration"
                    element={
                        <PrivateRoute>
                            <HeaderComponent />
                            <ForgotPasswordRegistrationForm />
                        </PrivateRoute>
                    }
                />

                <Route
                    path="/super-admin"
                    element={
                        <PrivateRoute requiredRole="SUPER_ADMIN">
                            <HeaderComponentAdmin />
                            <SuperAdminPage />
                        </PrivateRoute>
                    }
                />

                {/* Admin routes */}
                <Route path="/admin-login" element={<LoginPageAdmin />} />
                <Route 
                    path="/admin-services" 
                    element={
                        <PrivateRoute requiredRole="ADMIN">
                            <HeaderComponentAdmin />
                            <ListStatementsAdmin />
                        </PrivateRoute>
                    } />
                
            </Routes>
        </Router>
    );
}

export default App;
