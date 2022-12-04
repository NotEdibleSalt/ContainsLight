# 指定基础镜像，这是多阶段构建的前期阶段
FROM openjdk:17 as builder
# 指定工作目录，目录不存在会自动创建
WORKDIR /app
# 将生成的 jar 复制到容器镜像中
COPY target/*.jar application.jar
# 通过工具spring-boot-jarmode-layertools从application.jar中提取拆分后的构建结果
RUN java -Djarmode=layertools -jar application.jar extract

# 正式构建镜像
FROM openjdk:17
# 指定工作目录，目录不存在会自动创建
WORKDIR /app
# 前一阶段从jar中提取除了多个文件，这里分别执行COPY命令复制到镜像空间中，每次COPY都是一个layer
COPY --from=builder app/dependencies ./
COPY --from=builder app/spring-boot-loader ./
COPY --from=builder app/snapshot-dependencies ./
COPY --from=builder app/application ./
# 指定时区
ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# 定义一些环境变量，方便环境变量传参
ENV JVM_OPTS=""
ENV JAVA_OPTS=""
# 指定暴露的端口，起到说明的作用，不指定也会暴露对应端口
EXPOSE 8080
# 启动 jar 的命令
ENTRYPOINT ["sh","-c","java $JVM_OPTS $JAVA_OPTS org.springframework.boot.loader.JarLauncher"]
