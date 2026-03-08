FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN cp config/config_prod.js config/config.js
EXPOSE 3000
CMD ["node", "server.js"]