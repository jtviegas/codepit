#!/usr/bin/python

# --------------------------------------------------------
#           PYTHON PROGRAM
# Here is where we are going to define our set of...
# - Imports
# - Global Variables
# - Functions
# ...to achieve the functionality required.
# When executing > python 'this_file'.py in a terminal,
# the Python interpreter will load our program,
# but it will execute nothing yet.
# --------------------------------------------------------

import sys

#---------------------------------------
#  FUNCTION get_key_value
#---------------------------------------
def get_key_value(line):
    # 1. Get rid of the end of line at the end of the string
    line = line.replace('\n', '')

    # 2. Split the string by the tabulator delimiter
    words = line.split('\t')

    # 3. Get the key and the value and return them
    city = words[0]
    value = words[1]

    # 4. Get the year and the temperature from value
    value = value.rstrip(')')
    value = value.strip('(')

    fields = value.split(',')
    year = int(fields[0])
    temp = float(fields[1])

    return city, year, temp

# ------------------------------------------
# FUNCTION my_reduce
# ------------------------------------------
def my_reduce(input_stream, output_stream):
    # 1. We have initially read no key-value word, so our current city is empty and min year and temperature are dummy values
    best_city = ""
    best_year = 10000
    best_temp = 10000.0

    # 1. We read one by one the pairs key-value passed from the mapper
    for city_info in input_stream.readlines():
        # 1.1. We extract the key and the value
        (new_city, new_year, new_temp) = get_key_value(city_info)

        # 1.2. We compare the new temperature with the best we have so far. If it is better, we update it
        if new_temp < best_temp:
            best_city = new_city
            best_year = new_year
            best_temp = new_temp

    # 2. Once the entire set of pairs key-value pairs assigned to the reducer have been processed,
    # we print the final key-value for the best result
    res = best_city + '\t' + '(' + str(best_year) + ',' + str(best_temp) + ')' + '\n'
    output_stream.write(res)

# ------------------------------------------
# FUNCTION my_main
# ------------------------------------------
def my_main():
    # We pick the working mode:
    # Mode 1: Debug --> We pick our file to read test the program on it
    #my_input_stream = open("sort_simulation.txt", "r")
    #my_output_stream = open("reduce_simulation.txt", "w")

    # Mode 2: Actual MapReduce --> We pick std.stdin and std.stdout
    my_input_stream = sys.stdin
    my_output_stream = sys.stdout

    # We launch the Map program
    my_reduce(my_input_stream, my_output_stream)

# ---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
# ---------------------------------------------------------------
if __name__ == '__main__':
    my_main()
