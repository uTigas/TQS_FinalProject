FROM maven:3.8.5-openjdk-17

RUN curl -fsSL https://download.docker.com/linux/static/stable/x86_64/docker-23.0.3.tgz -o docker.tgz && tar xzvf docker.tgz && mv docker/docker /usr/local/bin/docker

WORKDIR /backend
COPY . .
RUN mvn --fail-never clean compile

CMD mvn spring-boot:run