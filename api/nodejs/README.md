# Docker commands
docker rm nodejstodo

docker build -t luizflor/nodejstodo .

docker run -d -p 3000:3000 --name nodejstodo luizflor/nodejstodo  