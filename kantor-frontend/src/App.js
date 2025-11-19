import React, { useState } from "react";
import RegistrationForm from "./components/RegistrationForm";
import LoginForm from "./components/LoginForm";
import ExchangePanel from "./components/ExchangePanel";
import UserHistory from "./components/UserHistory";
import AdminPanel from "./components/AdminPanel";
import "./styles/AuthPanel.css";
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [username, setUsername] = useState("");
  const [role, setRole] = useState("");
  const [showLogin, setShowLogin] = useState(true);
  const [activeTab, setActiveTab] = useState("exchange");

  const logout = () => {
    setLoggedIn(false);
    setUsername("");
    setRole("");
    setActiveTab("exchange");
  };

  return (
    <div className="auth-root">
      {!loggedIn && (
        <>
          <h1 style={{ marginTop: 70 }}>KANTOR</h1>
          <div className="dual-form-holder">
            <div className="auth-form-container">
              <LoginForm
                onLogin={(user, roleInfo) => {
                  setLoggedIn(true);
                  setUsername(user);
                  if (roleInfo && roleInfo.includes("ADMIN")) setRole("ADMIN");
                  else setRole("USER");
                }}
                onSwitch={() => setShowLogin(false)}
                show={showLogin}
              />
            </div>
            <div className="auth-form-container">
              <RegistrationForm
                onSwitch={() => setShowLogin(true)}
                show={!showLogin}
              />
            </div>
            <div
              className={
                "cover-slider" + (showLogin ? " slide-right" : " slide-left")
              }
            ></div>
          </div>
        </>
      )}
      {loggedIn && (
        <>
          <div className="top-menu">
            <button
              className={activeTab === "exchange" ? "menu-active" : ""}
              onClick={() => setActiveTab("exchange")}
            >
              Wymiana
            </button>
            <button
              className={activeTab === "history" ? "menu-active" : ""}
              onClick={() => setActiveTab("history")}
            >
              Historia
            </button>
            {role === "ADMIN" && (
              <button
                className={activeTab === "admin" ? "menu-active" : ""}
                onClick={() => setActiveTab("admin")}
              >
                Panel Admina
              </button>
            )}
            <span className="menu-user">
              {username} ({role}){" "}
              <button onClick={logout} style={{ marginLeft: 10 }}>
                Wyloguj
              </button>
            </span>
          </div>
          <div style={{ marginTop: 24, width: "100%", display: "flex", justifyContent: "center" }}>
            {activeTab === "exchange" && (
              <ExchangePanel username={username} />
            )}
            {activeTab === "history" && (
              <UserHistory username={username} />
            )}
            {activeTab === "admin" && role === "ADMIN" && <AdminPanel />}
          </div>
        </>
      )}
    </div>
  );
}

export default App;