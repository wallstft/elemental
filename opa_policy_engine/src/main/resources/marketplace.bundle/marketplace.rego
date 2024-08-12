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



