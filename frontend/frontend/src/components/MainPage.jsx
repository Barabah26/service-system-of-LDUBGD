import React from "react";
import backgroundImage from "../../assets/hero-bg.jpg";
import { useNavigate } from 'react-router-dom';

const MainPage = () => {
    const navigate = useNavigate();

    return (
        <div
            style={{
                position: "relative",
                backgroundImage: `url(${backgroundImage})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
                minHeight: "100vh",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                color: "#fff",
                padding: "20px"
            }}
        >
            {/* Dark Overlay */}
            <div
                style={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    width: "100%",
                    height: "100%",
                    backgroundColor: "rgba(0, 0, 0, 0.5)",
                    zIndex: 1,
                }}
            ></div>

            {/* Page Content */}
            <div className="text-center container" style={{ position: "relative", zIndex: 2 }}>
                <h1 className="mb-5">
                    ЛЬВІВСЬКИЙ ДЕРЖАВНИЙ УНІВЕРСИТЕТ БЕЗПЕКИ ЖИТТЄДІЯЛЬНОСТІ
                </h1>
                <div className="d-flex flex-column align-items-center">
                    <button className="btn btn-outline-light mb-3" onClick={() => navigate("/student-login")}>
                        СЕРВІС ПОСЛУГ ДЕКАНАТУ ДЛЯ СТУДЕНТІВ
                    </button>
                    <button className="btn btn-outline-light mb-3" onClick={() => navigate("/admin-login")}>
                        СЕРВІС ПОСЛУГ ДЕКАНАТУ ДЛЯ АДМІНІСТРАТОРІВ
                    </button>
                </div>
            </div>
        </div>
    );
};

export default MainPage;
