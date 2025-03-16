FROM node:latest AS build

WORKDIR /app

COPY package*.json ./

RUN npm ci

RUN npm install -g @angular/cli
COPY . .

RUN ng build --configuration=production

FROM nginx:stable

COPY nginx.conf /etc/nginx/nginx.conf

COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]