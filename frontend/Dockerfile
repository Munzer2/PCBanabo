# Command to build the Docker image separately
# docker build -t frontend .

# Command to run the Docker container separately
# docker run -p 80:80 frontend

# Build Stage
FROM node:18-bullseye AS build

WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Production Stage
FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]