#!/bin/bash

echo "Send requests every $1 seconds"
skus=( 101 102 103 104 200 201 202 300 301 302 303 400 401 500 501 502 )
while [ true ]
do
  s_id=$(( RANDOM % ${#skus[@]} ))
  sku=${skus[s_id]}
	quantity=$((1 + RANDOM % 10))
	curl_cmd="curl -X GET 'http://localhost:8080/api/stores/stock?sku=${sku}&quantity=${quantity}'"
	echo "$curl_cmd"
	eval "$curl_cmd"
	sleep "$1"
	printf "\n"
done
