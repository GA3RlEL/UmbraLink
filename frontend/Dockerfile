FROM node:latest AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

RUN npm install -g @angular/cli

COPY . .

RUN npm run build --configuration=production

FROM nginx:latest

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html


EXPOSE 80