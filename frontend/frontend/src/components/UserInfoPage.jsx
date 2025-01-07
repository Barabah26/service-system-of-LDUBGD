import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

const StudentInfoPage = () => {
  const student = {
    university: 'Львівський державний університет безпеки життєдіяльності',
    faculty: 'Факультет цивільного захисту',
    specialty: '122 Комп’ютерні науки',
    degree: 'бакалавр',
    group: 'КН43с',
  };

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
              <p><strong>Університет:</strong> {student.university}</p>
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
                    <a href="#">{link}</a>
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
