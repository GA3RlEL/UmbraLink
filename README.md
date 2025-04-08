# Umbralink

Umbralink is a chat application built with Angular on the Front-End side and Spring Boot on the Back-End side.

## 📋 Project Status

### ✅ Fixed Bugs
- Wrong display of date on production
- Disconnecting from websocket after resizing to mobile view

### ✅ Implemented Features
- Restoring password
- Reconnecting to websocket
- Sending photos to chat

## 🛠️ Technologies

### Front-End
![HTML](https://img.shields.io/badge/-HTML-E34F26?style=flat-square&logo=html5&logoColor=white)
![CSS](https://img.shields.io/badge/-CSS-1572B6?style=flat-square&logo=css3&logoColor=white)
![TypeScript](https://img.shields.io/badge/-TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white)
![Angular](https://img.shields.io/badge/-Angular-DD0031?style=flat-square&logo=angular&logoColor=white)
![WebSocket](https://img.shields.io/badge/-WebSocket-010101?style=flat-square&logo=socket.io&logoColor=white)

### Back-End
![Spring Boot](https://img.shields.io/badge/-Spring%20Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Spring Web](https://img.shields.io/badge/-Spring%20Web-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/-Spring%20Security-6DB33F?style=flat-square&logo=spring-security&logoColor=white)
![JWT](https://img.shields.io/badge/-JWT-000000?style=flat-square&logo=json-web-tokens&logoColor=white)
![Docker](https://img.shields.io/badge/-Docker-2496ED?style=flat-square&logo=docker&logoColor=white)

## 🔧 Environment Variables

To run this project, you will need the following environment variables:

| Variable | Description |
|---------|------|
| `CLOUDINARY_API_KEY` | API key for Cloudinary service |
| `CLOUDINARY_CLOUD_NAME` | Cloudinary cloud name |
| `CLOUDINARY_SECRET_KEY` | Cloudinary secret key |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `DB_URL` | Database connection URL |
| `SECRET_KEY` | Secret key used for JWT encryption |
| `SMTP_EMAIL` | Email address for SMTP server |
| `SMTP_PASSWORD` | Password for SMTP server |
| `SMTP_FRONTEND_ENDPOINT` | Frontend URL for links in emails e.g http:localhost:4200 |

## 💻 Run Locally

### Front-End

1. Clone the project
```bash
git clone https://github.com/GA3RlEL/UmbraLink.git
```

2. Go to the front-end directory
```bash
cd UmbraLink/frontend
```

3. Install dependencies
```bash
npm install
```

4. Start the front-end server
```bash
ng serve
```
The application will be available at `http://localhost:4200`.

### Back-End

1. Return to the project root folder
```bash
cd ..
```

2. Go to the back-end directory
```bash
cd backend
```

3. Install dependencies
```bash
# Linux/macOS
./mvnw clean install

# Windows
.\mvnw clean install
```

4. Start the back-end server

**Linux/macOS:**
```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="\
-DCLOUDINARY_API_KEY=your_key \
-DCLOUDINARY_CLOUD_NAME=your_cloud_name \
-DCLOUDINARY_SECRET_KEY=your_secret \
-DDB_USERNAME=user \
-DDB_PASSWORD=pass \
-DDB_URL=jdbc:mysql://localhost:3306/umbralink \
-DSECRET_KEY=supersecretkey \
-DSMTP_PASSWORD=your_smtp_password \
-DSMTP_FRONTEND_ENDPOINT=your_frontend_url \
-DSMTP_EMAIL=your_smtp_email"
```

**Windows:**
```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-DCLOUDINARY_API_KEY=your_key -DCLOUDINARY_CLOUD_NAME=your_cloud_name -DCLOUDINARY_SECRET_KEY=your_secret -DDB_USERNAME=user -DDB_PASSWORD=pass -DDB_URL=jdbc:mysql://localhost:3306/umbralink -DSECRET_KEY=supersecretkey -DSMTP_PASSWORD=your_smtp_password -DSMTP_FRONTEND_ENDPOINT=your_frontend_url -DSMTP_EMAIL=your_smtp_email"
```

## 🚀 Demo

Try the application online at: [https://umbralink-frontend-1093767679330.europe-west1.run.app](https://umbralink-frontend-1093767679330.europe-west1.run.app)

> 💡 **Note:**  Because backend is hosted on GCloud it can take some time to warm up the backend
