import React from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import Signup from './pages/Signup';
import Configurator from './pages/Configurator';

function App() {
  useLocation();                         // this makes App re-render on route changes
  const token = localStorage.getItem('token');

  return (
    <Routes>
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
        path="/configurator"
        element={token ? <Configurator /> : <Navigate to="/login" replace />}
      />
      <Route
        path="*"
        element={<Navigate to={token ? '/dashboard' : '/login'} replace />}
      />
    </Routes>
  );
}



export default App;
