# Сборка
FROM node:22-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
ARG API_URL=/api
RUN if [ -f "src/api/api-connector.jsx" ]; then \
      sed -i "s|http://localhost:8080|${API_URL}|g" src/api/api-connector.jsx; \
    fi
RUN npm run build

# Веб-сервер (опционально, если нужно отдавать статику напрямую)
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
