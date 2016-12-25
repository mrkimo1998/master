function fib(x)
  if x <= 2
    return 1
  else
    result=(((1+sqrt(5))^x)-((1-sqrt(5))^x))/((2^x)*sqrt(5))
    return result
  end
end



i = 1
while i < 70
  println("$i $(fib(i))")
  i+=1
end
