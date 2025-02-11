import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';  // Import useNavigate instead of useHistory
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
    // Check the type and navigate accordingly
    if (
      type === 'Довідка з місця навчання' ||
      type === 'Довідка для військкомату (Форма 20)' ||
      type === 'Довідка (Форма 9)'
    ) {
      navigate(`/statement-registration?type=${type}`);  // Navigate to statement registration
    } else if (
      type === 'Пароль до журналу' ||
      type === 'Пароль до віртуального університету'
    ) {
      navigate(`/forgot-password-registration?type=${type}`);  // Navigate to forgot password registration
    }
  };

  if (loading) {
    return <div>Завантаження...</div>;
  }

  if (error) {
    return <div>{error}</div>;
  }

  if (!student) {
    return <div>Студент не знайдений</div>;
  }

  const links = [
    { label: 'Замовити довідку з місця навчання', type: 'Довідка з місця навчання' },
    { label: 'Замовити довідку для військкомату(Форма 20)', type: 'Довідка для військкомату (Форма 20)' },
    { label: 'Довідка(Форма 9)', type: 'Довідка (Форма 9)' },
    { label: 'Пароль до журналу', type: 'Пароль до журналу' },
    { label: 'Пароль до віртуального університету', type: 'Пароль до віртуального університету' },
  ];

  return (
    <div className="container my-5">
      <div className="row mt-4">
        <div className="col-md-8">
          <div className="card">
            <div className="card-body">
              <h2 className="card-title">Загальна інформація</h2>
              <p><strong>Університет:</strong> Львівський державний університет безпеки життєдіяльності</p>
              <p><strong>Факультет:</strong> {student.faculty}</p>
              <p><strong>Спеціальність:</strong> {student.specialty}</p>
              <p><strong>Ступінь / Освітньо-професійний ступінь:</strong> {student.degree}</p>
              <p><strong>Група:</strong> {student.group}</p>
            </div>
          </div>
        </div>
        <div className="col-md-4">
          <div className="card">
            <div className="card-body">
              <h5 className="card-title">Корисні посилання</h5>
              <ul className="list-group list-group-flush">
                {links.map((link, index) => (
                  <li key={index} className="list-group-item">
                    <a href="#" onClick={() => handleNavigate(link.type)}>{link.label}</a>
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
