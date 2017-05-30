FROM java:8
ENV VERSION=0.0.1-SNAPSHOT

RUN mkdir -p /app
WORKDIR  /app

COPY build/distributions/todo-$VERSION.zip .

RUN unzip todo-$VERSION.zip \
    && rm -Rf todo-$VERSION.zip \
    && mv todo-$VERSION todo

EXPOSE 3010
WORKDIR /app/todo
CMD /bin/bash -c "bin/todo"
