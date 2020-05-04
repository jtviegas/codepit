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

import io
import os

POPULAR_LIST_SIZE = 5
LINE_SEPARATOR = " "
REQUESTS_FIELD_INDEX = 0
OUTPUT_LINE_FORMAT = u"{}\t({}, {}, {})\n"
DATASET_DIR = '../my_dataset'
OUTPUT_DIR = '../my_result'
REDUCE_OUTPUT_FILE_PATTERN = OUTPUT_DIR + u"/reduce_job_{}.txt"
INPUT_FILE_PATTERN = DATASET_DIR + u"/{}"

# ------------------------------------------
# FUNCTION process_line
# ------------------------------------------
def process_line(line):
    return line.split(LINE_SEPARATOR)


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
# FUNCTION my_map
# ------------------------------------------
def my_map(input_stream, output_stream):
    rankings = {}
    for line in input_stream:
            fields = process_line(line)
            proj = fields[0].strip()
            if proj not in rankings.keys():
                rankings[proj] = create_initial_list(POPULAR_LIST_SIZE)
            check_if_popular(rankings[proj], int(fields[2].strip()), int(fields[3].strip()), fields[1].strip())

    for key in rankings.keys():
        print_key_value(key, rankings[key], output_stream)


# ------------------------------------------
# FUNCTION my_main
# ------------------------------------------
def my_main():
    # We pick the working mode:
    # Mode 1: Debug --> We pick a file to read test the program on it
    file_list = [f for f in os.listdir(DATASET_DIR) if os.path.isfile(os.path.join(DATASET_DIR, f))]
    for index, file in enumerate(file_list):
        with io.open(REDUCE_OUTPUT_FILE_PATTERN.format(index), 'w') as my_output_stream:
            # with io.open("test.txt", "r") as my_input_stream:
            with io.open(INPUT_FILE_PATTERN.format(file), "r") as my_input_stream:
                my_map(my_input_stream, my_output_stream)

    # Mode 2: Actual MapReduce --> We pick std.stdin and std.stdout
    # my_input_stream = sys.stdin
    # my_output_stream = sys.stdout

    # We launch the Map program,


# ---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
# ---------------------------------------------------------------
if __name__ == '__main__':
    my_main()
