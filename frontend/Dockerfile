# Command to build the Docker image separately
# docker build -t frontend .

# Command to run the Docker container separately
# docker run -p 80:80 frontend


# ---------- Build Stage ----------
FROM node:18-bullseye AS build

WORKDIR /app

# Copy dependencies first (enables Docker caching)
COPY package*.json ./
RUN npm install

# Copy rest of the frontend code
COPY . .

# Set DOCKER env var for Vite config to ensure correct paths
ENV DOCKER=True

# Run the production build
RUN npm run build

# ---------- Production Stage ----------
FROM nginx:stable-alpine

# Copy the built frontend files to Nginx's web root
COPY --from=build /app/dist /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
