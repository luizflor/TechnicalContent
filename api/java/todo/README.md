# Docker commands
gradle build 

docker build -f Dockerfile -t luizflor/javatodo .

docker rm javatodo

docker run -it --name javatodo luizflor/javatodo


docker run -d -p 3010:3010 --name javatodo luizflor/javatodo 