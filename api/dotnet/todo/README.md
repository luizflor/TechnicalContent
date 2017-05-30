# Docker commands
dotnet publish
docker rm dotnettodo

docker build -t luizflor/dotnettodo .

docker run -p 3020:3020 --name dotnettodo luizflor/dotnettodo 