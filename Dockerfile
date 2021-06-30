FROM ascdc/jdk8
ADD ./build/libs/yoon-0.0.1.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT ["java","-jar","app.jar"]
