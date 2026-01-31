# Сборка
FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
ARG API_URL=/api
RUN npm run build

# Веб-сервер
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
