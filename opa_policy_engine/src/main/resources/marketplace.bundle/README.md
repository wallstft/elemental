**Overview**\
This document describes how to start the opa server, upload the policy file, upload the data file and then run a query using input json.   The query will depend on values in the data file and logic defined in the policy file.

**Learning about OPA**\
https://www.openpolicyagent.org/docs/latest/

**Learning about the OPA Rest API**\
https://www.openpolicyagent.org/docs/latest/rest-api/


**Policy File**
```
package marketplace_policy

import rego.v1

default allow := false

mplace := data.marketplace

#This will take the user and location from the input json and check the data json for this user in the specified location.
#This will produce a set of roles that are then used to resolve to a list of tables.
myroles = {
    R.roles[_]
    |
    R = mplace.users[_];
    R.name == input.user
    R.location == input.location
}

#take the list of roles that are found from searching the data json and filtering the roles based on user name and location
#produce a list of table objects, which includes all the elements of that table object.  Elements of the table object can be
#column-names, with masking rules, or no column-names which should be interpreted as allowing all columnes, while an
#empty columns array should be interpreted as not allowing access to any column.
table_list = [
    table[_]
    |
    R = myroles[_]
    table = mplace.roles[R]
]

#If the list of tables is greater then zero then the user is authorized to access the tables in the table_list.
allow if {
    count(table_list) > 0
}
```
Note - the package name is referenced in the URL's below which is important as it provideds context.
The "data" variable is a keyword in Rego and is populated by http PUT commands to the /v1/data/"key" URL, where key is an element of data.
For example data.marketplace_policy is populated by a PUT to /v1/data/marketplace_policy.

**Data File**
```json
> cat data.json
{
  "users": [
    {
      "name": "alice",
      "location": "nyc",
      "roles": [
        "data_analyst"
      ]
    },
    {
      "name": "bob",
      "location": "nyc",
      "roles": [
        "management"
      ]
    },
    {
      "name": "bob",
      "location": "apac",
      "roles": [
        "data_analyst",
        "sec_lending",
        "foobar"
      ]
    },
    {
      "name": "dan",
      "location": "nyc",
      "roles": [
        "sec_lending"
      ]
    }
  ],
  "roles": {
    "data_analyst": [
      {
        "role": "data_analyst",
        "table_name": "table1",
        "columns": [
          {
            "column_name": "col1",
            "erasure": "mask"
          },
          {
            "column_name": "col2",
            "erasure": "mask"
          }
        ]
      },
      {
        "role": "data_analyst",
        "table_name": "table3",
        "columns": [
          {
            "column_name": "col1",
            "erasure": "mask"
          },
          {
            "column_name": "col2",
            "erasure": "mask"
          }
        ]
      }
    ],
    "sec_lending": [
      {
        "role": "sec_lending",
        "table_name": "table2"
      }
    ],
    "management": [
      {
        "role": "management",
        "table_name": "table4"
      }
    ]
  }
}

> curl -X PUT http://localhost:8181/v1/data/marketplace --data-binary @data.json
```
The curl command will upload the contents of the data.json file and assign it to data.marketplace variable which is referenced in the policy file above.

**Input File**
```json
> cat input.json
{ "input" : { "message": "world"}}

curl -X POST http://localhost:8181/v1/data/marketplace_policy?pretty=true --data-binary @input.json
```
Note - that the POST url has the package name "marketplace" which directs the input.json to the policy file with the "package marketplace" command.

**The Rego Playground**\
This website provides a great place to learn and test new Rego policy files with data and input json files. In the Examples dropdown menu there is an example called "Attributed base" which is an example of an ABAC Rego Policy\
https://play.openpolicyagent.org/p/crU5YehMej


**Loading Policy**

We need to PUT the policy to\
curl -X PUT http://localhost:8181/v1/policies/marketplace_policy --data-binary @marketplace.rego

**Running Evaluation using Input**

We run the test using POST and pass the input json\
curl -X POST http://localhost:8181/v1/data/marketplace_policy?pretty=true --data-binary @input.json

**Example**

Loading Policy
```
> curl -X PUT http://localhost:8181/v1/policies/marketplace_policy --data-binary @marketplace.rego
{}
```
Note - The @play.rego file contains the policy and once put to the server the server responds with an empty json object.

Evaluating Input against Policy and Data
```json
> curl -X POST http://localhost:8181/v1/data/marketplace_policy?pretty=true --data-binary @input.json
{
    "result": {
        "allow": true,
        "mplace": {
        "roles": {
    "data_analyst": [
        {
        "columns": [
            {
            "column_name": "col1",
            "erasure": "mask"
            },
            {
            "column_name": "col2",
            "erasure": "mask"
            }
        ],
        "role": "data_analyst",
        "table_name": "table1"
        },
        {
        "columns": [
            {
            "column_name": "col1",
            "erasure": "mask"
            },
            {
            "column_name": "col2",
            "erasure": "mask"
            }
        ],
        "role": "data_analyst",
        "table_name": "table3"
        }
    ],
    "management": [
        {
        "role": "management",
        "table_name": "table4"
        }
    ],
    "sec_lending": [
        {
        "role": "sec_lending",
        "table_name": "table2"
        }
    ]
    },
    "users": [
        {
        "location": "nyc",
        "name": "alice",
        "roles": [
            "data_analyst"
        ]
        },
        {
            "location": "nyc",
            "name": "bob",
            "roles": [
                "management"
            ]
        },
        {
            "location": "apac",
            "name": "bob",
            "roles": [
                "data_analyst",
                "sec_lending",
                "foobar"
            ]
        },
        {
            "location": "nyc",
            "name": "dan",
            "roles": [
                "sec_lending"
            ]
        }
    ]
    },
    "myroles": [
        "data_analyst",
        "foobar",
        "sec_lending"
    ],
    "table_list": [
        {
            "columns": [
              {
                "column_name": "col1",
                "erasure": "mask"
                },
                {
                "column_name": "col2",
                "erasure": "mask"
                }
            ],
            "role": "data_analyst",
            "table_name": "table1"
        },
        {
            "columns": [
                {
                    "column_name": "col1",
                    "erasure": "mask"
                },
                {
                    "column_name": "col2",
                    "erasure": "mask"
                }
            ],
            "role": "data_analyst",
            "table_name": "table3"
        },
        {
            "role": "sec_lending",
            "table_name": "table2"
        }
    ]
    }
}
```
Note - This POST command uploads the contents of the input.json and responds with the output of the policy file which uses the previously uploaded data.json file.
