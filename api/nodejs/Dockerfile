FROM ubuntu:latest

# Install NodeJS
RUN apt-get update && apt-get install -y curl
RUN curl -sL https://deb.nodesource.com/setup_6.x | bash -
RUN apt-get install -y python
RUN apt-get install -y nodejs
RUN apt-get install -y git

WORKDIR /app
COPY controllers /app/controllers
COPY index.js /app
COPY package.json /app
RUN npm install

EXPOSE 3000

CMD ["node", "./index.js"]