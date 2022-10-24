import inspect
import math
from functools import wraps
from inspect import signature
import queue
from abc import ABC, abstractmethod
import logging

logging.basicConfig(
    filename="question3-B.log",
    level=logging.INFO,
    format="%(asctime)s:%(levelname)s:%(message)s"
)


#==========================================================================================
# ====================================== Question 1 - A ===================================
#==========================================================================================



def getSum(n):
    sum = 0
    i = 1
    while i <= (math.sqrt(n)):
        if n % i == 0:
            if n / i == i:
                sum = sum + i
            else:
                sum = sum + i
                sum = sum + (n / i)
        i = i + 1
    sum = sum - n
    return sum


def checkNumber(n):
    if (getSum(n) >= n):
        return True
    else:
        return False

#==========================================================================================
# ====================================== Question 1 - B ===================================
#==========================================================================================

def makeDict(list1):
    return getAllDeviders(list1)


def getAllDeviders(list):
    trueNumberList = []
    for x in list:
        if (checkNumber(x)):
            trueNumberList.append(x)
    print("The numbers that are abundant from the list are:\n")
    print(trueNumberList)
    dict2 = {}
    for i in trueNumberList:
        k = 2
        while k <= i / 2 + 1:
            if (i % k == 0):
                if (k in dict2.keys()):
                    dict2[k] += 1
                else:
                    dict2[k] = 1

            k = k + 1
    return dict2

#==========================================================================================
# ====================================== Question 1 - C ===================================
#==========================================================================================
def gen_expression(num):
    halfPoint = int((num / 2) + 1)
    gen_exp = (x for x in range(1, halfPoint + 1, 1) if num % x == 0)
    y = next(gen_exp)
    if (y >= halfPoint):
        print("Detected Prime Number:" + str(next(gen_exp)))
        return False
    while (not isPrime(y)):
        try:
            y = next(gen_exp)
        except:
            print("There are no dividers for the number besides natural 1,the number:" + str(num) + " is Prime")
            return False
    if (isPrime(y)):
        print("Prime number detected:" + str(y))
        return False
    else:
        print("No prime numbers")


def isPrime(n):
    # Corner cases
    if (n <= 1):
        return False
    if (n <= 3):
        return True

    # This is checked so that we can skip
    # middle five numbers in below loop
    if (n % 2 == 0 or n % 3 == 0):
        return False

    i = 5
    while (i * i <= n):
        if (n % i == 0 or n % (i + 2) == 0):
            return False
        i = i + 6

    return True

#==========================================================================================
# ====================================== Question 2 - A ===================================
#==========================================================================================
def question2A(lst):
    q2dict = {'c': [], 'i': [], 'f': [], 'o': []}
    objects = filter(lambda x: type(x) != str and type(x) != float and type(x) != int, lst)
    floats = filter(lambda x: type(x) == float, lst)
    integers = filter(lambda x: type(x) == int, lst)
    characters = filter(lambda x: type(x) == str, lst)
    q2dict['c'] = list(characters)
    q2dict['o'] = list(objects)
    q2dict['f'] = list(floats)
    q2dict['i'] = list(integers)
    return q2dict

#==========================================================================================
# ====================================== Question 2 - B ===================================
#==========================================================================================
def question2B(lst):
    temp1 = []
    temp2 = filter(lambda x: len(signature(x).parameters) == 1, lst)
    temp1.append(list(temp2))
    return temp1


# just a test function to check if this function will be taken as part of the single input functions
def testFuncforQ2B(num1, num2, num3):
    num = num1 + num2 + num3
    return num

#==========================================================================================
# ====================================== Question 3 - A ===================================
#==========================================================================================
inputList = list()
outputList = list()


def calculate_avarages(func):
    def inner1(*args):
        inputList.append(args[0])
        outputList.append(func(*args))
        print("input list is:")
        print(inputList)
        print("output list is:")
        print(outputList)
        print("The average input is:" + str(sum(inputList) / len(inputList)))
        print("The average output is:" + str(sum(outputList) / len(outputList)) + "\n")

    return inner1

#==========================================================================================
# some test functions for question 3 - A
#==========================================================================================
@calculate_avarages
def squere(a):
    return pow(a, 2)


@calculate_avarages
def reduce1(a):
    return a - 1


@calculate_avarages
def multiplyBy2(a):
    return a * 2
#==========================================================================================
# ====================================== Question 3 - B ===================================
#==========================================================================================


def loggingInputOutput(func):
    def inner1(*args,**kwargs):
        argumentInput=args
        argumentInputString = ''.join(str(argumentInput))
        keyworInput = kwargs
        keyworInputString = ''.join(str(keyworInput))
        if(argumentInputString=="()"):
            argumentInputString=""
        if(keyworInputString=="{}"):
            keyworInputString=""
        functionOutput=func(*args,**kwargs)
        logging.info("you called "+ func.__name__+""+(argumentInputString)+(keyworInputString)
                     +" it returned "+str(functionOutput))

    return inner1
#==========================================================================================
#some test functions for question 3-B
#==========================================================================================
@loggingInputOutput
def squere1(a):
    return pow(a, 2)

@loggingInputOutput
def reduce11(a):
    return a - 1

@loggingInputOutput
def multiplyBy21(a):
    return a * 2
@loggingInputOutput
def sumItAll(a,b,c):
    return a+b+c
@loggingInputOutput
def returnOposite(key,key2):
    return not key

#==========================================================================================
# ====================================== Question 4  ======================================
#==========================================================================================
q = queue.Queue(3)


def print3LastFunctions(func):
    def echo_func(*args, **kwargs):
        q.put(func.__name__)

        if (q.full()):
            t1 = q.get()
            t2 = q.get()
            t3 = q.get()
            print("The latest functions that were called: " + t1 + " , " + t2 + " , " + t3 + "\n")
            q.put(t2)
            q.put(t3)
        return func(*args, **kwargs)

    return echo_func


# some test functions for question 4

@print3LastFunctions
def squereSecond(a):
    return pow(a, 2)


@print3LastFunctions
def reduce1Second(a):
    return a - 1


@print3LastFunctions
def multiplyBy2Second(a):
    return a * 2


@print3LastFunctions
def multiplyBy3(a):
    return a * 2

#==========================================================================================
# ====================================== Question 5  ======================================
#==========================================================================================
class Twitter:


    def __init__(self, name):
        self.name = name

        print(self.name, " joined twitter\n")


        self._observers = []

    def tweet(self, tweet, modifier=None):
        self._tweet=tweet

        print(tweet)
        for observer in self._observers:
            if modifier != observer:
                observer.update(self)

    def follow(self, observer):



        if self not in observer._observers:
            observer._observers.append(self)

    def unfollow(self, observer):



        try:
            self._observers.remove(observer)
        except ValueError:
            pass

    def update(self, subject):
        print(subject._tweet)


class Follower(Twitter):


    def __init__(self, name=''):
        Twitter.__init__(self, name)
        self.name = name
        self._data = 0

    @property
    def data(self):
        return self._data

    @data.setter
    def data(self, value):
        self._data = value
        self.tweet(value)





if __name__ == '__main__':
    print("\n========================================================================")
    print("========================= Question 1 - A Tests ===========================")
    print("========================================================================\n")
    # Question 1 - A tests
    print("Is 12 an abundant number? :"+str(checkNumber(12))+"\n")
    print("Is 12 an abundant number? :"+str(checkNumber(15))+"\n")
    print("Is 12 an abundant number? :"+str(checkNumber(28))+"\n")


    # Question 1 - B test
    print("\n========================================================================")
    print("=========================  Question 1 - B Tests ==========================")
    print("========================================================================\n")
    testList = [12, 34, 28, 53, 64]
    testDict = makeDict(testList)
    print("\nThe divisors and their amounts are:\n")
    print(testDict)

    # Question 1 - C test
    print("\n========================================================================")
    print("========================= Question 1 - C Tests =========================")
    print("========================================================================\n")
    num = 8
    gen_expression(num)

    # Question 2 - A test
    print("\n========================================================================")
    print("========================= Question 2 - A Tests =========================")
    print("========================================================================\n")
    testList = [1, "Hello", 3.4, (1, 2), 2, 3, (3, 3), (4, 4), "There", 6.5]
    d = question2A(testList)
    print("\n" + str(d))

    # Question 2 - B test
    print("\n========================================================================")
    print("========================= Question 2 - A Tests =========================")
    print("========================================================================\n")
    testList = [gen_expression, pow, testFuncforQ2B, isPrime]
    tempList = question2B(testList)
    for x in range(len(tempList)):
        print(tempList[x])

    # Question 3 - A test
    print("\n========================================================================")
    print("========================= Question 3 - A Tests =========================")
    print("========================================================================\n")
    squere(5)
    reduce1(6)
    multiplyBy2(3)

    # Question 3 - B test
    #========================================================================\n")
    #========================= Question 3 - B Tests =========================\n")
    #========================================================================\n")

    squere1(5)
    reduce11(6)
    multiplyBy21(1)
    sumItAll(1, 2, 3)
    returnOposite(key=False,key2="hello")


    # Question 4 test
    print("\n========================================================================")
    print("============================ Question 4 Tests ==========================")
    print("========================================================================\n")
    squereSecond(5)
    reduce1Second(6)
    multiplyBy2Second(1)
    multiplyBy3(1)
    print("\n========================================================================")
    print("============================= Question 5 Tests =========================")
    print("========================================================================\n")
    a = Twitter('Alice')
    k = Twitter('King')
    q = Twitter('Queen')
    h = Twitter('Mad Hatter')
    c = Twitter('Cheshire Cat')
    a.follow(c)
    a.follow(h)
    a.follow(q)
    k.follow(q)
    q.follow(q)
    q.follow(h)
    h.follow(a)
    h.follow(q)
    h.follow(c)

    print(f'==== {q.name} tweets ====')
    q.tweet('Off with their heads!')
    print(f'\n==== {a.name} tweets ====')
    a.tweet('What a strange world we live in.')
    print(f'\n==== {k.name} tweets ====')
    k.tweet('Begin at the beginning, and go on till you come to the end: then stop.')
    print(f'\n==== {c.name} tweets ====')
    c.tweet("We're all mad here.")
    print(f'\n==== {h.name} tweets ====')
    h.tweet('Why is a raven like a writing-desk?')

