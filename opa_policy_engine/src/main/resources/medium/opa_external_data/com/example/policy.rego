# package or namespace for 'policy.rego' module
# policy.rego file can be in any directory, not necessarily in the exact same directory as the package naming
# In this example, 'policy.rego' file is in 'com\example' directory
# It's not in 'com\example\marketing\rules' folder
package com.example.marketing.rules

# Optional, you only need this import in case you want an alias
import data.com.example as base

default allow = false

# 'local' documet 'users'
users := {
    "alice":   {"manager": "charlie", "title": "salesperson"},
    "bob":     {"manager": "charlie", "title": "salesperson"},
    "charlie": {"manager": "dave",    "title": "manager"},
    "dave":    {"manager": null,      "title": "ceo"}
}

# obtain data from data.json when evaluating policy direclty from json file
# we will provide an option for consistency at the end
myusers := data.users
# obtain data from data.json when evaluating policy againt bundle
myusers_base := base.datafolder.users

# obtain data from pushdata.json or through HTTP PUT
mytest := data.test

# OPA evaluates input.path and assign true or false to virtual document 'allow'
allow {
  input.path == ["fake1"]
}
