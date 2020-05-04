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
#  FUNCTION process_line
#---------------------------------------
def process_line(line):
    # 1. We get rid of the end line character
    line = line.replace('\n', '')

    # 2. We strip any white character at the end
    line = line.rstrip()
    line = line.rstrip('\t')

    # 3. We strip any white character at the begining
    line = line.strip()
    line = line.strip('\t')

    # 4. We split the info by tabulators or white spaces
    line = line.replace('\t', ' ')
    words = line.split(' ')

    # 5. We get rid of any empty word (if it exists)
    size = len(words)-1
    while size >= 0:
        if words[size] == '':
            del words[size]
        size = size - 1

    # 6. Return the parsed words
    return words

#---------------------------------------
#  FUNCTION get_index_in_alphabet
#---------------------------------------
def get_index_in_alphabet(letter):
    # 1. We put an index of -1 by default
    index = -1

    # 2. If the letter is an uppercase or lowercase letter, we update index to its position in the alphabet
    if ord(letter) >= ord('a') and ord(letter) <= ord('z'):
        index = ord(letter) - ord('a')
    if ord(letter) >= ord('A') and ord(letter) <= ord('Z'):
        index = ord(letter) - ord('A')

    # 3. We return the index
    return index

#---------------------------------------
#  FUNCTION get_letter_from_index
#---------------------------------------
def get_letter_from_index(value):
    # 1. We get the int value of character 'a'
    v = ord('a')

    # 2. We add to it the index we want to access
    new_v = v + value

    # 3. We convert the index to a character
    c = chr(new_v)

    # 4. We return the character
    return c

# ------------------------------------------
# FUNCTION my_map
# ------------------------------------------
def my_map(input_stream, output_stream):
    # 1. We create two lists for counting the word appearances with starting letter and their total length
    # Initially both lists will be empty
    num_words = []
    total_length = []
    for i in range(0, (ord('Z') - ord('A')) + 1):
        num_words.append(0)
        total_length.append(0)

    # 2. We process the lines, to fill our lists
    for text_line in input_stream.readlines():
        # 2.1. We process the line to obtain its words
        words_list = process_line(text_line)

        # 2.2. We traverse the words, filling the associated position of the array (if it starts by a letter)
        for i in range(0, len(words_list)):
            # 2.2.1. We get the word
            word = words_list[i]

            # 2.2.2. We get the index in the alphabet of its first letter
            index = get_index_in_alphabet(word[0])

            # 2.2.3. If index is greater-equal than 0, we update num_words and its total_length at the associated position
            if index >= 0:
                num_words[index] = num_words[index] + 1
                total_length[index] = total_length[index] + len(word)

    # 3. Print the (key, value) pairs
    for i in range(0, len(num_words)):
        if num_words[i] > 0:
            letter = get_letter_from_index(i)
            number_of_words = str(num_words[i])
            total_letters = str(total_length[i])
            res = letter + '\t' + '(' + number_of_words + ',' + total_letters + ')' + '\n'
            output_stream.write(res)

# ------------------------------------------
# FUNCTION my_main
# ------------------------------------------
def my_main():
    # We pick the working mode:
    # Mode 1: Debug --> We pick a file to read test the program on it
    #my_input_stream = open("comedies.txt", "r")
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
