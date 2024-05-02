network_name = filesystem-handler-network
db_container_name = postgres-container
db_name = filesystem-handler
db_user = filesystem-handler-user
db_password = 192837465

all: start
.PHONY: all

start: create-network start-postgres start-app-instance-1 start-app-instance-2

stop: shutdown-network
	sudo podman rm -f filesystem-handler-app-01 filesystem-handler-app-02

create-network:
	sudo podman network exists ${network_name} > /dev/null || sudo podman network create ${network_name}

shutdown-network:
	sudo podman network rm -f ${network_name}

start-postgres:
	sudo podman run -d --name ${db_container_name} \
		--network ${network_name} \
		-e POSTGRES_PASSWORD=${db_password} \
		-e POSTGRES_USER=${db_user} \
		-e POSTGRES_DB=${db_name} \
		-v postgres-data:/var/lib/postgresql/data \
		-p 5436:5432 \
		postgres

start-app-instance-1:
	sudo podman run -d --name filesystem-handler-app-01 \
        --network ${network_name} \
        -e DB_URL=jdbc:postgresql://${db_container_name}:5432/${db_name} \
        -e DB_USER=${db_user} \
        -e DB_PASSWORD=${db_password} \
        -p 8081:8080 \
        zsombok/filesystemhandler:latest

start-app-instance-2:
	sudo podman run --name filesystem-handler-app-02 \
        --network ${network_name} \
        -e DB_URL=jdbc:postgresql://${db_container_name}:5432/${db_name} \
        -e DB_USER=${db_user} \
        -e DB_PASSWORD=${db_password} \
        -p 8082:8080 \
        zsombok/filesystemhandler:latest