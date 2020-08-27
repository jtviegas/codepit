import pymongo
from pymongo import MongoClient

client = MongoClient() # This assumes a default host of localhost and the default port of 27017

db = client.test

collection = db.data8005

# Now let's find the Marie Curie document
marie_curie = collection.find_one({"name" : "Marie Curie"})

print(marie_curie)

# Comment out the code below after you first run the code
collection.update_one({"name":"Marie Curie"}, {"$set": {"known_for":"radioactivity,polonium,radium"}})
marie_curie = collection.find_one({"name" : "Marie Curie"})
print(marie_curie)

print(marie_curie["name"])

print(collection.count_documents({"$expr":{"$gt":[{"$strLenCP":"$name"},5]}}))
print(collection.count_documents({"$expr":{"$gte":[{"$strLenCP":"$name"},12]}}))


mapper = Code("""
   function(){
        this.contributors.forEach(
            function(o){
                if( o.role === "artist" ){
                    emit(o.id, this._id);
                }
            }
        );
   }    
""")