# Стадия сборки
FROM node:22-alpine AS builder

ARG API_URL=/api

WORKDIR /app

# Копируем package.json
COPY package*.json ./

# Устанавливаем зависимости
RUN npm ci --only=production

# Копируем исходный код
COPY . .

# Настраиваем API URL (если нужно)
RUN if [ -f "src/api/api-connector.jsx" ]; then \
    sed -i "s|http://localhost:8080|${API_URL}|g" src/api/api-connector.jsx; \
    fi

# Собираем приложение
RUN npm run build

# Стадия продакшн
FROM nginx:1.24-alpine

# Копируем собранное приложение
COPY --from=builder /app/build /usr/share/nginx/html

# Копируем конфигурацию nginx
COPY --from=builder /app/nginx.conf /etc/nginx/conf.d/default.conf

# Открываем порт
EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
