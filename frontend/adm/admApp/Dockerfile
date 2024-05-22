# Use an official Node.js runtime as the base image
FROM node:latest AS build

# Set the working directory in the container
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install Ionic CLI globally
RUN npm install -g @ionic/cli

# Install dependencies
RUN npm install

# Copy the remaining source code to the working directory
COPY . .

# Build the Ionic app
RUN ionic build

# Expose the port your app runs on
EXPOSE 8101

# Set the command to run your app
CMD ["ionic", "serve", "--host", "0.0.0.0"]
