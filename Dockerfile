# Dockerfile
# Use the official Python image as the base image
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install any dependencies if needed (not required for this simple example)

# Run the application when the container starts
CMD ["python", "main.py"]
