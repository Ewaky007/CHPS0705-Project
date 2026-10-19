# Kapture

## Lancer la base de données

1. Installer Docker Desktop
2. Cloner le repo
3. A la racine du projet :

docker-compose up -d

4. La base est accessible sur localhost:3306
   - user : root
   - password : kapture
   - database : kapture

## Lancer le backend Spring Boot

cd backend
./mvnw spring-boot:run