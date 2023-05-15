FROM openjdk:20

ARG MAVEN_VERSION=3.6.3
ARG USER_HOME_DIR="/root"
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

RUN mkdir -p /usr/share/maven /usr/share/maven/ref \
 && output=`curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz` || echo $output \
 && output=`tar -xzf /tmp/apache-maven.tar.gz -C /usr/share/maven --strip-components=1` || echo $output\
 && rm -f /tmp/apache-maven.tar.gz \
 && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

WORKDIR /app

COPY . .

RUN output=`mvn clean install -DskipTests --no-transfer-progress 2>&1` || echo $output

RUN ls

CMD ["java", "-jar", "target/babbysFirstRancher-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080