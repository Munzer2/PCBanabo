import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite'

const isDocker = process.env.DOCKER === 'true';

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
  ],
  server: {
    host: true,
    port: 3000,
    proxy: {
      // proxy API calls to backend
      '/auth': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
      '/users': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
      '/components': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
    }
  },
  build: {
    outDir: '../src/main/resources/static',
    emptyOutDir: true,
    // use relative asset paths
    assetsDir: '.',
    rollupOptions: {
      output: {
        entryFileNames: `assets/[name].js`,
        chunkFileNames: `assets/[name].js`,
        assetFileNames: `assets/[name].[ext]`
      }
    }
  }
});
