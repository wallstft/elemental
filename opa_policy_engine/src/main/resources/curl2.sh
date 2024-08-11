#/bin/bash


curl http://localhost:8181/v1/data \
     -H "Content-Type: application/json" \
     --data-binary @post.json 
