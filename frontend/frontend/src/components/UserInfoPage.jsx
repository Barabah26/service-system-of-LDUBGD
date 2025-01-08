import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const StudentInfoPage = () => {
  const [student, setStudent] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Отримуємо дані про студента після завантаження компонента
  useEffect(() => {
    const fetchStudentInfo = async () => {
      try {
        // Отримуємо токен з localStorage або з іншого джерела
        const token = localStorage.getItem('accessToken');
        
        const response = await axios.get('http://localhost:9000/api/auth/profile', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });

        setStudent(response.data); // Оновлюємо стан даних студента
      } catch (error) {
        setError('Не вдалося отримати інформацію про студента');
      } finally {
        setLoading(false);
      }
    };

    fetchStudentInfo();
  }, []);

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
    'Замовити довідку з місця навчання',
    'Замовити довідку для військкомату(Форма 20)',
    'Довідка(Форма 9)',
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
                    <a href="/statement-registration">{link}</a>
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
