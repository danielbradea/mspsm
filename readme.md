# Post Service Management Microservice

## Overview

This microservice provides functionalities for managing posts, including creating, updating, listing, and deleting posts. It also supports handling image retrieval associated with the posts.

## Features
- Post Management:
    - List Posts: Retrieve a list of all posts using the /api/posts endpoint.
    - Get Post by ID: Retrieve details of a post by its unique ID with the /api/posts/{id} endpoint.
    - Create Post: Create a new post with the provided details and upload an image using the /api/posts endpoint (multipart/form-data).
    - Update Post: Update an existing post with new details and an image using the /api/posts/{id} endpoint (multipart/form-data). 
    - Delete Post: Delete a post by its ID using the /api/posts/{id} endpoint. 

- Image Retrieval 
    - Get Image by Name: Retrieve an image associated with a post by its name using the /api/posts/images/{imageName} endpoint.

## Requirements
- Java 17 or higher
- Maven


## Setup
1. Clone Repository
    ```sh
    git clone https://github.com/danielbradea/mspsm.git
    cd mspsm
    ```
2. Build the project
    ```sh
    mvn clean install
   ```
3. Run the app
    ```sh
   APP_URL=http://localhost:8080
   DS_URL=jdbc:h2:file:./testdb
   DS_DRIVER=org.h2.Driver
   DS_USERNAME=sa
   RMQ_HOST=localhost \
   RMQ_PORT=5672 \
   RMQ_USER=user \
   RMQ_PASSWORD=password \
   RMQ_VHOST=msemail \
   JWT_SECRET=d5501539-43e9-4e97-8256-4ab29a5bf539 \
   mvn spring-boot:run
   ```

## Accessing Swagger API Documentation
Once the application is running, you can access the Swagger UI to view and interact with the API documentation:
- Open a web browser and go to: `http://localhost:8080/mspsm/swagger-ui/index.html`