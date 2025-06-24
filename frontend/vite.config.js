import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import tailwindcss from '@tailwindcss/vite';

const isDocker = process.env.DOCKER === 'true';
const isDev = process.env.NODE_ENV !== 'production';

export default defineConfig({
  plugins: [
    react(),
    tailwindcss(),
  ],
  server: isDev && {
    host: true,
    port: 3000,
    proxy: {
      '/auth': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
      '/api/users': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
      '/api/components': isDocker ? 'http://backend:8080' : 'http://localhost:8080',
    }
  },
  build: {
    outDir: 'dist',
    emptyOutDir: true,
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
