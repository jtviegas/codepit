import sys
import comprehensions
import builtins

def assorted():

    print(sys.argv)

    print("""
Usage: thingy [OPTIONS]
     -h                        Display this usage message
     -H hostname               Hostname to connect to
    """)

    ln= 'Py' 'thon'
    print(ln)
    print(len(ln))

    a,b = 0,1
    while a < 10:
        print(a)
        a,b = b, a+b

    print("value is:", a)
    # sets
    basket = {'apple', 'orange', 'apple', 'pear', 'orange', 'banana'}
    print(basket)
    # dictionaries
    d = dict([('sape', 4139), ('guido', 4127), ('jack', 4098)])
    print(d)
    d = dict()
    print(d)
    d = {x: x ** 2 for x in (2, 4, 6)}
    print(d)
    d = dict(sape=4139, guido=4127, jack=4098)
    print(d)
    # looping
    basket = {'apple', 'orange', 'apple', 'pear', 'orange', 'banana'}
    for i,v in enumerate(basket):
        print(i,v)
    d = dict(sape=4139, guido=4127, jack=4098)
    for k,v in d.items():
        print(k, v)

    questions = ['name', 'quest', 'favorite color']
    answers = ['lancelot', 'the holy grail', 'blue']
    for q, a in zip(questions, answers):
        print('What is your {0}?  It is {1}.'.format(q, a))



assorted()
comprehensions.doit()
print(comprehensions.__name__)
# print('comprehensions qualname: {}'.format(comprehensions.__name__))
print(sys.path)
print(dir(comprehensions))
print(dir(builtins))




