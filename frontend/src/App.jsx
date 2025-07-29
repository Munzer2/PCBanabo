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
import MotherBoard from './pages/lists/MotherBoard';
import Ram from './pages/lists/Ram';
import CpuCooler from './pages/lists/CpuCooler';
import Psu from './pages/lists/Psu';
import Ssd from './pages/lists/Ssd';
import Builds from './pages/Builds';
import MyBuilds from './pages/MyBuilds';
import Users from './pages/Users';

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
        path="/components/motherboard"
        element={token ? <MotherBoard /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/ram"
        element={token ? <Ram /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/cpu-cooler"
        element={token ? <CpuCooler /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/psu"
        element={token ? <Psu /> : <Navigate to="/login" replace />}
      />

      <Route 
        path="/components/ssd"
        element={token ? <Ssd /> : <Navigate to="/login" replace />}
      />

      <Route
        path="/configurator"
        element={token ? <Configurator /> : <Navigate to="/login" replace />}
      />

      <Route
        path="/builds"
        element={token ? <Builds /> : <Navigate to="/login" replace />}
      />

      <Route
        path="/builds/my"
        element={token ? <MyBuilds /> : <Navigate to= "/login"replace />}
      />

      <Route
        path="/users"
        element={token ? <Users /> : <Navigate to="/login" replace />}
      />

      <Route
        path="*"
        element={<Navigate to={token ? "/dashboard" : "/login"} replace />}
      />
    </Routes>
  );
}



export default App;
