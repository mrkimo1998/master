def main():
    i = 1
    while True:
        print(str(i)+ " " + str(fib(i)))
        i+=1

def fib(x):
    if x <= 2:
        return 1
    else:
        return fib(x-1) + fib(x-2)

if __name__ =='__main__':main()
