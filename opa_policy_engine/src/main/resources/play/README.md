**Overview**\
This document describes how to start the opa server, upload the policy file, upload the data file and then run a query using input json.   The query will depend on values in the data file and logic defined in the policy file.

**Learning about OPA**\
https://www.openpolicyagent.org/docs/latest/

**Learning about the OPA Rest API**\
https://www.openpolicyagent.org/docs/latest/rest-api/


**Policy File**
```
package play
default hello = false

hello_msg := input.message
xxx := "test"
my_yyy := data.yyy

my_struct := data.struct

hello { 
  print("Evaluating policy with input: ", input)
  m := input.message
  m == "world"
}

test_data {
    f := data.foo
    f == "bar"
}

test_struct {
    b := my_struct.foo
    b == "bar"
}

my_bar := my_struct.foo

```
Note - the package name is referenced in the URL's below which is important as it provideds context.
The "data" variable is a keyword in Rego and is populated by http PUT commands to the /v1/data/"key" URL, where key is an element of data.
For example data.my_struct is populated by a PUT to /v1/data/my_struct.  

**Data File**
```json
> cat struct.json
{"foo":"bar"}

> curl -X PUT http://localhost:8181/v1/data/struct --data-binary @struct.json
```
The curl command will upload the contents of the struct file and assign it to data.struct variable which is referenced in the policy file above.

**Input File**
```json
> cat input.json
{ "input" : { "message": "world"}}

curl -X POST http://localhost:8181/v1/data/play --data-binary @input.json
```
Note - that the POST url has the package name "play" which directs the input.json to the policy file with the "package play" command.

**The Rego Playground**\
This website provides a great place to learn and test new Rego policy files with data and input json files. In the Examples dropdown menu there is an example called "Attributed base" which is an example of an ABAC Rego Policy\
https://play.openpolicyagent.org/p/crU5YehMej


**Loading Policy**

We need to PUT the policy to\
curl -X PUT http://localhost:8181/v1/policies/play --data-binary @play.rego

**Running Evaluation using Input**

We run the test using POST and pass the input json\
curl -X POST http://localhost:8181/v1/data/play --data-binary @input.json

**Example**

Loading Policy
```
> curl -X PUT http://localhost:8181/v1/policies/play --data-binary @play.rego
{}
```
Note - The @play.rego file contains the policy and once put to the server the server responds with an empty json object.

Evaluating Input against Policy and Data
```json
> curl -X POST http://localhost:8181/v1/data/play --data-binary @input.json
{"result":{"hello":true,"hello_msg":"world","my_bar":"bar","my_struct":{"foo":"bar"},"test_struct":true,"xxx":"test"}}}
```
Note - This POST command uploads the contents of the input.json and responds with the output of the policy file which uses the previously uploaded data.json file.
