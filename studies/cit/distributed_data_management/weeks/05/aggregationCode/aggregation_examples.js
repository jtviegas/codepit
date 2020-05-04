//---------------------------------
//
//  0. Setup the collection
//
//---------------------------------
use practise
db.dropDatabase()
use practise
db.c.insert([
    {
		"identifier": {
			"name": "Luis",
			"CIT_id": 1234
		},
		"eyes": "Brown",
		"city": "Madrid",
		"likes": [{
			"sport": "Football",
			"score": 10
		}, {
			"sport": "Basketball",
			"score": 6
		}, {
			"sport": "Tennis",
			"score": 5
		}]
	}, {
		"identifier": {
			"name": "John",
			"CIT_id": 5678
		},
		"eyes": "Blue",
		"city": "Paris",
		"likes": [{
			"sport": "Football",
			"score": 8
		}, {
			"sport": "Basketball",
			"score": 4
		}]
	}, {
		"identifier": {
			"name": "Mary",
			"CIT_id": 9012
		},
		"eyes": "Blue",
		"city": "London",
		"likes": [{
			"sport": "Tennis",
			"score": 8
		}, {
			"sport": "Basketball",
			"score": 7
		}]
	}, {
		"identifier": {
			"name": "Patrick",
			"CIT_id": 3456
		},
		"eyes": "Green",
		"city": "Paris",
		"likes": [{
			"sport": "Tennis",
			"score": 1
		}]
	}, {
		"identifier": {
			"name": "Susan",
			"CIT_id": 7890
		},
		"eyes": "Green",
		"city": "Dublin",
		"likes": [{
			"sport": "Rugby",
			"score": 10
		}]
	}
])
//---------------------------------
//
//  1. Match the documents
//
//---------------------------------
//
//---------------------------------
// 1.1. With a single condition
//---------------------------------
db.c.aggregate([
  { $match : { eyes : "Blue" } }
])
//
//---------------------------------
// 1.2. With multiple conditions
//---------------------------------
//
db.c.aggregate([
  { $match : { eyes : "Blue", city : "Paris" } }
])
//
//---------------------------------
// 1.3. It can return no documents at all
//---------------------------------
//
db.c.aggregate([
  { $match : { eyes : "Blue", city : "Madrid" } }
])
//---------------------------------
//
//  2. Group the documents
//
//---------------------------------
//
//---------------------------------
// 2.1. Grouping by a single field
//---------------------------------
//
db.c.aggregate([
  { $group : { _id : "$eyes" } }
])
//
//---------------------------------
// 2.2. Grouping by a single field and add something to the resulting document
//---------------------------------
//
db.c.aggregate([
  { $group : { _id : "$eyes", total : { $sum : 1 } } }
])
//
//---------------------------------
// 2.3. Grouping by several fields (and add something to the resulting document)
//---------------------------------
//
db.c.aggregate([
  { $group : { _id : {"col" : "$eyes", "cit" : "$city"}, total : { $sum : 1 } } }
])
//---------------------------------
//
//  3. Sort the documents
//
//---------------------------------
//
//---------------------------------
// 3.1. Sort based on a single field
//---------------------------------
//
db.c.aggregate([
  { $sort : { eyes : 1 } }
])
//
//---------------------------------
// 3.2. Sort based on several fields
//---------------------------------
//
db.c.aggregate([
  { $sort : { eyes : -1, city : 1 } }
])
//---------------------------------
//
//  4. Limit to a number of documents
//
//---------------------------------
//
//---------------------------------
// 4.1. Limit the collection to a maximum number of documents
//---------------------------------
//
db.c.aggregate([
  { $limit : 3 }
])
//---------------------------------
//
//  5. Count number of documents
//
//---------------------------------
//
//---------------------------------
// 5.1. This is not properly an aggregation command
//---------------------------------
//
db.c.count()
//
//---------------------------------
// 5.2. Counting the elements with an aggregation command
//---------------------------------
//
db.c.aggregate([
  { $group : { _id : null, count : { $sum : 1 } } }
])
//---------------------------------
//
//  6. Project the documents
//
//---------------------------------
//
//---------------------------------
// 6.1. Modify the document to contain only some fields
//---------------------------------
//
db.c.aggregate([
  { $project : { eyes : 1, city : 1 } }
])
//
//---------------------------------
// 6.2. To not to show the ObjectId field you have to explicitly state it
//---------------------------------
//
db.c.aggregate([
  { $project : { _id : 0, eyes : 1, city : 1 } }
])
//
//---------------------------------
// 6.3. To add fields belonging to a subfield (as a subfield)
//---------------------------------
//
db.c.aggregate([
  { $project : { _id : 0, eyes : 1, city : 1, "identifier.name" : 1 } }
])
//
//---------------------------------
// 6.4. To add fields belonging to a subfield (as a normal field)
//---------------------------------
//
db.c.aggregate([
  { $project : { _id : 0, eyes : 1, city : 1, my_new_field : "$identifier.name" } }
])
//
//---------------------------------
// 6.5. To add new fields
//---------------------------------
//
db.c.aggregate([
  { $project : { _id : 0, eyes : 1, city : 1, sports_I_like : { $size : "$likes" } } }
])
//---------------------------------
//
//  7. Size of a list
//
//---------------------------------
//
//---------------------------------
// 7.1. To compute the size of a list
//---------------------------------
//
db.c.aggregate([
  { $project : { _id : 0, eyes : 1, city : 1, sports_I_like : { $size : "$likes" } } }
])
//---------------------------------
//
//  8. Unwind a list
//
//---------------------------------
//
//---------------------------------
// 8.1. To unfold the content of a list
//---------------------------------
//
db.c.aggregate([
  { $unwind : "$likes" }
])
//---------------------------------
//
//  9. Aggregation Pipeline
//
//---------------------------------
//
//---------------------------------
// 9.1. Get the sport with higher number of points
//---------------------------------
//
db.c.aggregate([
  { $unwind : "$likes" },
  { $project : { _id : 0, sport : "$likes.sport", points : "$likes.score" } },
  { $group : { _id : "$sport", points : { $sum : "$points" } } },
  { $sort : { points : -1 } },
  { $limit : 1 }
])






