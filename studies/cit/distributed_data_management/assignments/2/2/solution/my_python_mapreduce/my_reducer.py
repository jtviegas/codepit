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

import os
import io

POPULAR_LIST_SIZE = 5
REQUESTS_FIELD_INDEX = 0
OUTPUT_LINE_FORMAT = u"{}\t({}, {}, {})\n"
INPUT_DIR = '../my_result'
INPUT_FILE_PATTERN = INPUT_DIR + u"/{}"
OUTPUT_FILE = 'reduce_result.txt'

rankings = {}

# ---------------------------------------
#  FUNCTION get_key_value
# ---------------------------------------
def get_key_value(line):

    fields = line.split('\t')
    key = fields[0].strip()
    values = fields[1].strip()
    values = values.rstrip(')')
    values = values.strip('(')
    list_fields = values.split(',')
    field0 = list_fields[0].strip()
    field1 = list_fields[1].strip()
    field2 = list_fields[2].strip()

    return key, field0, field1, field2


# ------------------------------------------
# FUNCTION create_initial_list
# ------------------------------------------
def create_initial_list(length):
    result = []
    for i in range(length):
        result.append((0, 0, ""))
    return result


def find_lowest_tuple_index_by_field(list_of_tuples, field_index):
    lowest_list_index = None
    for list_index, t in enumerate(list_of_tuples):
        if lowest_list_index is None:
            lowest_list_index = list_index
        else:
            if t[field_index] < list_of_tuples[lowest_list_index][field_index]:
                lowest_list_index = list_index
    return lowest_list_index


# ------------------------------------------
# FUNCTION check_if_popular
# ------------------------------------------
def check_if_popular(current_list, new_requests, new_transferred, new_page):
    lowest_index = find_lowest_tuple_index_by_field(current_list, REQUESTS_FIELD_INDEX)
    if new_requests > current_list[lowest_index][REQUESTS_FIELD_INDEX]:
        del current_list[lowest_index]
        current_list.append((new_requests, new_transferred, new_page))


# ------------------------------------------
# FUNCTION print_key_value
# ------------------------------------------
def print_key_value(project, current_list, output_stream):
    for t in current_list:
        output_stream.write(OUTPUT_LINE_FORMAT.format(project, t[0], t[1], t[2]))


# ------------------------------------------
# FUNCTION my_reduce
# ------------------------------------------
def my_reduce(input_stream, output_stream):

    for line in input_stream:
            fields = get_key_value(line)
            proj = fields[0].strip()
            if proj not in rankings.keys():
                rankings[proj] = create_initial_list(POPULAR_LIST_SIZE)
            check_if_popular(rankings[proj], int(fields[1].strip()), int(fields[2].strip()), fields[3].strip())



# ------------------------------------------
# FUNCTION my_main
# ------------------------------------------
def my_main():
    # We pick the working mode:
    # Mode 1: Debug --> We pick our file to read test the program on it

    file_list = [f for f in os.listdir(INPUT_DIR) if os.path.isfile(os.path.join(INPUT_DIR, f))]
    with io.open(OUTPUT_FILE, 'w') as my_output_stream:
        for f in file_list:
            with io.open(INPUT_FILE_PATTERN.format(f), "r") as my_input_stream:
                my_reduce(my_input_stream, my_output_stream)
        for key in rankings.keys():
            print_key_value(key, rankings[key], my_output_stream)

    #my_input_stream = open("map_result.txt", "r")
    #my_output_stream = open("reduce_result.txt", "w")

    # Mode 2: Actual MapReduce --> We pick std.stdin and std.stdout
    #my_input_stream = sys.stdin
    #my_output_stream = sys.stdout

    # We launch the Map program
    #my_reduce(my_input_stream, my_output_stream)

# ---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
# ---------------------------------------------------------------
if __name__ == '__main__':
    my_main()
