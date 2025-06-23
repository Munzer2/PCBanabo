# Command to build the Docker image separately
# docker build -t backend .

# Command to run the Docker container separately
# docker run -p 8080:8080 backend

# Use Debian-based JDK 24 image
FROM eclipse-temurin:24-jdk

# Set work directory
WORKDIR /app

# # Install Chrome & dependencies
# RUN apt-get update
# RUN apt-get install -y wget unzip curl gnupg ca-certificates chromium chromium-driver
# RUN ln -sf /usr/bin/chromium-browser /usr/bin/chromium
# RUN ln -sf /usr/bin/chromium-browser /usr/bin/google-chrome
# RUN chmod +x /usr/bin/chromedriver
# RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Install dependencies
# RUN apt-get update && apt-get install -y wget unzip curl gnupg ca-certificates fonts-liberation libasound2 libatk-bridge2.0-0 libatk1.0-0 libcups2 libdbus-1-3 \
#     libgdk-pixbuf2.0-0 libnspr4 libnss3 libx11-xcb1 libxcomposite1 libxdamage1 libxrandr2 xdg-utils libu2f-udev libvulkan1
RUN apt-get update && apt-get install -y wget unzip curl gnupg ca-certificates fonts-liberation jq

# Install Google Chrome
RUN wget -q https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb && \
    apt-get install -y ./google-chrome-stable_current_amd64.deb && \
    rm google-chrome-stable_current_amd64.deb

# Install Chrome driver
RUN CHROME_VERSION=$(google-chrome --version | awk '{print $3}') && \
    DRIVER_VERSION=$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json \
        | jq -r --arg ver "$CHROME_VERSION" '.channels.Stable.version') && \
    DRIVER_URL=$(wget -qO- https://googlechromelabs.github.io/chrome-for-testing/last-known-good-versions-with-downloads.json \
        | jq -r --arg ver "$DRIVER_VERSION" '.channels.Stable.downloads.chromedriver[] | select(.platform == "linux64") | .url') && \
    wget -q "$DRIVER_URL" -O chromedriver.zip && \
    unzip chromedriver.zip && \
    mv chromedriver-linux64/chromedriver /usr/bin/chromedriver && \
    chmod +x /usr/bin/chromedriver && \
    rm -rf chromedriver.zip chromedriver-linux64

# Symlinks for compatibility
RUN ln -sf /usr/bin/google-chrome /usr/bin/chromium && \
    ln -sf /usr/bin/google-chrome /usr/bin/chromium-browser

# Copy Maven wrapper and pom
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Install dependencies ahead-of-time for faster rebuilds
RUN ./mvnw dependency:go-offline

# Copy source code (can be ignored if mounted in docker-compose)
COPY src ./src

# Expose the development port
EXPOSE 8080

# Environment variables for Selenium
ENV CHROME_BINARY_PATH=/usr/bin/google-chrome
ENV CHROMEDRIVER_PATH=/usr/bin/chromedriver

# Run in dev mode with hot reload support
CMD ["./mvnw", "spring-boot:run"]


###################################
### Production Build Dockerfile ###
###################################


