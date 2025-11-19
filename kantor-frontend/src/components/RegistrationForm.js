import React, { useState } from "react";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8089";

function RegistrationForm({ onRegister, onSwitch, show }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [info, setInfo] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    setInfo("Rejestruję...");

    try {
      const response = await fetch(`${API_URL}/api/users/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password })
      });
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || "Rejestracja nie powiodła się");
      }
      setInfo("Zarejestrowano! Możesz się zalogować.");
      setUsername("");
      setPassword("");
      if (onRegister) onRegister();
    } catch (err) {
      setInfo("Błąd: " + err.message);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <h2>Rejestracja</h2>
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
        autoComplete="new-password"
      />
      <br />
      <button className="btn_log_reg" type="submit">Zarejestruj</button>
      <br />
      <button className="btn_change" type="button" onClick={onSwitch} style={{ marginTop: "10px" }}>
        Masz już konto? Zaloguj się
      </button>
      <br />
      <span>{info}</span>
    </form>
  );
}

export default RegistrationForm;