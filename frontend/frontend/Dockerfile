# Етап збірки
FROM node:18 AS builder
WORKDIR /app

# Копіюємо package.json і package-lock.json, встановлюємо залежності
COPY package*.json ./
RUN npm install

# Копіюємо весь код і збираємо додаток
COPY . .
RUN npm run build

# Етап розгортання з Nginx
FROM nginx:alpine

# Копіюємо зібраний додаток у стандартну директорію Nginx
COPY --from=builder /app/dist /usr/share/nginx/html

# Копіюємо кастомний конфіг у Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Копіюємо SSL-сертифікат і приватний ключ (з папки ssl)
COPY ssl /etc/nginx/ssl

# Відкриваємо порти 80 та 443
EXPOSE 80 443

# Запускаємо Nginx
CMD ["nginx", "-g", "daemon off;"]

