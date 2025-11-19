import React, { useState, useEffect } from "react";
import "../styles/AdminPanel.css";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8089";

function AdminPanel() {
  const [transactions, setTransactions] = useState([]);
  const [logs, setLogs] = useState([]);
  const [info, setInfo] = useState("");
  const [coursePLNUSD, setCoursePLNUSD] = useState("");
  const [courseUSDPLN, setCourseUSDPLN] = useState("");
  const [currentPLNUSD, setCurrentPLNUSD] = useState(null);
  const [currentUSDPLN, setCurrentUSDPLN] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setInfo("Ładowanie transakcji...");
      try {
        const transRes = await fetch(`${API_URL}/api/transactions/all`);
        if (!transRes.ok) throw new Error("Błąd pobierania transakcji");
        const transactionsData = await transRes.json();
        setTransactions(transactionsData);
        setInfo("");
      } catch (e) {
        setInfo("Błąd: " + e.message);
      }

      setInfo("Ładowanie logów...");
      try {
        const logsRes = await fetch(`${API_URL}/api/logs/all`);
        if (!logsRes.ok) throw new Error("Błąd logów");
        const logsData = await logsRes.json();
        setLogs(logsData);
        setInfo("");
      } catch (e) {
        setInfo("Błąd: " + e.message);
      }

      // pobiera aktualne kursy
      try {
        const ratePLNUSD = await fetch(`${API_URL}/api/exchange/rate?from=PLN&to=USD`);
        if (ratePLNUSD.ok) setCurrentPLNUSD(await ratePLNUSD.text());
        const rateUSDPLN = await fetch(`${API_URL}/api/exchange/rate?from=USD&to=PLN`);
        if (rateUSDPLN.ok) setCurrentUSDPLN(await rateUSDPLN.text());
      } catch (e) {
      }
    };
    fetchData();
  }, []);

  const updateRate = async (from, to, rate) => {
    setInfo("Aktualizacja kursu...");
    try {
      const res = await fetch(
        `${API_URL}/api/exchange/rate?from=${from}&to=${to}&rate=${rate}`,
        { method: "POST" }
      );
      if (!res.ok) throw new Error("Błąd aktualizacji kursu");
      setInfo("Kurs zaktualizowany!");

      // odśwież wyświetlane kursy
      if (from === "PLN" && to === "USD") setCurrentPLNUSD(rate);
      if (from === "USD" && to === "PLN") setCurrentUSDPLN(rate);

    } catch (e) {
      setInfo("Błąd: " + e.message);
    }
  };

    return (
    <div className="adminPanel">
      <h2>Panel Admina</h2>
      {info && <div style={{ color: "red" }}>{info}</div>}
      <h3>Zmień kursy wymiany</h3>
      <div style={{ marginBottom: 10 }}>
        <input
          type="number"
          placeholder="PLN → USD"
          value={coursePLNUSD}
          onChange={e => setCoursePLNUSD(e.target.value)}
        />
        <button onClick={() => updateRate("PLN", "USD", coursePLNUSD)}>
          Aktualizuj PLN→USD
        </button>
        <br />
        <span style={{ marginLeft: 12, color: "#333" }}>
          Aktualny kurs:&nbsp;
          <b>{currentPLNUSD !== null ? currentPLNUSD : "..."}</b>
        </span>
      </div>
      <div>
        <input
          type="number"
          placeholder="USD → PLN"
          value={courseUSDPLN}
          onChange={e => setCourseUSDPLN(e.target.value)}
        />
        <button onClick={() => updateRate("USD", "PLN", courseUSDPLN)}>
          Aktualizuj USD→PLN
        </button>
        <br />
        <span style={{ marginLeft: 12, color: "#333" }}>
          Aktualny kurs:&nbsp;
          <b>{currentUSDPLN !== null ? currentUSDPLN : "..."}</b>
        </span>
      </div>
<table style={{ width: '100%', borderCollapse: 'collapse', background: '#f7f7fb' }}>
  <thead>
    <tr>
      <th colSpan="4" style={{ background: '#e0e4f1', fontSize: '1.1em', padding: '12px', textAlign: 'center', border: '1px solid #ccc' }}>
        Tabela transakcji
      </th>
    </tr>
    <tr>
      <th style={{ border: '1px solid #ccc', padding: '8px' }}>Data</th>
      <th style={{ border: '1px solid #ccc', padding: '8px' }}>Z/Na Waluta</th>
      <th style={{ border: '1px solid #ccc', padding: '8px' }}>Kwoty i kurs</th>
      <th style={{ border: '1px solid #ccc', padding: '8px' }}>Użytkownik</th>
    </tr>
  </thead>
  <tbody>
    {transactions.map(tx => (
      <tr key={tx.id}>
        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
          {tx.timestamp && new Date(tx.timestamp).toLocaleString('pl-PL', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit'
          })}
        </td>
        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
          {tx.fromCurrency} → {tx.toCurrency}
        </td>
        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
          {tx.fromAmount} {tx.fromCurrency} → {tx.toAmount} {tx.toCurrency} (kurs: {tx.exchangeRate})
        </td>
        <td style={{ border: '1px solid #ccc', padding: '8px' }}>
          <b>{tx.user.username}</b> (<i>{tx.user.role}</i>)
        </td>
      </tr>
    ))}
  </tbody>
</table>
      <table style={{ width: '100%', borderCollapse: 'collapse', background: '#f7f7fb' }}>
        <thead>
          <tr>
            <th colSpan="4" style={{ background: '#e0e4f1', fontSize: '1.1em', padding: '12px', textAlign: 'center', border: '1px solid #ccc' }}>
              Logi systemowe
            </th>
          </tr>
          <tr>
            <th style={{ border: '1px solid #ccc', padding: '8px' }}>Data</th>
            <th style={{ border: '1px solid #ccc', padding: '8px' }}>Akcja</th>
            <th style={{ border: '1px solid #ccc', padding: '8px' }}>Opis</th>
            <th style={{ border: '1px solid #ccc', padding: '8px' }}>Użytkownik</th>
          </tr>
        </thead>
        <tbody>
          {logs.map(log => (
            <tr key={log.id}>
              <td style={{ border: '1px solid #ccc', padding: '8px' }}>{log.timestamp && new Date(log.timestamp).toLocaleString('pl-PL', {year: 'numeric',month: '2-digit',day: '2-digit',hour: '2-digit',minute: '2-digit'})}</td>
              <td style={{ border: '1px solid #ccc', padding: '8px' }}>{log.action}</td>
              <td style={{ border: '1px solid #ccc', padding: '8px' }}>{log.description}</td>
              <td style={{ border: '1px solid #ccc', padding: '8px' }}>
                {log.user ? `${log.user.username} (${log.user.role})` : ""}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AdminPanel;