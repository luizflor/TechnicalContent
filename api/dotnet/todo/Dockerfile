FROM microsoft/aspnetcore:1.1.1
WORKDIR /app
EXPOSE 3020
ENV ASPNETCORE_URLS="http://*:3020"
COPY /bin/Debug/netcoreapp1.1/publish /app
ENTRYPOINT /bin/bash -c "dotnet todo.dll"