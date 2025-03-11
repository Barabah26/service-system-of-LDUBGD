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
COPY nginx.conf /etc/nginx/nginx.conf

# Відкриваємо порт 80
EXPOSE 80

# Запускаємо Nginx
CMD ["nginx", "-g", "daemon off;"]
