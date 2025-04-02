
# Umbralink

Umbralink is an chat application built in Angular in Front-End side and Spring Boot in Back-End side.

## 🐞 Actuall bugs

- Wrong display of date on production ✅ fixed
- Disconnecting from websocket after resizing to mobile 🟡

## Features TBD
- Restoring password
- Reconnecting to websocket ✅
- Sending photos to chat ✅

## Technologies

HTML | CSS | TypeScript |  Angular | WebSocket  <br/> <br/>
Spring Boot | Spring Web | Spring Security | JWT | Docker

## Environment Variables

To run this project, you will need the following environment variables

`CLOUDINARY_API_KEY`

`CLOUDINARY_CLOUD_NAME`

`CLOUDINARY_SECRET_KEY`

`DB_PASSWORD`

`SECRET_KEY`

`DB_USERNAME`

`DB_URL`


## Run Locally

#### Run front-end

Clone the project

```bash
  git clone https://github.com/GA3RlEL/UmbraLink.git
```

Go to the project front-end directory

```bash
  cd frontend
```

Install dependencies

```bash
  npm install
```

Start the front-end server

```bash
  ng serve
```

#### Run back-end

Return to project root folder

```bash
cd ..
```

Go to the project back-end directory

```bash
  cd backend
```

Install dependencies
```
./mvnw clean install #linux/mac
.\mvnw clean install #windows
```

Start the back-end server

```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="\
-DCLOUDINARY_API_KEY=your_key \
-DCLOUDINARY_CLOUD_NAME=your_cloud_name \
-DCLOUDINARY_SECRET_KEY=your_secret \
-DDB_USERNAME=user \
-DDB_PASSWORD=pass \
-DDB_URL=jdbc:mysql://localhost:3306/umbralink \
-DSECRET_KEY=supersecretkey"
```

💡 Note: On Windows, use the following command instead
```bash
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-DCLOUDINARY_API_KEY=your_key -DCLOUDINARY_CLOUD_NAME=your_cloud_name -DCLOUDINARY_SECRET_KEY=your_secret -DDB_USERNAME=user -DDB_PASSWORD=pass -DDB_URL=jdbc:mysql://localhost:3306/umbralink -DSECRET_KEY=supersecretkey"
```


# Try it on your own
If you want to try this app go to this url https://umbralink-frontend-1093767679330.europe-west1.run.app
<br/>
💡 Because backend is hosted on GCloud it can take some time to warm up the backend




