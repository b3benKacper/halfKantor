import React, { useState } from "react";
import '../styles/LogRegForm.css';

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8089";

function LoginForm({ onLogin, onSwitch, show }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [info, setInfo] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setInfo("Loguję...");

    try {
      const response = await fetch(`${API_URL}/api/users/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Logowanie nie powiodło się");
      }

      const data = await response.text();
      setInfo("Zalogowano! " + data);
      setUsername("");
      setPassword("");
      if (onLogin) onLogin(username, data);
    } catch (err) {
      setInfo("Błąd: " + err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Logowanie</h2>
      <input
        type="text"
        placeholder="Login"
        value={username}
        onChange={e => setUsername(e.target.value)}
        required
        autoComplete="username"
      />
      <br />
      <input
        type="password"
        placeholder="Hasło"
        value={password}
        onChange={e => setPassword(e.target.value)}
        required
        autoComplete="current-password"
      />
      <br />
      <button className="btn_log_reg" type="submit">Zaloguj</button>
      <br />
      <button className="btn_change" type="button" onClick={onSwitch} style={{ marginTop: "10px" }}>
        Nie masz konta? Zarejestruj się
      </button>
      <br />
      <span>{info}</span>
    </form>
  );
}

export default LoginForm;