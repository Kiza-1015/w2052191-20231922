import React, { useState } from 'react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

const TicketManagementApp = () => {
  // State for form inputs
  const [totalTickets, setTotalTickets] = useState('');
  const [maximumCapacity, setMaximumCapacity] = useState('');
  const [releaseRate, setReleaseRate] = useState('');
  const [retrievalRate, setRetrievalRate] = useState('');
  const [quantity, setQuantity] = useState('');

  // State for simulation control
  const [isRunning, setIsRunning] = useState(false);

  // State for console logs and results
  const [consoleLog, setConsoleLog] = useState([]);
  const [results, setResults] = useState(null);

  // Handle Start button click
  const handleStart = async () => {
    // Prevent starting if already running
    if (isRunning) return;

    // Clear previous logs and results
    setConsoleLog([]);
    setResults(null);
    setIsRunning(true);

    try {
      // Prepare the data to send to backend
      const payload = {
        totalTickets: parseInt(totalTickets),
        maximumCapacity: parseInt(maximumCapacity),
        releaseRate: parseFloat(releaseRate),
        retrievalRate: parseFloat(retrievalRate),
        quantity: parseInt(quantity)
      };

      // Send request to backend to start simulation
      const response = await fetch('/api/start-simulation', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload)
      });

      // Check if response is successful
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      // Parse response data
      const data = await response.json();

      // Update console log
      setConsoleLog(prevLog => [...prevLog, 'Simulation started...']);

    } catch (error) {
      // Handle any errors
      setConsoleLog(prevLog => [...prevLog, `Error: ${error.message}`]);
      setIsRunning(false);
    }
  };

  // Handle Stop button click
  const handleStop = async () => {
    // Prevent stopping if not running
    if (!isRunning) return;

    try {
      // Send request to backend to stop simulation
      const response = await fetch('/api/stop-simulation', {
        method: 'POST'
      });

      // Check if response is successful
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }

      // Parse response data
      const data = await response.json();

      // Update console log and results
      setConsoleLog(prevLog => [...prevLog, 'Simulation stopped.']);
      setResults(data.results);
      setIsRunning(false);

    } catch (error) {
      // Handle any errors
      setConsoleLog(prevLog => [...prevLog, `Error stopping simulation: ${error.message}`]);
    }
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Ticket Management System</h1>
      
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {/* Input Form */}
        <Card>
          <CardHeader>
            <CardTitle>Input Parameters</CardTitle>
          </CardHeader>
          <CardContent>
            <form className="space-y-4">
              <div>
                <label className="block mb-2">Total Tickets</label>
                <Input 
                  type="number" 
                  value={totalTickets}
                  onChange={(e) => setTotalTickets(e.target.value)}
                  placeholder="Enter total tickets"
                  disabled={isRunning}
                  required
                />
              </div>
              <div>
                <label className="block mb-2">Maximum Capacity</label>
                <Input 
                  type="number" 
                  value={maximumCapacity}
                  onChange={(e) => setMaximumCapacity(e.target.value)}
                  placeholder="Enter maximum capacity"
                  disabled={isRunning}
                  required
                />
              </div>
              <div>
                <label className="block mb-2">Release Rate</label>
                <Input 
                  type="number" 
                  step="0.01"
                  value={releaseRate}
                  onChange={(e) => setReleaseRate(e.target.value)}
                  placeholder="Enter release rate"
                  disabled={isRunning}
                  required
                />
              </div>
              <div>
                <label className="block mb-2">Retrieval Rate</label>
                <Input 
                  type="number" 
                  step="0.01"
                  value={retrievalRate}
                  onChange={(e) => setRetrievalRate(e.target.value)}
                  placeholder="Enter retrieval rate"
                  disabled={isRunning}
                  required
                />
              </div>
              <div>
                <label className="block mb-2">Quantity</label>
                <Input 
                  type="number" 
                  value={quantity}
                  onChange={(e) => setQuantity(e.target.value)}
                  placeholder="Enter quantity"
                  disabled={isRunning}
                  required
                />
              </div>
              <div className="flex space-x-4">
                <Button 
                  type="button"
                  onClick={handleStart}
                  disabled={isRunning || !totalTickets || !maximumCapacity || !releaseRate || !retrievalRate || !quantity}
                  className="w-full"
                >
                  Start
                </Button>
                <Button 
                  type="button"
                  onClick={handleStop}
                  disabled={!isRunning}
                  className="w-full"
                >
                  Stop
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>

        {/* Console and Results */}
        <Card>
          <CardHeader>
            <CardTitle>Console Output & Results</CardTitle>
          </CardHeader>
          <CardContent>
            {/* Console Log */}
            <div className="bg-black text-green-400 p-4 rounded-md mb-4 max-h-64 overflow-y-auto">
              {consoleLog.map((log, index) => (
                <div key={index}>{log}</div>
              ))}
            </div>

            {/* Results Display */}
            {results && (
              <Card className="mt-4">
                <CardHeader>
                  <CardTitle>Simulation Results</CardTitle>
                </CardHeader>
                <CardContent>
                  <pre>{JSON.stringify(results, null, 2)}</pre>
                </CardContent>
              </Card>
            )}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default TicketManagementApp;