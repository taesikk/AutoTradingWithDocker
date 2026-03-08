# 빌드 스테이지
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
# 프로덕션 빌드 시 config_prod.js를 config.js로 복사
RUN cp config_prod.js config.js
RUN npm run build

# 실행 스테이지 (nginx 사용)
FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]