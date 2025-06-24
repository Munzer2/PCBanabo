// src/pages/Home.jsx
import React from 'react';
import { useNavigate } from 'react-router-dom';
import Particles from "react-tsparticles";
import { loadSlim } from "tsparticles-slim"; // Using slim version
import { FaCog, FaRocket } from 'react-icons/fa';

export default function Home() {
  const navigate = useNavigate();

  const particlesInit = async (main) => {
    await loadSlim(main); // Using slim loader
  };

  return (
    <div className="min-h-screen bg-[#0a0e17] relative overflow-hidden flex items-center justify-center px-6">
      <Particles
        className="absolute inset-0 z-0"
        init={particlesInit}
        options={{
          background: { color: { value: "#0a0e17" } },
          fpsLimit: 60,
          interactivity: {
            events: {
              onHover: { enable: true, mode: "repulse" },
              resize: true
            },
            modes: { repulse: { distance: 100, duration: 0.4 } }
          },
          particles: {
            color: { value: "#38b2ac" },
            links: {
              color: "#3182ce",
              distance: 150,
              enable: true,
              opacity: 0.3,
              width: 1
            },
            move: {
              enable: true,
              speed: 1,
              direction: "none",
              outModes: "bounce"
            },
            number: { value: 50 },
            opacity: { value: 0.3 },
            shape: { type: "circle" },
            size: { value: { min: 1, max: 3 } },
            reduceDuplicates: true,
            zIndex: { value: -1 }
          },
          detectRetina: true
        }}
      />

      <div className="relative z-10 text-center max-w-2xl">
        <div className="flex justify-center mb-6 animate-spin-slow">
          <FaCog className="text-5xl text-[#38b2ac] mr-2" />
          <FaRocket className="text-5xl text-[#3182ce]" />
        </div>

        <h1 className="text-5xl sm:text-6xl font-extrabold bg-gradient-to-r from-[#38b2ac] to-[#3182ce] text-transparent bg-clip-text animate-pulse drop-shadow-md mb-6">
          Welcome to PCBanabo
        </h1>

        <p className="text-gray-400 text-lg mb-10 px-2">
          Build your dream PC. Track component prices. Automate your research.
        </p>

        <div className="flex justify-center space-x-6">
          <button
            onClick={() => navigate('/login')}
            className="px-6 py-3 bg-gradient-to-r from-[#38b2ac] to-[#3182ce] text-white font-semibold rounded-lg shadow-lg transform transition-all duration-300 hover:scale-105 hover:shadow-xl"
          >
            Login
          </button>

          <button
            onClick={() => navigate('/signup')}
            className="px-6 py-3 border border-[#38b2ac] text-[#38b2ac] font-semibold rounded-lg shadow-lg transform transition-all duration-300 hover:bg-[#122027] hover:scale-105 hover:shadow-xl"
          >
            Sign Up
          </button>
        </div>

        <p className="mt-10 text-sm text-gray-600 italic">
          Stand against pedophilia and child exploitation. Join us in making the internet a safer place.
        </p>
      </div>
    </div>
  );
}
