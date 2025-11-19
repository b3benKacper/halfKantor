import React, { useState } from "react";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8089";

function ExchangePanel({ username }) {
  const [amount, setAmount] = useState("");
  const [from, setFrom] = useState("PLN");
  const [to, setTo] = useState("USD");
  const [result, setResult] = useState("");
  const [info, setInfo] = useState("");

  const handleCalculate = async () => {
    if (!amount || isNaN(amount) || Number(amount) <= 0) {
      setInfo("Podaj poprawną kwotę większą od zera!");
      return;
    }
    setInfo("Liczenie...");
    setResult("");
    try {
      const url = `${API_URL}/api/exchange/convert?from=${from}&to=${to}&amount=${amount}`;
      const res = await fetch(url);
      if (!res.ok) throw new Error("Błąd pobierania kursu");
      const data = await res.text();
      setResult(data);
      setInfo("");
    } catch (e) {
      setInfo("Błąd: " + e.message);
    }
  };

  const handleExchange = async () => {
    if (!amount || isNaN(amount) || Number(amount) <= 0) {
      setInfo("Podaj poprawną kwotę większą od zera!");
      return;
    }
    setInfo("Wysyłanie...");
    try {
      const url = `${API_URL}/api/transactions/exchange?username=${username}&from=${from}&to=${to}&amount=${amount}`;
      const res = await fetch(url, {
        method: "POST"
      });
      if (!res.ok) {
        const text = await res.text();
        throw new Error(text || "Błąd transakcji");
      }
      setInfo("Wymiana zakończona sukcesem!");
    } catch (e) {
      setInfo("Błąd: " + e.message);
    }
  };

return (
  <div className="container d-flex justify-content-center align-items-center" style={{ minHeight: "60vh" }}>
    <div className="card shadow p-4" style={{ width: 400, maxWidth: "100%" }}>
      <h5 className="card-title text-center text-primary mb-4">
        Panel Wymiany Walut
      </h5>
      <form>
        <div className="mb-3">
          <label className="form-label fw-semibold">Kwota</label>
          <input
            type="number"
            className="form-control form-control-lg border-primary"
            placeholder="Wpisz kwotę"
            value={amount}
            onChange={e => setAmount(e.target.value)}
            min="0"
            step="0.01"
          />
        </div>
        <div className="row g-2 mb-3">
          <div className="col">
            <label className="form-label">Z waluty</label>
            <select
              className="form-select form-select-lg border-success"
              value={from}
              onChange={e => setFrom(e.target.value)}
            >
              <option value="PLN">PLN</option>
              <option value="USD">USD</option>
            </select>
          </div>
          <div className="col">
            <label className="form-label">Na walutę</label>
            <select
              className="form-select form-select-lg border-primary"
              value={to}
              onChange={e => setTo(e.target.value)}
            >
              <option value="PLN">PLN</option>
              <option value="USD">USD</option>
            </select>
          </div>
        </div>
        <div className="d-flex justify-content-between mb-3">
          <button
            type="button"
            onClick={handleCalculate}
            className="btn btn-primary px-4 rounded-pill"
          >
            Przelicz
          </button>
          <button
            type="button"
            onClick={handleExchange}
            className="btn btn-success px-4 rounded-pill"
          >
            Wymień walutę
          </button>
        </div>
      </form>
      {result && (
        <div className="alert alert-info my-2 text-center rounded-pill">
          Otrzymasz: <strong>{result} {to}</strong>
        </div>
      )}
      {info && (
        <div className="text-success text-center fw-bold mt-2 rounded-pill" style={{ minHeight: 28 }}>
          {info}
        </div>
      )}
    </div>
  </div>
);
}

export default ExchangePanel;