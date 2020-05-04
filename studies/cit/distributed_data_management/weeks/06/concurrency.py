
#--------------------------------------------------------
#           PYTHON PROGRAM
# Here is where we are going to define our set of...
# - Imports
# - Global Variables
# - Functions
# ...to achieve the functionality required.
# When executing > python 'this_file'.py in a terminal,
# the Python interpreter will load our program,
# but it will execute nothing yet.
#--------------------------------------------------------

import random
import multiprocessing
import time

#--------------------------------------------------
# dummy_algorithm
#--------------------------------------------------
def dummy_algorithm(initial, number, iterations):
    exec_time = time.time()

    for i in range(0, iterations):
        for j in range(0, number-1):
            initial = initial + random.randint(0,9)
        initial = initial / number

    exec_time = time.time() - exec_time
    print(exec_time)

    return initial, exec_time

#--------------------------------------------------
# trigger_function_with_arguments
#--------------------------------------------------
def trigger_function_with_arguments(args):
   return dummy_algorithm(*args)

#--------------------------------------------------
# my_main
#--------------------------------------------------
def my_main():
    pool = multiprocessing.Pool()
    res = pool.map(trigger_function_with_arguments,[(5, 5, 1000000), (5, 3, 1000000), (5, 7, 1000000)])
    print(res)

#---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
#---------------------------------------------------------------

if __name__ == "__main__":
    my_main()
