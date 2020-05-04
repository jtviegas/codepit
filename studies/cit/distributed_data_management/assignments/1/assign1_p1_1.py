#!/usr/bin/python3.7
import pymongo
import pprint
import re
from pymongo import MongoClient
from bson.code import Code

def initDb():
    client = MongoClient()
    db = client.get_database('tate')
    return db

def findArtists(db, name):
    cursor = db['artists'].find({'fc': re.compile(name, re.IGNORECASE)})
    result = list()
    for doc in cursor:
        result.append(doc)
    return result

def findArtistArtworks(db, name):
    cursor = db.artworks.find({'contributors.fc': name, 'contributors.role': 'artist'})
    result = list()
    for doc in cursor:
        result.append(doc)
    return result

def user_input_names():
    _input = input("please provide the names in `Surname,Firstname` format:")
    _names = _input.split(',')
    if len(_names) != 2:
        raise ValueError('should provide two comma separated names!')
    result = list()
    for _name in _names:
        result.append(_name.strip())
    return result

def print_artist_data(data):
    print("--- artist info ---")
    print(data["artist"]["fc"])
    print("gender: {}".format(data["artist"]["gender"]))
    print("birth place: {}".format(data["artist"]["birth"]["place"]["name"]))
    print("birth year: {}".format(data["artist"]["birth"]["time"]["startYear"]))
    if  0 < len(data["artist"]["movements"]):
        print("movements:")
        for _mov in  data["artist"]["movements"]:
            print("\t {name} ({era})".format(name=_mov["name"], era=_mov["era"]["name"]))
    if 0 < len(data["artwork"]):
        print("artwork titles:")
        for _art in data["artwork"]:
            print("\t {}".format(_art["title"]))

if __name__ == "__main__":
    try:
        db = initDb()
        surname, firstname = user_input_names()
        _artists = findArtists( db,"{fn} {sn}".format(sn=surname,fn=firstname) )
        data = list()
        for _artist in _artists:
            _artwork =  findArtistArtworks(db, _artist["fc"])
            data.append({'artist': _artist, 'artwork': _artwork})

        for _entry in data:
            print_artist_data(_entry)

    except ValueError as e:
        print("sorry there was a processing exception:", e)
