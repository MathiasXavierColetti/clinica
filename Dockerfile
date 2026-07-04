# --- Etapa 1: Build do Frontend (Angular) ---
FROM node:22 AS build-frontend
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ .
# O comando abaixo deve gerar a pasta em dist/clinica
RUN npm run build --prod

# --- Etapa 2: Build do Backend (Spring Boot) ---
FROM maven:3.9.9-eclipse-temurin-21 AS build-backend
WORKDIR /app/backend
COPY backend/pom.xml .
RUN mvn dependency:go-offline
COPY backend/src ./src

# Copia os arquivos do build do Angular para o Spring Boot (pasta static)
COPY --from=build-frontend /app/frontend/dist/frontend/browser /app/backend/src/main/resources/static
RUN mvn clean package -Dmaven.test.skip=true
# --- Etapa 3: Imagem Final (Runtime) ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copia o arquivo .jar gerado na etapa anterior
COPY --from=build-backend /app/backend/target/*.jar app.jar

# Expõe a porta que o Spring Boot utiliza
EXPOSE 8787

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]