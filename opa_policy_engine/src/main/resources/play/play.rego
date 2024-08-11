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

