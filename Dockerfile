# Usa a imagem base com Maven e sistema preparado (já inclui JDK 23)
FROM workbridge-base:1.0

WORKDIR /app

# Copia JDK localmente e instala (se ainda não estiver instalado na imagem base)
COPY .jdk/jdk-23_linux-x64_bin.deb /tmp/jdk-23.deb
RUN dpkg -i /tmp/jdk-23.deb \
    && JDK_DIR=$(find /usr/lib/jvm -type d -name "jdk-23*" | head -n 1) \
    && mv "$JDK_DIR" /usr/lib/jvm/jdk-23 \
    && rm /tmp/jdk-23.deb

ENV JAVA_HOME=/usr/lib/jvm/jdk-23
ENV PATH="$JAVA_HOME/bin:$PATH"

# Copia o POM primeiro (para cache de dependências)
COPY pom.xml .

# Cache de dependências Maven
RUN --mount=type=cache,target=/root/.m2 mvn dependency:go-offline -B

# Copia o código-fonte completo e compila o projeto
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn clean package -DskipTests

# Expõe a porta da aplicação
EXPOSE 8080

# Copia e executa o .jar
RUN cp target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
