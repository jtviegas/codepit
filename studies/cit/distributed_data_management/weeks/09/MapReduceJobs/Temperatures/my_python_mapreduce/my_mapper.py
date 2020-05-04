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
#  FUNCTION is_a_int
#---------------------------------------
def is_a_int(value):
    try:
        int(value)
        return True

    except ValueError:
        return False

#---------------------------------------
#  FUNCTION is_a_float
#---------------------------------------
def is_a_float(value):
    try:
        float(value)
        return True

    except ValueError:
        return False

#---------------------------------------
#  FUNCTION process_first_line
#---------------------------------------
def process_first_line(line):
    # 1. We get rig of the end line character
    line = line.replace('\n', '')

    # 2. We strip any white character at the end
    line = line.rstrip()

    # 3. We split the info by tabulator
    words = line.split(' ')

    # 4. We get the name of the city (first word)
    city = words[0]

    # 3. We return the name of the city
    return city

#---------------------------------------
#  FUNCTION process_year_line
#---------------------------------------
def process_year_line(line):
    # 1. We get rig of the end line character
    line = line.replace('\n', '')

    # 2. We strip any white character at the end
    line = line.rstrip()

    # 3. We strip any white character at the begining
    line = line.strip()

    # 4. We split the info by tabulator
    words = line.split(' ')

    # 5. Remove empty words
    k = len(words)-1
    while k >= 0:
        if words[k] == '':
            del words[k]
        k = k - 1

    # 6. We collect the year and the temperature
    # 6.1. If the line has enough fields, we process them.
    if (len(words) >= 4):
        # 6.1.1. We collect the fields
        year = words[0]
        temp = words[3]

        # 6.1.2 We check whether the fields have the right datatype
        if is_a_int(year) == True and is_a_float(temp) == True:
            year = int(year)
            temp = float(temp)
        # 6.1.3. Otherwise, we return dummy values
        else:
            year = 10000
            temp = 10000.0
    # 6.2. Otherwise, we return dummy values
    else:
        year = 10000
        temp = 10000.0

    # 7. We return both year and temperature
    return year, temp

# ------------------------------------------
# FUNCTION my_map
# ------------------------------------------
def my_map(input_stream, output_stream):
    # 1. We process the name of the city and compute an initial big enough value for year and temperature
    intro_line = input_stream.readline()
    city = process_first_line(intro_line)
    year = 10000
    temp = 10000.0

    # 2. We discard next 6 lines
    for t in range(0, 6):
        input_stream.readline()

    # 3. We process each year for which information has been stored
    for year_line in input_stream.readlines():
        # 3.1. We process the line
        (new_year, new_temp) = process_year_line(year_line)

        # 3.2. We compare the new_temperature with the one already stored
        if new_temp < temp:
            temp = new_temp
            year = new_year

    # 4. We print the key value
    res = city + '\t' + '(' + str(year) + ',' + str(temp) + ')' + '\n'
    output_stream.write(res)

# ------------------------------------------
# FUNCTION my_main
# ------------------------------------------
def my_main():
    # We pick the working mode:
    # Mode 1: Debug --> We pick a file to read test the program on it
    #my_input_stream = open("Aberporth.txt", "r")
    #my_output_stream = open("mapResult.txt", "w")

    # Mode 2: Actual MapReduce --> We pick std.stdin and std.stdout
    my_input_stream = sys.stdin
    my_output_stream = sys.stdout

    # We launch the Map program
    my_map(my_input_stream, my_output_stream)

# ---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
# ---------------------------------------------------------------
if __name__ == '__main__':
    my_main()
