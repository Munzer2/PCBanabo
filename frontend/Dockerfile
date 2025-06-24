# Command to build the Docker image separately
# docker build -t frontend .

# Command to run the Docker container separately
# docker run -p 3000:3000 frontend

FROM node:18-bullseye

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 3000

CMD ["npm", "run", "dev"]


###################################
### Production Build Dockerfile ###
###################################

# FROM node:18-alpine as build

# WORKDIR /app

# COPY package*.json ./
# RUN npm install

# COPY . .
# RUN npm run build

# FROM nginx:alpine
# COPY --from=build /app/dist /usr/share/nginx/html

# EXPOSE 80

# CMD ["nginx", "-g", "daemon off;"]

