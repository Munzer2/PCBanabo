import React from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Home from './pages/Home';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Signup from './pages/Signup';
import Configurator from './pages/Configurator';

import Casing from './pages/lists/Casing';
import CPU from './pages/lists/CPU';
import GPU from './pages/lists/GPU';

function App() {
  useLocation();                         // this makes App re-render on route changes
  const token = localStorage.getItem('token');

  return (
    <Routes>
      <Route
        path="/" element={<Home />}
      />
      
      <Route
        path="/login"
        element={token ? <Navigate to="/dashboard" replace /> : <Login />}
      />

      <Route
        path="/signup"
        element={token ? <Navigate to="/dashboard" replace /> : <Signup />}
      />

      <Route
        path="/dashboard"
        element={token ? <Dashboard /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/casing"
        element={token ? <Casing /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/cpu"
        element={token ? <CPU /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/gpu"
        element={token ? <GPU /> : <Navigate to="/login" replace />}
      />

      <Route
        path="/configurator"
        element={token ? <Configurator /> : <Navigate to="/login" replace />}
      />
      <Route
        path="*"
        element={<Navigate to={token ? "/dashboard" : "/login"} replace />}
      />
    </Routes>
  );
}



export default App;
