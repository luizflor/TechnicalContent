# Docker commands
build gradle

docker build -f Dockerfile -t luizflor/javatodo .

docker rm javatodo

docker run -it --name javatodo luizflor/javatodo