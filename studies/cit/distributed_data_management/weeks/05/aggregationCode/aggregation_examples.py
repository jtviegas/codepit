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

import pymongo
import json
import sys

# --------------------------------------------------------
#
# FUNCTION load_json_file
#
# --------------------------------------------------------
def load_json_file(file_path):
    my_file = open(file_path, 'r')
    content = json.load(my_file)
    my_file.close()
    return content

# --------------------------------------------------------
#
# FUNCTION store_json_file
#
# --------------------------------------------------------
def store_json_file(file_path, d):
    my_file = open(file_path, 'w')
    json.dump(d, my_file)
    my_file.close()

# --------------------------------------------------------
#
# FUNCTION aggregation_command
#
# --------------------------------------------------------
def aggregation_command(db, option):
    res = []
    #
    #---------------------------------
    #
    #  1. Match the documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 1.1. With a single condition
    #---------------------------------
    if option == 11:
        res = list(db.c.aggregate([
            { "$match" : { "eyes" : "Blue" } }
        ]))
    #
    #---------------------------------
    # 1.2. With multiple conditions
    #---------------------------------
    #
    if option == 12:
        res = list(db.c.aggregate([
            { "$match" : { "eyes" : "Blue", "city" : "Paris" } }
        ]))
    #
    #---------------------------------
    # 1.3. It can return no documents at all
    #---------------------------------
    #
    if option == 13:
        res = list(db.c.aggregate([
            { "$match" : { "eyes" : "Blue", "city" : "Madrid" } }
        ]))
    #---------------------------------
    #
    #  2. Group the documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 2.1. Grouping by a single field
    #---------------------------------
    #
    if option == 21:
        res = list(db.c.aggregate([
            { "$group" : { "_id" : "$eyes" } }
        ]))
    #
    #---------------------------------
    # 2.2. Grouping by a single field and add something to the resulting document
    #---------------------------------
    #
    if option == 22:
        res = list(db.c.aggregate([
            { "$group" : { "_id" : "$eyes", "total" : { "$sum" : 1 } } }
        ]))
    #
    #---------------------------------
    # 2.3. Grouping by several fields (and add something to the resulting document)
    #---------------------------------
    #
    if option == 23:
        res = list(db.c.aggregate([
            { "$group" : { "_id" : {"col" : "$eyes", "cit" : "$city"}, "total" : { "$sum" : 1 } } }
        ]))
    #---------------------------------
    #
    #  3. Sort the documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 3.1. Sort based on a single field
    #---------------------------------
    #
    if option == 31:
        res = list(db.c.aggregate([
            { "$sort" : { "eyes" : 1 } }
        ]))
    #
    #---------------------------------
    # 3.2. Sort based on several fields
    #---------------------------------
    #
    if option == 32:
        res = list(db.c.aggregate([
            { "$sort" : { "eyes" : -1, "city" : 1 } }
        ]))
    #---------------------------------
    #
    #  4. Limit to a number of documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 4.1. Limit the collection to a maximum number of documents
    #---------------------------------
    #
    if option == 41:
        res = list(db.c.aggregate([
            { "$limit" : 3 }
        ]))
    #---------------------------------
    #
    #  5. Count number of documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 5.1. This is not properly an aggregation command
    #---------------------------------
    #
    if option == 51:
        print(db.c.count())
    #
    #---------------------------------
    # 5.2. Counting the elements with an aggregation command
    #---------------------------------
    #
    if option == 52:
        res = list(db.c.aggregate([
            { "$group" : { "_id" : "null", "how_many" : { "$sum" : 1 } } }
        ]))
    #---------------------------------
    #
    #  6. Project the documents
    #
    #---------------------------------
    #
    #---------------------------------
    # 6.1. Modify the document to contain only some fields
    #---------------------------------
    #
    if option == 61:
        res = list(db.c.aggregate([
            { "$project" : { "eyes" : 1, "city" : 1 } }
        ]))
    #
    #---------------------------------
    # 6.2. To not to show the ObjectId field you have to explicitly state it
    #---------------------------------
    #
    if option == 62:
        res = list(db.c.aggregate([
            { "$project" : { "_id" : 0, "eyes" : 1, "city" : 1 } }
        ]))
    #
    #---------------------------------
    # 6.3. To add fields belonging to a subfield (as a subfield)
    #---------------------------------
    #
    if option == 63:
        res = list(db.c.aggregate([
            { "$project" : { "_id" : 0, "eyes" : 1, "city" : 1, "identifier.name" : 1 } }
        ]))
    #
    #---------------------------------
    # 6.4. To add fields belonging to a subfield (as a normal field)
    #---------------------------------
    #
    if option == 64:
        res = list(db.c.aggregate([
            { "$project" : { "_id" : 0, "eyes" : 1, "city" : 1, "my_new_field" : "$identifier.name" } }
        ]))
    #
    #---------------------------------
    # 6.5. To add new fields
    #---------------------------------
    #
    if option == 65:
        res = list(db.c.aggregate([
            { "$project" : { "_id" : 0, "eyes" : 1, "city" : 1, "sports_I_like" : { "$size" : "$likes" } } }
        ]))
    #---------------------------------
    #
    #  7. Size of a list
    #
    #---------------------------------
    #
    #---------------------------------
    # 7.1. To compute the size of a list
    #---------------------------------
    #
    if option == 71:
        res = list(db.c.aggregate([
            { "$project" : { "_id" : 0, "eyes" : 1, "city" : 1, "sports_I_like" : { "$size" : "$likes" } } }
        ]))
    #---------------------------------
    #
    #  8. Unwind a list
    #
    #---------------------------------
    #
    #---------------------------------
    # 8.1. To unfold the content of a list
    #---------------------------------
    #
    if option == 81:
        res = list(db.c.aggregate([
            { "$unwind" : "$likes" }
        ]))
    #

    #---------------------------------
    #
    #  9. Aggregation Pipeline
    #
    #---------------------------------
    #
    #---------------------------------
    # 9.1. Get the sport with higher number of points
    #---------------------------------
    #
    if option == 91:
        pipeline1 = []

        # 9.1.1. Unfold the likes
        command1 = {"$unwind" : "$likes" }
        pipeline1.append(command1)

        # 9.1.2. Project to get just the sport and the scores
        command2 = { "$project" : { "_id" : 0, "sport" : "$likes.sport", "points" : "$likes.score" } }
        pipeline1.append(command2)

        # 9.1.3. Group them by sport
        command3 = { "$group" : { "_id" : "$sport", "points" : { "$sum" : "$points" } } }
        pipeline1.append(command3)

        # 9.1.4. Sort them in decreasing order by points
        command4 = { "$sort" : { "points" : -1 } }
        pipeline1.append(command4)

        # 9.1.5. Limit the collection to one document, to get just the one with more points
        command5 = { "$limit" : 1 }
        pipeline1.append(command5)

        res = list(db.c.aggregate(pipeline1))

    return res

# ------------------------------------------
#
# FUNCTION my_main
#
# ------------------------------------------
def my_fun(option):
    # 0. We set up the connection to mongos.exe allowing us to access to the cluster
    client = pymongo.MongoClient()

    # 1. We drop the database and create it again
    client.drop_database('practise')
    db = client.practise

    # 2. We read the documents from the file and insert them again in the collection
    content = load_json_file("my_collection.json")
    documents = content["documents"]
    db.c.insert(documents)

    # 3. Perform whatever aggregation_command option you have selected
    res = aggregation_command(db, option)

    # 4. Print the collection c' obtained from applying the command
    print("---------------------------------")
    print("---                           ---")
    print("---     LIST OF RESULTS       ---")
    print("---                           ---")
    print("---------------------------------")

    for i in range(0, len(res)):
        # 4.1. We get the i-est document
        document = res[i]
        print("---------------------------------")
        print("--- DOCUMENT", (i+1), "INFO   ---")
        print("---------------------------------")
        # 4.2. We traverse all the fields of the document
        for j in document:
            print(j, " : ", document[j])

    # 5. Close the client
    client.close()

# ---------------------------------------------------------------
#           PYTHON EXECUTION
# This is the main entry point to the execution of our program.
# It provides a call to the 'main function' defined in our
# Python program, making the Python interpreter to trigger
# its execution.
# ---------------------------------------------------------------
if __name__ == '__main__':
    # 1. To run from command line
    option = int(sys.argv[1])
    my_fun(option)

    # 2. To run from PyCharm or Spyder
    #option = 11
    #my_fun(option)

