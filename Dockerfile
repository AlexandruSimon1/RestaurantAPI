FROM openjdk:14
ARG PASSWORD=local
ARG DATABASE=testing
ENV PASSWORD ${PASSWORD}
ENV DATABASE ${DATABASE}
# Copy jar file
COPY target/*.jar  /opt/restaurant-spring-boot.jar
ADD wrapper.sh wrapper.sh
RUN bash -c 'chmod +x /wrapper.sh'
ENTRYPOINT ["/usr/bin/bash", "/wrapper.sh", "PASSWORD=${PASSWORD}", "DATABASE=${DATABASE}"]