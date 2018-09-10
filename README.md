# Spring Boot 2 - Oauth2 - Zuul - Eureka - Config Server

This project was written to learn microservice architecture with Java.

# Spring Boot Version

2.0.4.RELEASE

# Spring Cloud Version

Finchley.SR1

# Architecture

This project has Centralized Configuration Server. You can find in config-server.

Service Registration and Discovery handled by Eureka Server.

Endpoints are being accessed by api-gateway. Zuul is used to handle this.

Oauth2 is used for security. In-memory jwt approach is used.

# Ports

config-server : 8888

euraka-server : 8761

api-gateway   : 8765

uaa-server    : 9999

advertiser-service : 8801

# Contribution

Please add issues and/or send pull requests.
