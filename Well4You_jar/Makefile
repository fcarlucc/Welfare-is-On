all : up

clean : down

re : clean up

restart : stop start

up:
	docker-compose -f docker-compose.yml up --build

detach:
	docker-compose -f docker-compose.yml up -d --build

down:
	docker-compose -f docker-compose.yml down -v --rmi all

stop:
	docker-compose -f docker-compose.yml stop

start:
	docker-compose -f docker-compose.yml start

status:
	docker ps -a

image:
	docker image ls

volume:
	docker volume ls

logs:
	@echo "\nlogs of springboot\n--------------------------------------------\n"
	docker logs springboot
	@echo "\nlogs of postgres\n--------------------------------------------\n"
	docker logs postgres
	@echo "\nlogs of angular-nginx\n--------------------------------------------\n"
	docker logs angular-nginx

angular-nginx:
	docker exec -it angular-nginx /bin/bash

postgres:
	docker exec -it postgres /bin/bash

springboot:
	docker exec -it springboot /bin/bash

postgres_address:
	docker inspect postgres | grep IPAddress

purge:
	docker system prune -a --force --volumes

prune:
	docker container prune

ssl:
	@keytool -genkey -alias well4you -keyalg RSA -keystore well4you.jks -storepass password -validity 365 -keysize 2048 -dname "CN=Bravo Team, OU=42, O=42, L=Rome, ST=Italy, C=IT" -keypass password
