import React from "react";
import { Card, Button } from "react-bootstrap";
import { FaUser, FaClipboardList, FaKey, FaBell, FaExternalLinkAlt } from "react-icons/fa";

const Instructions = () => {
    return (
        <div className="container mt-4">
            <Card className="shadow-lg p-4 border-0">
                <Card.Body>
                    <h2 className="text-center mb-4">📌 Інструкція для користувачів</h2>

                    {/* Авторизація та реєстрація */}
                    <h5 className="mb-3"><FaUser className="me-2 text-primary" />Перші кроки: Авторизація або реєстрація</h5>
                    <ul className="list-unstyled">
                        <li>🔹 Якщо у вас вже є акаунт – <strong className="text-success">увійдіть</strong> у систему.</li>
                        <li>🔹 Якщо ви ще не зареєстровані – <strong className="text-danger">створіть акаунт</strong> через форму реєстрації.</li>
                        <li>✅ Після успішного входу ви можете користуватися всіма функціями системи.</li>
                    </ul>

                    {/* Як замовити довідки */}
                    <h5 className="mt-4"><FaClipboardList className="me-2 text-primary" />Як замовити довідки?</h5>
                    <ul className="list-unstyled">
                        <li>📌 Через <strong className="text-primary">"Корисні посилання"</strong> – натисніть потрібний пункт.</li>
                        <li>📌 Через <strong className="text-success">"Послуги"</strong> – оберіть довідку та заповніть заявку.</li>
                    </ul>

                    {/* Як відновити пароль */}
                    <h5 className="mt-4"><FaKey className="me-2 text-danger" />Як відновити пароль?</h5>
                    <ul className="list-unstyled">
                        <li>🔑 Через <strong className="text-primary">"Корисні посилання"</strong> – натисніть на "Пароль до журналу" або "Пароль до віртуального університету".</li>
                        <li>🔑 Через <strong className="text-success">"Послуги"</strong> – оберіть відновлення пароля.</li>
                    </ul>

                    {/* Як дізнатися про статус */}
                    <h5 className="mt-4"><FaBell className="me-2 text-warning" />Як дізнатися про статус?</h5>
                    <ul className="list-unstyled">
                        <li>🔔 <strong className="text-warning">"Сповіщення"</strong> – отримання повідомлень про готовність довідок та заявок.</li>
                        <li>📄 <strong className="text-info">"Мої довідки"</strong> – перегляд статусу заявок.</li>
                        <li>📧 <strong className="text-danger">Електронна пошта</strong> – перевірка пошти для отримання довідок та можливість завантаження файлів.</li>
                    </ul>

                    {/* Посилання на систему */}
                    <div className="text-center mt-4">
                        <Button
                            href="https://dovidka.ldubgd.edu.ua/"
                            target="_blank"
                            variant="primary"
                            className="fw-bold d-flex align-items-center gap-2"
                            style={{ fontSize: "1.1rem", padding: "10px 20px" }}
                        >
                            Перейти до системи <FaExternalLinkAlt />
                        </Button>
                    </div>
                </Card.Body>
            </Card>
        </div>
    );
};

export default Instructions;
