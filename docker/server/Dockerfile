FROM ubuntu
MAINTAINER datpt7@vng.com.vn
RUN apt update && apt install -y git
RUN DEBIAN_FRONTEND="noninteractive" apt install -y openjdk-11-jdk
RUN apt install wget
RUN wget https://services.gradle.org/distributions/gradle-7.1.1-bin.zip -P /tmp
RUN apt install -y unzip vim
RUN unzip -d /opt/gradle /tmp/gradle-*.zip
ENV GRADLE_HOME /opt/gradle/gradle-7.1.1
ENV PATH $GRADLE_HOME/bin:$PATH
RUN git clone https://gitlab.com/datpt7/server.git
WORKDIR /server
