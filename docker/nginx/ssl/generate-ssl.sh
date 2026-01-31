#!/bin/bash

# Создаем директорию если не существует
mkdir -p /etc/nginx/ssl

# Генерируем корневой CA
openssl genrsa -out /etc/nginx/ssl/ca.key 2048
openssl req -x509 -new -nodes -key /etc/nginx/ssl/ca.key \
  -sha256 -days 3650 -out /etc/nginx/ssl/ca.crt \
  -subj "/C=BY/ST=Minsk/L=Minsk/O=JavaApp/CN=JavaApp Root CA"

# Генерируем сертификат для домена
openssl genrsa -out /etc/nginx/ssl/java-app.local.key 2048

# Создаем CSR
openssl req -new -key /etc/nginx/ssl/java-app.local.key \
  -out /etc/nginx/ssl/java-app.local.csr \
  -subj "/C=BY/ST=Minsk/L=Minsk/O=JavaApp/CN=java-app.local"

# Создаем конфиг для SAN
cat > /etc/nginx/ssl/java-app.local.ext << EOF
authorityKeyIdentifier=keyid,issuer
basicConstraints=CA:FALSE
keyUsage = digitalSignature, nonRepudiation, keyEncipherment, dataEncipherment
subjectAltName = @alt_names

[alt_names]
DNS.1 = java-app.local
DNS.2 = localhost
IP.1 = 127.0.0.1
EOF

# Подписываем сертификат
openssl x509 -req -in /etc/nginx/ssl/java-app.local.csr \
  -CA /etc/nginx/ssl/ca.crt -CAkey /etc/nginx/ssl/ca.key -CAcreateserial \
  -out /etc/nginx/ssl/java-app.local.crt -days 3650 \
  -sha256 -extfile /etc/nginx/ssl/java-app.local.ext

# Устанавливаем правильные права
chmod 600 /etc/nginx/ssl/*.key
chmod 644 /etc/nginx/ssl/*.crt

echo "SSL сертификаты сгенерированы!"
echo "Добавьте /etc/nginx/ssl/ca.crt в доверенные корневые сертификаты вашей ОС"
