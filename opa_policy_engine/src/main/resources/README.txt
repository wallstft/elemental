

We must PUT the data to the opa server NOT POST
curl -X PUT http://localhost:8181/v1/data/play --data-binary @test.json

We need to PUT the policy to
curl -X PUT http://localhost:8181/v1/policies/play --data-binary @play.rego

We run the test using POST and pass the input json

curl -X POST http://localhost:8181/v1/data/play --data-binary @test.json

