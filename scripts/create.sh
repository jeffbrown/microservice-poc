#!/bin/bash

curl -i -X POST -H "Authorization: Bearer <put token here>" -H "Content-Type: application/json" -d '{"firstName":"Johnny","lastName":"Winter","age":70}' http://localhost:8080/people
curl -i -X POST -H "Authorization: Bearer <put token here>" -H "Content-Type: application/json" -d '{"firstName":"Jeff","lastName":"Beck","age":70}' http://localhost:8080/people
