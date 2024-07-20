#!/bin/bash

mvn clean package
docker build -t mspsm:latest .