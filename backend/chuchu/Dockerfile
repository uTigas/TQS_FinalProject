FROM maven:3.8.5-openjdk-17

WORKDIR /backend
COPY . .
RUN mvn --fail-never clean install

CMD mvn spring-boot:run