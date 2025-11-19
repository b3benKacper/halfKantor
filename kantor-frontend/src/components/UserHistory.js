import React, { useState, useEffect } from "react";

const API_URL = process.env.REACT_APP_API_URL || "http://localhost:8089";

function UserHistory({ username }) {
  const [history, setHistory] = useState([]);
  const [info, setInfo] = useState("");

  useEffect(() => {
    if (!username) return;
    const fetchHistory = async () => {
      setInfo("Ładowanie...");
      try {
        const res = await fetch(`${API_URL}/api/transactions/user?username=${username}`);
        if (!res.ok) throw new Error("Błąd ładowania historii");
        const data = await res.json();
        setHistory(data);
        setInfo("");
      } catch (e) {
        setInfo("Błąd: " + e.message);
      }
    };
    fetchHistory();
  }, [username]);

  return (
    <div style={{ marginTop: 30, width: '100%' }}>
      {info && <div style={{ color: "red" }}>{info}</div>}
      <textarea
        style={{ width: "100%", minHeight: "200px", marginTop: 10, textAlign: "center" }}
        value={history.map(tx => 
          `${tx.fromAmount} ${tx.fromCurrency} → ${tx.toAmount} ${tx.toCurrency} (kurs: ${tx.exchangeRate}) [${tx.timestamp && new Date(tx.timestamp).toLocaleString('pl-PL', {year: 'numeric',month: '2-digit',day: '2-digit',hour: '2-digit',minute: '2-digit'})}]`
        ).join('\n')}
        readOnly
      />
    </div>
  );
}

export default UserHistory;