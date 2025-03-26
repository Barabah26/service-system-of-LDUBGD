import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import API_ENDPOINTS from './apiConfig';

const StudentInfoPage = () => {
  const [student, setStudent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchStudentInfo = async () => {
      try {
        const token = localStorage.getItem('accessToken');
        const response = await axios.get(API_ENDPOINTS.AUTH.PROFILE, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setStudent(response.data);
      } catch (error) {
        setError('Не вдалося отримати інформацію про студента');
      } finally {
        setLoading(false);
      }
    };

    fetchStudentInfo();
  }, []);

  const handleNavigate = (type) => {
    if (
      type === 'Довідка з місця навчання' ||
      type === 'Довідка для військкомату (Форма 20)' ||
      type === 'Довідка (Форма 9)'
    ) {
      navigate(`/statement-registration?type=${type}`);
    } else if (
      type === 'Пароль до журналу' ||
      type === 'Пароль до віртуального університету'
    ) {
      navigate(`/forgot-password-registration?type=${type}`);
    }
  };

  if (loading) {
    return <div className="text-center py-5">Завантаження...</div>;
  }

  if (error) {
    return <div className="text-center py-5 text-danger">{error}</div>;
  }

  if (!student) {
    return <div className="text-center py-5">Студент не знайдений</div>;
  }

  const links = [
    { label: 'Замовити довідку з місця навчання', type: 'Довідка з місця навчання' },
    { label: 'Замовити довідку для військкомату (Форма 20)', type: 'Довідка для військкомату (Форма 20)' },
    { label: 'Довідка (Форма 9)', type: 'Довідка (Форма 9)' },
    { label: 'Пароль до журналу', type: 'Пароль до журналу' },
    { label: 'Пароль до віртуального університету', type: 'Пароль до віртуального університету' },
  ];

  return (
    <div className="container my-3 my-md-5">
      <div className="row">
        {/* Секція основної інформації */}
        <div className="col-12 col-md-8 mb-4 mb-md-0">
          <div className="card shadow-sm">
            <div className="card-body p-3 p-md-4">
              <h2 className="card-title h4 h3-md mb-3">Загальна інформація</h2>
              <p className="mb-2"><strong>Університет:</strong> Львівський державний університет безпеки життєдіяльності</p>
              <p className="mb-2"><strong>Факультет:</strong> {student.faculty}</p>
              <p className="mb-2"><strong>Спеціальність:</strong> {student.specialty}</p>
              <p className="mb-2"><strong>Ступінь:</strong> {student.degree}</p>
              <p className="mb-2"><strong>Група:</strong> {student.group}</p>
            </div>
          </div>

          {/* Секція інструкцій */}
          <div className="card shadow-sm mt-4">
            <div className="card-body p-3 p-md-4">
              <h4 className="card-title h5 h4-md mb-3">Інструкція для користувачів</h4>
              <h5 className="h6 mb-2">Як замовити довідки?</h5>
              <ul className="list-unstyled ms-3">
                <li className="mb-1">Через <strong>"Корисні посилання"</strong> – натисніть потрібний пункт.</li>
                <li className="mb-1">Через <strong>"Послуги"</strong> – оберіть довідку та заповніть заявку.</li>
              </ul>

              <h5 className="h6 mb-2">Як відновити пароль?</h5>
              <ul className="list-unstyled ms-3">
                <li className="mb-1">Через <strong>"Корисні посилання"</strong> – натисніть "Пароль до журналу" або "Пароль до віртуального університету".</li>
                <li className="mb-1">Через <strong>"Послуги"</strong> – оберіть відновлення пароля.</li>
              </ul>

              <h5 className="h6 mb-2">Як дізнатися про статус?</h5>
              <ul className="list-unstyled ms-3">
                <li className="mb-1"><strong>"Сповіщення"</strong> – повідомлення про готовність.</li>
                <li className="mb-1"><strong>"Мої довідки"</strong> – перегляд статусу заявок.</li>
                <li className="mb-1"><strong>Електронна пошта</strong> – перевіряйте пошту для отримання довідок.</li>
              </ul>
            </div>
          </div>
        </div>

        {/* Секція корисних посилань */}
        <div className="col-12 col-md-4">
          <div className="card shadow-sm">
            <div className="card-body p-3 p-md-4">
              <h5 className="card-title h6 h5-md mb-3">Корисні посилання</h5>
              <ul className="list-group list-group-flush">
                {links.map((link, index) => (
                  <li key={index} className="list-group-item p-2">
                    <a
                      href="#"
                      className="d-block text-decoration-none text-primary"
                      onClick={(e) => {
                        e.preventDefault();
                        handleNavigate(link.type);
                      }}
                      style={{ padding: '0.5rem 0' }} // Збільшена область натискання
                    >
                      {link.label}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default StudentInfoPage;