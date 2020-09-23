FROM openjdk:11
#将容器中的/tmp目录作为持久化目录
VOLUME /tmp
#暴露端口
EXPOSE 8080
#复制文件
COPY ./target/docker-example-0*.jar app.jar
#配置容器启动后执行的命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]