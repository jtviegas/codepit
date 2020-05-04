#!/usr/bin/python3.7

import pprint
import re
from pymongo import MongoClient
from bson.code import Code

from create_movements import create_movements

def init_db():
    client = MongoClient()
    db = client.get_database('tate')
    return db
def user_input():
    _input = input("Enter artist name: ")
    _names = _input.split(',')
    if len(_names) != 2:
        raise ValueError('should provide two comma separated names!')
    _input = input("Enter movement title: ")
    _movement = _input.strip()

    result = list()
    for _name in _names:
        result.append(_name.strip())
    result.append(_movement)

    return result
def find_artist(db, name):
    result = None
    cursor = db['artists'].find({'fc': name})
    try:
        result = cursor[0]
    except IndexError:
        print("no artist with that name, unfortunately")

    return result

if __name__ == "__main__":
    try:
        db = init_db()
        movements = create_movements(db)
        surname, firstname, movement = user_input()
        _actual_artist = find_artist( db,"{fn} {sn}".format(sn=surname,fn=firstname) )
        if _actual_artist is None:
            raise ValueError('unknown artist!')
        _actual_movement = movements.get(movement)
        if _actual_movement is None:
            raise ValueError('unknown movement!')

        _artist_movements = _actual_artist['movements']
        if _actual_movement in _artist_movements:
            print("movement already part of artist")
        else:
            _query = { "id":  _actual_artist["id"] }
            _artist_movements.append(_actual_movement)
            _new = { "$set": { "movements": _artist_movements } }
            db['artists'].update_one(_query, _new)
            print("added movement to artist")

    except ValueError as e:
        print("sorry there was a processing exception:", e)
