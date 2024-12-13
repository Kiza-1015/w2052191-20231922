import React, { useEffect, useState } from "react";
import "./App.css";

const App = () => {
  const [formValues, setFormValues] = useState({
    maxCapacity: "",
    totalTickets: "",
    releaseRate: "",
    retrievalRate: "",
    quantity: "",
  });
  const [logs, setLogs] = useState(""); // Store log messages
  const [socket, setSocket] = useState(null);
  
  const handleChange = (e) => {
    setFormValues({ ...formValues, [e.target.name]: e.target.value });
  };

  const startWebsocket = () => {
    if (socket && (socket.readyState === WebSocket.OPEN || socket.readyState === WebSocket.CONNECTING)) return;
    
    // Establish WebSocket connection
    const ws = new WebSocket("ws://localhost:8070/ws");
  
    setSocket(ws);
    
    // Listen for messages from the WebSocket
    ws.onmessage = (event) => {
      setLogs((prevLogs) => prevLogs + "\n" + event.data);
    };
    
    // Handle connection errors
    ws.onerror = (error) => {
      console.error("WebSocket Error:", error);
      setLogs((prevLogs) => prevLogs + "\nWebSocket error occurred.");
    };
  }
  
  const handleStop = async () => {
    try {
      const response = await fetch("http://localhost:8070/api/system/stop", {
        method: "GET",
      });
      
      if (!response.ok) throw new Error("Failed to stop simulation");
      const result = await response.json();
      setLogs((prevLogs) => prevLogs + "\n" + result.message);
      setIsRunning(false);
    } catch (error) {
      setLogs((prevLogs) => prevLogs + "\nError: " + error.message);
    }
  };
  
  const handleSubmit = async (e) => {
    startWebsocket()


    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8070/api/tickets/processEvent", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          ticketPoolMaxCapacity: formValues.maxCapacity,
          totalTickets: formValues.totalTickets,
          releaseRate: formValues.releaseRate,
          retrievalRate: formValues.retrievalRate,
          quantity: formValues.quantity,
        }),
      });
      if (!response.ok) throw new Error("Failed to run simulation");
      const result = await response.json();
      setLogs((prevLogs) => prevLogs + "\n" + result.message); // Update logs
    } catch (error) {
      setLogs((prevLogs) => prevLogs + "\nError: " + error.message);
    }
  };
  
  return (
    <div className="app-container">
    <h1>Ticket Simulation</h1>
    <form onSubmit={handleSubmit} className="form-container">
    Maximum ticket capacity of the ticket pool
    <input
    type="number"
    name="maxCapacity"
    placeholder="Ticket Pool Max Capacity"
    value={formValues.maxCapacity}
    onChange={handleChange}
    />
    Total number of tickets a vendor can release
    <input
    type="number"
    name="totalTickets"
    placeholder="Total Tickets"
    value={formValues.totalTickets}
    onChange={handleChange}
    />
    How often vendors release tickets (Seconds)
    <input
    type="number"
    name="releaseRate"
    placeholder="Release Rate"
    value={formValues.releaseRate}
    onChange={handleChange}
    />
    How often customers buy tickets (Seconds)
    <input
    type="number"
    name="retrievalRate"
    placeholder="Retrieval Rate"
    value={formValues.retrievalRate}
    onChange={handleChange}
    />
    Number of tickets a customer can purchase
    <input
    type="number"
    name="quantity"
    placeholder="Quantity"
    value={formValues.quantity}
    onChange={handleChange}
    />
    <button type="button" onClick={handleStop}>Stop System</button>
    <button type="Submit" onClick={handleSubmit}>Run Simulation</button>
    </form>
    <div className="log-container">
    <textarea
    readOnly
    value={logs}
    placeholder="Logs will appear here..."
    ></textarea>
    </div>
    </div>
  );
};

export default App;
