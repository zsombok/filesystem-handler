.PHONY: install-docker install-podman install-podman-compose start stop check-deps check-docker check-podman check-podman-compose

all: start

check-deps: check-docker check-podman check-podman-compose

check-docker:
	@echo "Checking for Docker..."
	@which docker > /dev/null 2>&1 || (echo "Docker not installed, attempting to install..." && make install-docker)

check-podman:
	@echo "Checking for Podman..."
	@which podman > /dev/null 2>&1 || (echo "Podman not installed, attempting to install..." && make install-podman)

check-podman-compose:
	@echo "Checking for Podman Compose..."
	@which podman-compose > /dev/null 2>&1 || (echo "Podman Compose not installed, attempting to install..." && make install-podman-compose)

install-docker:
	@echo "Installing Docker..."
	@sudo apt-get update && sudo apt-get install -y docker.io
	@sudo systemctl start docker
	@sudo systemctl enable docker
	@echo "Docker installed successfully."

install-podman:
	@echo "Installing Podman..."
	@sudo apt-get update && sudo apt-get install -y podman
	@echo "Podman installed successfully."

install-podman-compose:
	@echo "Installing Podman Compose..."
	@sudo apt-get update && sudo apt-get install -y podman-compose
	@echo "Podman Compose installed successfully."

start: check-deps
	@echo "Starting Filesystem handler..."
	@sudo podman-compose up -d

stop:
	@echo "Stopping Filesystem handler..."
	@sudo podman-compose down
