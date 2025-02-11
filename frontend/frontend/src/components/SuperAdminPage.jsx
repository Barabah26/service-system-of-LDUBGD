import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';

const SuperAdminPage = () => {
    const [admins, setAdmins] = useState([]);
    const [users, setUsers] = useState([]);
    const [newAdmin, setNewAdmin] = useState({
        name: '',
        login: '',
        password: '',
        role: '', 
    });
    const [editingAdmin, setEditingAdmin] = useState(null);
    const [editingUser, setEditingUser] = useState(null);
    const [newPassword, setNewPassword] = useState('');
    const [error, setError] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetchAdmins();
        fetchUsers();
    }, []);

    const fetchAdmins = async () => {
        try {
            const token = localStorage.getItem('accessToken');
            const response = await axios.get('http://localhost:8080/auth/admin/allAdmins', {
                headers: { Authorization: `Bearer ${token}` },
            });
            setAdmins(response.data); // Виправлено на setAdmins
        } catch (error) {
            if (error.response && error.response.status === 401) {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                navigate('/login');
            } else {
                setError('Failed to fetch users');
            }
        }
    };

    const fetchUsers = async () => {
        try {
            const token = localStorage.getItem('accessToken');
            const response = await axios.get('http://localhost:8080/auth/admin/allUsers', {
                headers: { Authorization: `Bearer ${token}` },
            });
            setUsers(response.data);
        } catch (error) {
            if (error.response && error.response.status === 401) {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                navigate('/login');
            } else {
                setError('Failed to fetch users');
            }
        }
    };
    

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewAdmin((prevState) => ({
            ...prevState,
            [name]: value,
        }));
    };

    const handleRegisterUser = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('accessToken');
            await axios.post('http://localhost:8080/auth/admin/register', newAdmin, { // Виправлено newUser на newAdmin
                headers: { Authorization: `Bearer ${token}` },
            });
            setSuccessMessage('Адміністратор зареєстрований успішно');
            setNewAdmin({ name: '', login: '', password: '', role: '' });
            fetchAdmins();
        } catch (error) {
            if (error.response && error.response.status === 401) {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                navigate('/admin-login');
            } else if (error.response && error.response.status === 409) {
                setError('Такий користувач вже існує');
            } else if (error.response && error.response.status === 404) {
                setError('Роль може бути: USER або ADMIN');
            } else {
                setError(error.response?.data || 'Не вдалося зареєструвати адміністратора');
            }
        }
    };

    const handleDeleteAdmin = (login) => {
        if (!login) return;

        const confirmAction = window.confirm('Ви впевнені що хочете видалити цього користувача?');
        if (confirmAction) {
            const token = localStorage.getItem('accessToken');
            axios
                .delete(`http://localhost:8080/auth/admin/deleteByLogin/${login}`, { // Виправлено синтаксис шаблонного рядка
                    headers: { Authorization: `Bearer ${token}` }
                })
                .then(() => {
                    setAdmins((prevAdmins) => prevAdmins.filter((admin) => admin.login !== login)); // Виправлено setUsers на setAdmins
                })
                .catch((error) => {
                    if (error.response && error.response.status === 401) {
                        localStorage.removeItem('accessToken');
                        localStorage.removeItem('refreshToken');
                        navigate('/admin-login');
                    } else {
                        setError('Failed to delete admin');
                    }
                });
        }
    };

    const handleDeleteUser = (login) => {
        if (!login) return;

        const confirmAction = window.confirm('Ви впевнені що хочете видалити цього користувача?');
        if (confirmAction) {
            const token = localStorage.getItem('accessToken');
            axios
                .delete(`http://localhost:8080/auth/admin/deleteUserByLogin/${login}`, {
                    headers: { Authorization: `Bearer ${token}` }
                })
                .then(() => {
                    setUsers((prevUser) => prevUser.filter((user) => user.login !== login));
                })
                .catch((error) => {
                    if (error.response && error.response.status === 401) {
                        localStorage.removeItem('accessToken');
                        localStorage.removeItem('refreshToken');
                        navigate('/admin-login');
                    } else {
                        setError('Failed to delete admin');
                    }
                });
        }
    };

    const handleEditPassword = (admin) => {
        setEditingAdmin(admin);
        setNewPassword('');
    };

    const handleSavePassword = async () => {
        try {
            const token = localStorage.getItem('accessToken');
            const updateAdminDto = { newPassword: newPassword };
            await axios.put(`http://localhost:8080/auth/admin/updateByLogin/${editingAdmin.login}`, updateAdminDto, { // Виправлено на editingAdmin
                headers: { Authorization: `Bearer ${token}` }
            });
            setSuccessMessage('Password updated successfully');
            setEditingAdmin(null);
            fetchAdmins();
        } catch (error) {
            if (error.response && error.response.status === 401) {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                navigate('/admin-login');
            } else {
                setError(error.response?.data || 'Failed to update password');
            }
        }
    };

    const handleEditPasswordUsers = (user) => {
        setEditingUser(user);
        setNewPassword('');
    };

    const handleSaveUserPassword = async () => {
        try {
            const token = localStorage.getItem('accessToken');
            const updateUserDto = { newPassword: newPassword };
            await axios.put(`http://localhost:8080/auth/admin/updateUserPasswordByLogin/${editingUser.login}`, updateUserDto, { // Виправлено на editingAdmin
                headers: { Authorization: `Bearer ${token}` }
            });
            setSuccessMessage('Password updated successfully');
            setEditingUser(null);
            fetchUsers();
        } catch (error) {
            if (error.response && error.response.status === 401) {
                localStorage.removeItem('accessToken');
                localStorage.removeItem('refreshToken');
                navigate('/admin-login');
            } else {
                setError(error.response?.data || 'Failed to update password');
            }
        }
    };




    return (
        <div className="container mt-4">
            {error && <div className="alert alert-danger">{error}</div>}
            {successMessage && <div className="alert alert-success">{successMessage}</div>}

            <div className="row mb-4">
                <div className="col-12">
                    <div className="card">
                        <div className="card-header">
                            <h2>Зареєструвати нового адміністратора</h2>
                        </div>
                        <div className="card-body">
                            <form onSubmit={handleRegisterUser}>
                                <div className="mb-3">
                                    <input
                                        type="text"
                                        name="name"
                                        className="form-control"
                                        placeholder="Ім'я"
                                        value={newAdmin.name} // Виправлено newUser на newAdmin
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <input
                                        type="text"
                                        name="login"
                                        className="form-control"
                                        placeholder="Логін"
                                        value={newAdmin.login} // Виправлено newUser на newAdmin
                                        onChange={handleInputChange}
                                        required
                                    />
                                </div>
                                <div className="mb-3">
                                    <input
                                        type="password"
                                        name="password"
                                        className="form-control"
                                        placeholder="Пароль"
                                        value={newAdmin.password} // Виправлено newUser на newAdmin
                                        onChange={handleInputChange}
                                        required
                                        autoComplete="new-password"
                                    />
                                </div>
                                <div className="mb-3">
                                    <select
                                        name="role"
                                        className="form-select"
                                        value={newAdmin.role} // Виправлено newUser на newAdmin
                                        onChange={handleInputChange}
                                        required
                                    >
                                        <option value="">Оберіть роль</option>
                                        <option value="SUPER_ADMIN">SUPER_ADMIN</option>
                                        <option value="ADMIN">ADMIN</option>
                                        <option value="TECH_ADMIN">TECH_ADMIN</option>
                                    </select>
                                </div>
                                <button type="submit" className="btn btn-primary">
                                    Зареєструвати адміністратора
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card-header">
                            <h2>Всі адміністратори</h2>
                        </div>
                        <div className="card-body">
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Ім'я користувача</th>
                                        <th>Логін</th>
                                        <th>Роль</th>
                                        <th>Дія</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {admins.map((admin) => ( // Виправлено users на admins
                                        <tr key={admin.login}>
                                            <td>{admin.name}</td>
                                            <td>{admin.login}</td>
                                            <td>{admin.role}</td>
                                            <td>
                                                <button className="btn btn-danger btn-sm" onClick={() => handleDeleteAdmin(admin.login)}>Видалити</button>
                                                <button className="btn btn-warning btn-sm ms-2" onClick={() => handleEditPassword(admin)}>Змінити пароль</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            {editingAdmin && ( // Виправлено editingUser на editingAdmin
                <div className="row mb-4">
                    <div className="col-12">
                        <div className="card">
                            <div className="card-header">
                                <h2>Редагувати пароль для {editingAdmin.name}</h2> {/* Виправлено на editingAdmin */}
                            </div>
                            <div className="card-body">
                                <form onSubmit={(e) => { e.preventDefault(); handleSavePassword(); }}>
                                    <div className="mb-3">
                                        <input
                                            type="password"
                                            className="form-control"
                                            placeholder="Новий пароль"
                                            value={newPassword}
                                            onChange={(e) => setNewPassword(e.target.value)}
                                            required
                                            autoComplete="new-password"
                                        />
                                    </div>
                                    <button type="submit" className="btn btn-success">Зберегти пароль</button>
                                    <button type="button" className="btn btn-secondary ms-2" onClick={() => setEditingAdmin(null)}>Скасувати</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            )}

            <div className="row">
                <div className="col-12">
                    <div className="card">
                        <div className="card-header">
                            <h2>Всі студенти</h2>
                        </div>
                        <div className="card-body">
                            <table className="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Ім'я користувача</th>
                                        <th>Логін</th>
                                        <th>Роль</th>
                                        <th>Дія</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {users.map((user) => ( 
                                        <tr key={user.login}>
                                            <td>{user.name}</td>
                                            <td>{user.login}</td>
                                            <td>{user.role}</td>
                                            <td>
                                                <button className="btn btn-danger btn-sm" onClick={() => handleDeleteUser(user.login)}>Видалити</button>
                                                <button className="btn btn-warning btn-sm ms-2" onClick={() => handleEditPasswordUsers(user)}>Змінити пароль</button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            {editingUser && ( 
                <div className="row mb-4">
                    <div className="col-12">
                        <div className="card">
                            <div className="card-header">
                                <h2>Редагувати пароль для {editingUser.name}</h2> 
                            </div>
                            <div className="card-body">
                                <form onSubmit={(e) => { e.preventDefault(); handleSaveUserPassword(); }}>
                                    <div className="mb-3">
                                        <input
                                            type="password"
                                            className="form-control"
                                            placeholder="Новий пароль"
                                            value={newPassword}
                                            onChange={(e) => setNewPassword(e.target.value)}
                                            required
                                            autoComplete="new-password"
                                        />
                                    </div>
                                    <button type="submit" className="btn btn-success">Зберегти пароль</button>
                                    <button type="button" className="btn btn-secondary ms-2" onClick={() => setEditingUser(null)}>Скасувати</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

            )}

        </div>
    );
};

export default SuperAdminPage;