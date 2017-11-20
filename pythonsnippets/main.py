#!/usr/bin/python3.4

import sys # sys.exit() quits the program

from pkg_resources import null_ns_handler


def header(name):
    print("\n----------")
    print(name)
    print("----------\n")

def operators():
    header("operators")
    print ("forcing integer division - 7 // 3 : ", 7 // 3)



def strings():
    header("strings")

    mls = '''
    just another
    multiline string
    and paragraph
    '''
    print ("multiline string: ", mls)

def string_functions():
    header("string_functions")

    s = "    apple     "
    c = "python is nice, R is also nice"

    print ("strip().upper():_", s.strip().upper(), "_")
    print ("concatenation s+c:", s + c)
    print("c.replace(\"nice\", \"great\"):", c.replace("nice", "great"))
    print("c.replace(\"nice\", \"great\", 1):", c.replace("nice", "great", 1))
    print("c.replace(\"nice\", \"great\").count(\"great\"):", c.replace("nice", "great").count("great"))

    val = 2.3
    n = 12
    item = "stock"
    print('"my {item}".format(item=item) =>', "my {item}".format(item=item))
    print('"my {:s} was at price {:06.3f} {:d} times last year".format(item, val, n) =>', "my {:s} was at price {:06.3f} {:d} times last year".format(item, val, n))

def user_input():
    header("user_input")
    first = str(input("first name:")).capitalize()
    middle = str(input("middle name:")).capitalize()
    last = str(input("last name:")).capitalize()
    print ("input was : ", "{first} {middle:.1s} {last}".format(first=first, middle=middle, last=last))
    print("double input")
    a, b = int(input("a?")), int(input("b?"))
    print("a, b = int(input(\"a?\")), int(input(\"b?\")) => {a} {b}".format(a=a, b=b))



def lists():
    header("lists")

    numbers = [-5 -3, 3 , 2, 7, 1 ]
    names = ["james", "john", "peter"]
    mixed = ["james", "john", 345 ]

    print(names[0])
    print(mixed[2])
    del names[1]
    print("after del names[1]", names)

    for el in mixed:
        print(el)

    print('"blabla"[1]', "blabla"[1])

    alpha = ["a", "b", "c"]
    alpha.append("d")
    alpha = alpha + ["e"]
    print(alpha)

    print(alpha.index("d"))
    alpha.remove("e")
    del alpha[alpha.index("d")]
    print(alpha)

    alpha1 = ["a", "f", "b", "e", "d"]
    alpha2 = ["g", "i", "h"]
    alpha3="jklmnopqrstuvwxyz"

    alpha1.sort()
    alpha2.sort()

    alpha1.insert(2, "c")
    print(alpha1)
    print(alpha2)

    alpha1 = "".join(alpha1)
    alpha2 = "".join(alpha2)
    alpha = alpha1 + alpha2 + alpha3

    print(alpha)

    numbers = [-5 - 3, 3, 2, 7, 1]
    print("numbers", numbers)
    print("max(numbers)", max(numbers))
    print("min(numbers)", min(numbers))
    print("sum(numbers)", sum(numbers))
    print("len(numbers)", len(numbers))

    alpha3 = "jklmnopqrstuvwxyz"
    print("alpha3", alpha3)
    print("max(alpha3)", max(alpha3))
    print("min(alpha3)", min(alpha3))
    print("len(alpha3)", len(alpha3))

    print("!!!tuples are simiars to lists, only difference is that they are immutable !!!")
    mytup = (1,2,3)
    print("mytup = (1,2,3) =>", mytup)


from pprint import pprint as pretty_print
from copy import copy, deepcopy

def arrays():
    header("arrays")

    nums_2d = [
        [1,2,3,4]
        , [7,8,9,10,101]
        , [16,17,18,19,20]
    ]

    print(nums_2d)
    pretty_print(nums_2d)

    nums_2d[0][1]=2.5
    pretty_print(nums_2d)

    print("arrays are references so we need deep copies of it if we want to replicate it somewhere else")
    letters = ["a", "b", "c", "d", "e"]
    letters_2d = [letters, letters, letters]
    print(letters_2d)
    letters_2d[0][0]="G"
    print(letters_2d)
    letters = ["a", "b", "c", "d", "e"]
    letters_2db = [copy(letters), copy(letters), copy(letters)]
    print(letters_2db)
    letters_2db[0][0] = "G"
    print(letters_2db)


def list_slicing():
    header("list_slicing")

    l = list(range(0,10))
    print("l", l)
    print("l[0:5]", l[0:5])
    print("l[2:len(l)]", l[2:len(l)])
    print("l[:]", l[:])
    print("l[0:6:2]", l[0:6:2])
    print("l[5:-2]", l[5:-2])
    print("l[::-1]", l[::-1])
    print("l[::-2]", l[::-2])


def conditionals():
    header("conditionals")

    wearegood = True

    _out="n"
    while _out == "n":
        age=int(input("age?"))
        if age==35 and wearegood:
            print("age is 35")
        elif age < 35 and not(not wearegood):
            print("age is lower than 35")
        else:
            print("age is higher than 35")

        _out=str(input("want to go out?(y/n):"))



def loops():
    header("loops")

    l = list(range(0, 10))
    for e in l:
        print(e)
    print(5 in l)
    for i in range(len(l)):
        print(l[i])
    print(5 in l)
    i=0
    while i<len(l):
        print(l[i])
        i+=1

    i = 0
    while True:
        print(i)
        i+=1
        if i == 6:
            break



def iterables():
    header("iterables")

    l = list(range(0, 10))
    t = (2,3,4,5,6)
    s = "okinawa karamagochi tuah"

    print("'__iter__' in dir(l) =>", '__iter__' in dir(l))
    print("'__iter__' in dir(t) =>", '__iter__' in dir(t))
    print("'__iter__' in dir(s) =>", '__iter__' in dir(s))

    l_iter = iter(l)
    while True:
        try:
            next_elem=next(l_iter)
            print(next_elem)
        except StopIteration:
            break



def primes():
    n = int(input("getting primes for number...? "))

    if n>=2:
        divisors = []
        for divisor in range(2,n):
            if n % divisor == 0:
                divisors.append(divisor)

        if 0 == len(divisors):
            print("{number} is prime!".format(number=n))
        else:
            print("{:d} is NOT prime! divisors: {:}".format(n, str(divisors)))
    else:
        print("{:d} is NOT prime!".format(n))



def multipleparamsFunc(*numbers):
    header("multipleparamsFunc")

    total=0
    for n in numbers:
        total += n

    return total
print("multipleparamsFunc(3,4,5,6) =>", multipleparamsFunc(3,4,5,6))

import datetime as dt
def func_with_default_param(message, time = dt.datetime.now()):
    header("func_with_default_param")
    return"{:}: {:}".format(time, message)
print("func_with_default_param(\"oi baby\") =>", func_with_default_param("oi baby"))

def placeholder_example():
    pass
    if True:
        pass
placeholder_example()

import math
def show_module_content(mod):
    print(math.pi)
    print("mod {mod} content => {content}".format( mod=mod.__name__, content=dir(mod)))
show_module_content(math)

print("running code only if we are the main code, and not if we are an imported module")
if __name__ == "__main__":
    print(" we are the main code here !")
    assert "a" + "b" == "ab", "something wrong here"
    #assert "a" + "b" == "a", "something wrong here"

def files():
    header("files")

    countries = []
    print("...reading countries file...")
    f = open("resources/countries.tsv", "r")
    for line in f:
        line = line.strip()
        countries.append(line.split("|")[1])
    f.close()
    print(countries)
    print("num of countries", len(countries))

    print("...saving another countries file...")
    f = open("resources/countries_b.tsv", "w")
    for country in countries:
        f.write(country + "\n")
    f.close()

    with open("resources/pi.txt") as fptr:
        cont = fptr.read().rstrip()
        print(cont)

    with open("resources/pi.txt") as fptr:
        lines = fptr.readlines()

    for l in lines:
        print(l.strip())



class Greeter(object):
    def __init__(self, name):
        self.name = name;

    def hello(self):
        print("hello I am {name}".format(name=self.name.title()))
    def goodbye(self):
        print("goodbye")


from collections import OrderedDict
def stdlib():
    header("stdlib")
    d = OrderedDict()
    d['a'] = 1
    d['b'] = 2
    d['c'] = 3

    for k,v in d.items():
        print(k, v)


class BankAccount:
    # class variable(static)
    accountID = 0

    def __init__(self):
        BankAccount.accountID += 1
        self.__id = BankAccount.accountID
        self.__balance = 0

    def deposit(self, value):
        self.__balance += value

    def withdraw(self, value):
        self.__balance -= value

    def getBalance(self):
        return self.__balance;

    def getID(self):
        return self.__id;

    '''
    __str__ method: displays the object’s state
    '''

    def __str__(self):
        pass




# copying an object
import copy

o3 = copy.deepcopy(o2)


def classes():
    header("classes")

    g = Greeter("joe")
    g.hello();
    g.goodbye();

    m = '''
    Inheritance in python 2.7:

    class ElectricCar(Car):
        def __init__(self, make, model, year):
            super(ElectricCar, self).__init__(make, model, year)
            ...

    Inheritance in python 3:

    class ElectricCar(Car):
        def __init__(self, make, model, year):
            super().__init__(make, model, year)
            ...

    '''

    print(m)

    o1 = BankAccount()
    o2 = BankAccount()
    o1.deposit(123)

    for o in [o1, o2]:
        o.deposit(23)
        o.withdraw(5)
        print('balance:', o.getBalance())
        print('accountID', o.getID())

def exceptions():
    header("exceptions")

    try:
        print(5/0)
    except ZeroDivisionError:
        print("can't divide by zero")
    else: #only if try block was executed successfully
        print("done")

    try:
        raise ValueError('errrr')
    except ValueError as e:
        print("vaue error:", e)
    else: #only if try block was executed successfully
        print("done")

    c = '''
    failing silently
    try:
        --snip--
    except FileNotFoundError:
        pass
    else:
        --snip--
    '''
    print(c)

import json

def storing_data():
    header("storing_data")

    numbers = [2,3,4,5,6,0]
    fname = "resources/numbers.json"
    with open(fname,"w") as f:
        json.dump(numbers, f)

    with open(fname) as f:
        numbers2 = json.load(f)

    print(numbers2)


import matplotlib.pyplot as plt

def matplotlib():
    header("matplotlib")

    vals=[1,2,3,4,5]
    squares = [v**2 for v in vals]
    plt.plot(vals, squares, c='red', linewidth=5)
    plt.title('square numbers')
    plt.xlabel("value", fontsize=14)
    plt.ylabel("square of value", fontsize=14)
    plt.tick_params(axis="both", labelsize=14)
    # Set the range for each axis.
    plt.axis([0, 10, 0, 100])
    plt.show()

    plt.scatter(vals, squares, edgecolor='none', s=100, c=squares, cmap=plt.cm.Blues)
    plt.title('square numbers')
    plt.xlabel("value", fontsize=14)
    plt.ylabel("square of value", fontsize=14)
    plt.tick_params(axis="both", labelsize=14)
    # Set the range for each axis.
    plt.axis([0, 10, 0, 40])
    plt.show()
    # instead of show we can save
    # plt.savefig('squares_plot.png', bbox_inches='tight')


# ------------------------------------------------------
from random import choice
class RandomWalk(object):
    """class to generate random walks"""
    def __init__(self, num_points=5000):
        self.n = num_points
        self.x = [0]
        self.y = [0]

    def walk(self):
        while len(self.x) < self.n:
            x_direction = choice([-1,1])
            x_distance = choice([0,1])
            x_step = x_direction * x_distance
            y_direction = choice([-1, 1])
            y_distance = choice([0, 1, 2, 3])
            y_step = y_direction * y_distance

            if x_step == 0 and y_step == 0:
                continue

            next_x = self.x[-1] + x_step
            next_y = self.y[-1] + y_step
            self.x.append(next_x)
            self.y.append(next_y)



def random_walk():
    header("random_walk")
    rw = RandomWalk()
    rw.walk()
    plt.scatter(rw.x, rw.y, s=15)
    plt.show()

#-----------------------------------------------------
from random import randint
import pygal

class Die(object):
    def __init__(self):
        self.n_sides = 6

    def roll(self):
        return randint(1, self.n_sides)


def pygal():
    header("pygal")

    die = Die()
    results=[]
    for n in range(100):
        results.append(die.roll())

    #analyse the results
    frequencies=[]
    for v in range(1, die.n_sides+1):
        frequencies.append(results.count(v))

    #histogram
    #hist = pygal.Bar()
    #hist.title = "rolling D6"
    #hist.x_labels = ['1', '2', '3', '4', '5', '6']
    #hist.x_title = "result"
    #hist.y_title = "frequency"
    #hist.add('D6', frequencies)
    #hist.render_to_file('die_visual.svg')

#--------------------------------------------------
import csv
import json
from datetime import datetime
def downloading_data():
    header("downloading_data")
    #filename="resources/chapter_16/sitka_weather_07-2014.csv"
    filename = "resources/chapter_16/death_valley_2014.csv"
    dates, hi, lo= [], [], []

    first_date = datetime.strptime('2014-7-1', '%Y-%m-%d')
    print(first_date)

    with open(filename) as f:
        reader = csv.reader(f)
        header_row = next(reader)
        print(header_row)
        print("enumerating the columns")
        for i,col in enumerate(header_row):
            print(i, col)

        for row in reader:
            try:
                current_date = datetime.strptime(row[0], "%Y-%m-%d")
                h = int(row[1])
                l = int(row[3])
            except ValueError:
                print(current_date, "missing data")
            else:
                dates.append(current_date)
                hi.append(h)
                lo.append(l)
        #print(hi)

    fig = plt.figure(dpi=128, figsize=(10,6))
    plt.plot(dates, hi, c='red')
    plt.plot(dates, lo, c='blue')
    plt.fill_between(dates, hi, lo, facecolor='blue', alpha=0.1)
    plt.title("Daily high and low temperatures, July 2014", fontsize=24)
    plt.xlabel('', fontsize=16)
    # eventually print labels diagonally
    fig.autofmt_xdate()
    plt.ylabel("Temperature (F)", fontsize=16)
    plt.tick_params(axis='both', which='major', labelsize=16)
    plt.show()

    filename = "resources/chapter_16/population_data.json"
    with open(filename) as f:
        pop_data = json.load(f)
        for pop_dict in pop_data:
            if pop_dict['Year'] == '2010':
                country = pop_dict['Country Name']
                pop = int(float(pop_dict['Value']))
                print(country + ': ' + str(pop))

#--------------------------------------------------
import requests
from operator import itemgetter
def apis():
    header("apis")
    url = "https://api.github.com/search/repositories?q=language:python&sort=stars"
    r = requests.get(url)
    print('status code:', r.status_code)
    response_dict = r.json()
    print(response_dict.keys())

    url = 'https://hacker-news.firebaseio.com/v0/topstories.json'
    r = requests.get(url)
    print('status code:', r.status_code)
    ids = r.json()
    submission_dicts = []
    for submission_id in ids[:30]:
        url = ('https://hacker-news.firebaseio.com/v0/item/' + str(submission_id) + '.json')
        submission_r = requests.get(url)
        print('status code:', submission_r.status_code)
        r_dict = submission_r.json()
        submission_dict = {
            'title': r_dict['title'],
            'link': 'http://news.ycombinator.com/item?id=' + str(submission_id),
            'comments': r_dict.get('descendants', 0)
        }
        submission_dicts.append(submission_dict)

    # sort by number of comments
    submission_dicts = sorted(submission_dicts, key=itemgetter('comments'), reverse=True)
    for submission_dict in submission_dicts:
        print("\nTitle:", submission_dict['title'])
        print("Discussion link:", submission_dict['link'])
        print("Comments:", submission_dict['comments'])

#--------------------------------------------------
import numpy as np
def numpy():
    header("numpy")
    '''
    ...At the core of the NumPy package, is the Ndarray object....
    - NumPy arrays have a fixed size at creation, unlike Python lists (which
        can grow dynamically). Changing the size of an ndarray will create a
        new array and delete the original.
    - The elements in a NumPy array are all required to be of the same data
        type, and thus will be the same size in memory. 
    '''
    # create arrays
    a = np.arange(10000000, dtype=int)
    b = np.arange(10000000, dtype=int)
    c = a+b

    b2 = np.arange(10, dtype=int)
    a2 = np.array([2.3,5.6, 8.3], float)
    print (a2)
    np.append(a2, 4.6)

    arr3 = np.concatenate((a2,b2))
    print(arr3)

    arr3 = np.insert(arr3, 0, 5.1)
    print(arr3)
    arr3 = np.delete(arr3, 0)
    print(arr3)
    print(arr3[0])
    print(arr3[0:3])

    #multidimensional arrays
    c = np.array([[2.4,5.6,7.3,4.5],[1, 8.3, 9.3,0.5]], float)
    print(c)
    print( c[0,2] )
    print(c[1])
    print(c[:, 1])
    for row in c:
        print(row[0])

    print( 'c has {rows} rows'.format(rows=len(c)))
    print('c has {columns} columns'.format(columns=len(c[0])))

    # reading data from file
    fileDataArray = np.genfromtxt('resources/countries.tsv', delimiter='\t')
    print('fileDataArray has {rows} rows'.format(rows=len(fileDataArray)))

    # saving a file
    dummy = np.zeros((3,3))
    np.savetxt('/tmp/dummy', dummy, fmt='%d')

    #slicing
    '''
    - When we use slicing on lists, it returns a new list object.
    - However, performing slicing on a NumPy array it will return a view of the original
        array. In other words while it is a subset of the array it is still pointing at the same
        data in memory as the original array. 
    '''
    d2 = np.array([[1,2,3],[2,4,5], [4,5,7]], float)
    ra = d2[:,2]
    print(ra)
    ra[1]=0
    print(d2)

    # copying
    d2 = np.array([[1, 2, 3], [2, 4, 5], [4, 5, 7]], float)
    print('d2:',d2)
    d2copy = np.copy(d2)
    d2copy[0,0] = 0
    print('d2copy:',d2copy)
    print('d2:',d2)

    if 7 in d2:
        print('found 7')
    else:
        print('havent found 7')

    #reshaping arrays
    d2 = np.array([[1, 2, 3], [2, 4, 5]], float)
    print(d2)
    d2b=d2.reshape((3,2))
    print(d2b)

    #appending to arrays
    d2 = np.array([[1, 2, 3], [2, 4, 5], [4, 5, 7]], float)
    d2 = np.append(d2, [[2,3,4]])
    print(d2)
    d2 = np.array([[1, 2], [2, 7]], float)
    d2 = np.append(d2, [[2, 3], [34,2]], axis=1)
    print(d2)
    d2 = np.array([[1, 2], [2, 7]], float)
    d2 = np.append(d2, [[2, 3], [34,2]], axis=0)
    print(d2)

    # max min
    print(np.amax(d2))
    print(np.amin(d2))

    print(np.mean(d2))
    print(np.var(d2))
    print(np.std(d2))

    a1 = np.array([10,20,30], float)
    a2 = np.array([1, 2, 3], float)

    print(a1*a2)
    print(a1 / a2)
    print(a1 ** a2)

    a1 = np.array([[10, 20], [30,40]], float)
    a2 = np.array([[1, 2], [3,4]], float)
    print(a1 * a2)
    print(a1 / a2)
    print(a1 + a2)
    '''
    When we perform array based indexing it produces a new copy of the array
(this is unlike slicing which produces a view of the original data)
    '''
    print(a1>a2)
    v=a1[np.array([True,False])]
    print( v[0]  )
    print(v[0][np.array([True, False])])

    # logical operations in multidimensional arrays
    a1 = np.array([10, 20, 30], float)
    print( a1 > 12 )
    print('logical', np.logical_and(a1>12,a1<30))
    a1 = np.array([[10, 20], [30, 40]], float)
    print('logical md', a1[np.logical_and(a1[:,0] > 12, a1[:,1] > 30)])

    a1 = np.array([[10, 20], [30, 40]], float)
    print(a1)
    print(a1[0,:]>12)
    print('first row with higher than 12', a1[a1[0,:]>12])

    a1 = np.array([[10, 20], [30, 40]], float)
    a2 = np.array([[30, 10], [55, 33]], float)
    print(np.where(a1 > a2, a1, a2))


#--------------------------------------------------
import matplotlib.pyplot as plt
import scipy.optimize as opt
import scipy.stats as stats

def func(x):
    return x**2 + 10*np.sin(x)

def scipy():
    header("scipy")
    coords = np.arange(-20,20, 0.1)
    #plt.plot(coords, func(coords))
    #plt.show()

    # gradient descent
    print(opt.fmin_bfgs(func, 20))

    # linear regression
    x = np.array([1,2,5,7,10,15,4,6,14,14])
    y = np.array([2,6,7,9,14,19,6,10,12,13])
    #plt.plot(x,y,'ro')
    #plt.axis([0,30,0,30])
    #plt.show()

    slope, intercept, r_value, p_value, slope_std_err = stats.linregress(x,y)
    predict_y= intercept+ slope * x
    #plt.plot(x, y, 'o')
    #plt.plot(x, predict_y, 'k-')
    #plt.show()

#--------------------------------------------------
import pandas as pd
def pandas():
    header("pandas")

    # --- Series
    s1 = pd.Series(np.random.randn(5))
    s2 = pd.Series(np.random.randn(5), index=['a','b','c','d','e'])

    print (s1)
    print (s2)
    '''
    As with NumPy, relational operators
    return a separate copy of the data. The
    original series and the one returned by
    the relational operator don’t refer to
    the same copy of the same data. 
    '''
    s2 = pd.Series({'a': 12, 'b': 16})
    print(s2)
    print(s2['a'])
    print(s2[['a','b']])
    print(s2[s2>14])
    # series operations
    print(s2*2)
    print(np.square(s2))
    print(pd.unique(s2))

    # --- DataFrame
    '''
    To create a DataFrame out of common Python data structures, we can
    pass a dictionary of lists to the DataFrame constructor.
    • We can also easily create a dataframe by passing it a 2D NumPy array.
    • The syntax for creating a data frame is as follows:
    – DataFrame(data, columns=listOfColumns)
    '''
    data = {'student': ['jim', 'pat', 'peter'],
            'grade': [67,44,56]
            , 'dept': ['C', 'S', 'C']}
    students = pd.DataFrame(data)
    print(students)

    s1 = pd.Series(np.random.randn(3), index=['a', 'b', 'c'])
    s2 = pd.Series(np.random.randn(4), index=['a', 'b', 'c', 'd'])
    df = pd.DataFrame({'one': s1, 'two':s2})
    print(df)

    arr = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]], float)
    df = pd.DataFrame(arr)
    print(df)

    arr = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]], float)
    df = pd.DataFrame(arr, columns=['colA', 'colB', 'colC'])
    print(df)

    # reading data into a dataframe
    '''
    There are a number of ways of doing this
    read_csv,read_excel,read_hdf,read_sql,read_json,read_sas …
    '''
    df = pd.read_csv('resources/titanic.csv')
    print(df.describe())

    #accessing data in the Dataframe
    print(df['Age'])
    print(df[:5])
    print( df[['Age', 'Fare']] )
    print(df['Age'].tail())

    # number of occurrences
    print(df['Age'].value_counts())

    # operations
    print(df['Age'].head())
    print(df['Age'].head() + 5)

    survivals = df['Name'][df['Survived']==1]
    print(survivals)

    # all that survived and were under 10
    print(df[['Name','Age']][df['Survived'] == 1][df['Age']<10])

    # pclass and men and women breakdown of survived
    pclasses = pd.unique(df['Pclass'])
    grouped = df[['Pclass','Sex', 'Survived']].groupby('Pclass', sort=True)
    for pc in pclasses:
        g = grouped.get_group(pc)
        print(pc)
        print(g['Sex'][g['Survived']==1].value_counts())

    # condition combination
    c1 = df['Pclass']  == 1
    c2 = df['Embarked'] == 'S'
    c3 = df['Age'] > 55
    print(df[ c1 & c2 | c3 ])

    # converting series to numpy arrays
    '''
    We already mentioned that Dataframe is composed on multiple Series object.
    • However, a Series object is internally a NumPy array.
    • If you add values to the end of any Series, you'll get its internal numpy array
    • We can also add .values to a dataframe to produce a 2D numpy array
    • To do this you need to ensure there are only numerical contents in all columns
    '''

    allPassengers = df['Pclass'].value_counts()
    died = df[df['Survived']==0]
    diedFrq = died['Pclass'].value_counts()
    print(diedFrq/allPassengers)
    print(allPassengers / len(df['Pclass']))

#--------------------------------------------------
def matplotlib():
    header("matplotlib")
    '''
    matplotlib.pyplot is a collection of command style functions.
    – Each pyplot function makes some change to a figure:
    • Create a figure
    • Create a plotting area in a figure
    • Plot some lines in a plotting area
    • Format the plot with labels
    '''
    #plt.plot([1,2,3], [3,5,8])
    #plt.show()

    a1 = np.array([1,2,3])
    a2 = np.array([3,5,8])
    #plt.plot(a1,a2)
    #plt.show()

    #2d arrays
    a1 = np.array([[1,2,3], [3,5,8]])
    a2 = np.array([[3, 5, 6], [13, 15, 18]])
    #plt.plot(a1, a2)
    #plt.show()

    # plot multiple lines
    #plt.plot([1,2,3,4],[5,10,15,20])
    #plt.plot([4, 50], [60, 89])
    #plt.show()

    # set viewable area of the graph
    #plt.plot([1, 2, 3, 4], [5, 10, 15, 20])
    #plt.axis([0,10,0,25])
    #plt.show()

    # pandas DataFrame
    a = pd.DataFrame(np.random.rand(4,5), columns=list('abcde'))
    #plt.plot(a)
    #plt.show()

    d = {
        'one': np.random.randn(10)
        , 'two': np.random.randn(10)
    }

    df = pd.DataFrame(d)
    #df.plot()
    #plt.show()

    s2 = pd.Series(np.random.randn(5), index=['a', 'b', 'c', 'd', 'e'])
    #s2.plot()
    #plt.show()

    # plotting multiple lines
    t = np.arange(0., 5., 0.2)
    #red dashes, blue squares and green triangles
    #plt.plot(t, t, 'r--', t, t**2, 'bs', t, t**3, 'g^')
    #plt.show()

    # creating a Bar graph
    data = pd.read_csv('resources/titanic.csv')
    died = data[data['Survived'] == 0]
    males_died = died[died['Sex'] == 'male']
    females_died = died[died['Sex'] == 'female']

    pclass_males_died = males_died.groupby('Pclass')
    pclass_females_died = females_died.groupby('Pclass')
    y = pclass_males_died['Survived'].count()
    y2 = pclass_females_died['Survived'].count()

    labels = ['class 1','class 2','class 3']
    index = np.arange(1,4)
    #plt.bar(index, y)
    #plt.xticks(index+0.5, labels)
    #plt.show()

    bar_width = 0.35
    #plt.bar(index, y, bar_width, color='g')
    #plt.bar(index+bar_width, y2, bar_width, color='b')
    #plt.xticks(index+bar_width, labels)
    #plt.show()

    # Scatter plot
    '''
    Scatter Plots can be created using matplotlib.pyplot.scatter
    – Input data x, y (arrays), s : scalar size or array, optional, default: 20
    – c : color
    '''
    n=100
    x = np.random.rand(n)
    y = np.random.rand(n)
    area = np.pi * (np.random.rand(n))

    x2 = np.random.rand(n)
    y2 = np.random.rand(n)
    area2 = np.pi * (np.random.rand(n))

    #plt.scatter(x,y,s=area, color='r')
    #plt.scatter(x2, y2, s=area2, color='g')
    #plt.show()

    # Histograms
    '''
    Taking a list of numbers
    – Binning those numbers within a number of ranges
    – And counting the number of occurrences in each bin.
    '''
    numbers = np.random.normal(size=1000)
    #plt.hist(numbers)
    #plt.hist(numbers, bins=40)
    #defining bin edges
    #plt.hist(numbers, bins=[-5,-4,-3,-2,-1,0,1,2,3,4,5])

    #plt.title("gaussian numbers")
    #plt.xlabel('value')
    #plt.ylabel('frequency')
    #plt.show()

    # Pie charts
    '''
    The following are various arguments for the pie function
    • explode: [ None | list of sections to explode]
    • colors: [color sequence for each segment]
    • labels: [A sequence of strings providing the labels for each wedge
    • autopct: [ None | format string | format function ]
    • shadow: [ False | True ] Draw a shadow beneath the pie.
    • radius: [ None | scalar ] The radius of the pie, if radius is None it will be set to 1
    '''
    labels = ['FF', 'FG', 'Labour', 'Sin Fein']
    popularity = [23,34,244,76]
    section2explode=[0,0.05,0,0]
    plt.pie(popularity, explode = section2explode, labels=labels,
            autopct='%1.1f%%', shadow=True, startangle=90)
    plt.title('political parties')
    plt.show()


#--------------------------------------------------
#--------------------------------------------------
#--------------------------------------------------
operators()
strings()
string_functions()
# user_input()
lists()
arrays()
list_slicing()
# conditionals()
loops()
iterables()
#primes()
files()
classes()
stdlib()
classes()
exceptions()
storing_data()
#matplotlib()
#random_walk()
pygal()
#downloading_data()
#apis()
numpy()
scipy()
pandas()
matplotlib()
