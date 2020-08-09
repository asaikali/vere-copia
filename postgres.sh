#!/usr/bin/env bash
if [[ "$#" -ne 1 ]]; then
   echo "Usage: $0 [start|stop]"
   exit 0
fi

ACTION=$1
if [[ "$ACTION" == "start" ]]; then
    echo "Starting docker postgresql..."
    docker-compose up -d
elif [[ "$ACTION" == "stop" ]]; then
    echo "Stopping docker postgresql..."
    docker-compose down --volumes
fi