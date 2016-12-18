function fib(x)
  if x <= 2
    return 1
  else
    return fib(x-1) + fib(x-2)
  end
end
i = 1
while true
  println("$i $(fib(i))")
  i += 1
end
