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
    letter = words[0]
    value = words[1]

    # 4. Get the year and the temperature from value
    value = value.rstrip(')')
    value = value.strip('(')

    fields = value.split(',')
    num_words = int(fields[0])
    total_length = int(fields[1])

    return letter, num_words, total_length

#---------------------------------------
#  FUNCTION print_key_value
#---------------------------------------
def print_key_value(letter, num_words, total_length, output_stream):
    current_value = str(total_length / (num_words * 1.0))
    res = letter + '\t' + current_value + '\n'
    output_stream.write(res)

# ------------------------------------------
# FUNCTION my_reduce
# ------------------------------------------
def my_reduce(input_stream, output_stream):
    # 1. We have initially read no key-value, so our current key is empty and the number of workds and their total amount of letters is 0
    current_letter = ""
    current_num_words = 0
    current_total_length = 0

    # 1. We read one by one the pairs key-value passed from the mapper
    for text_line in input_stream.readlines():
        # 1.1. We extract the key and the value
        (new_letter, new_num_words, new_total_length) = get_key_value(text_line)

        # We process the key-value pair
        # 1.2. If we deal with a new key
        if new_letter != current_letter:
            # 1.2.1 If it is a truly new key it means we have finished processing the previous key words,
            # so we print the final key-value pair for them
            if current_letter != "":
                print_key_value(current_letter, current_num_words, current_total_length, output_stream)

            # 1.2.2. We assign the current key to the new key we are starting processing
            current_letter = new_letter
            current_num_words = 0
            current_total_length = 0

        # 1.3. We add the words and its total letters to the accumulated values
        current_num_words = current_num_words + new_num_words
        current_total_length = current_total_length + new_total_length

    # 2. Once the entire set of pairs key-value pairs assigned to the reducer have been processed,
    # we print the final key-value pair for the last category
    if current_letter != "":
        print_key_value(current_letter, current_num_words, current_total_length, output_stream)

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
