#/bin/bash


curl http://localhost:8181/v1/data/app/abac \
     -H "Content-Type: application/json" \
     -d '{ "input" : { "user": "bob", "action": "read", "resource": "dog123" }}'
