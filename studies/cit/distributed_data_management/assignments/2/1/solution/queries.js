let r=null


// UNWIND
// trying to find a list with a relationship artist<->movement to be able to group it later more easily
r = db.artists.aggregate([ 
    { $unwind : "$movements" }
    , { $project : { _id : 0, artist: "$fc", movement: "$movements.name" } }
    
])
printjson(r._batch)
printjson(r._batch.length)

// 

// PROJECT
// list of paintings with artist unequivocally known (with role artists), showing date, title and artist
r = db.artworks.aggregate([ 
    { $unwind : "$contributors" }
    ,{ $match: { "contributors.role": "artist"}  }
    , { $project : { _id : 0, date: "$dateText", title: "$title", artist : "$contributors.fc" } }
])
printjson(r._batch)
printjson(r._batch.length)


// GROUP
// grouping count between art classification and medium
r = db.artworks.aggregate([ 
    { $group: { _id: {"classification": "$classification", "medium": "$medium"}, count:{$sum: 1} } }
])
printjson(r._batch)
printjson(r._batch.length)

// SORT
// sort the gallery acquisitions by descendent order
r = db.artworks.aggregate([ 
	{ $sort : { "acquisitionYear" : -1} }
])
printjson(r._batch)
printjson(r._batch.length)

// LIMIT
// list the 3 most recent art works acquired by the gallery
r = db.artworks.aggregate([ 
	{ $sort : { "acquisitionYear" : -1} }
	, { $project : { _id : 0, aquisitionYear: "$acquisitionYear", title: "$title", artists : "$all_artists" } }
	, { $limit : 3 }
])
printjson(r._batch)
printjson(r._batch.length)

// PIPELINE
// the artist that in his career was part of more artistic movements
r = db.artists.aggregate([ 
    { $unwind : "$movements" }
    , { $project : { _id : 0, artist: "$fc", movement: "$movements.name" } }
    , { $group: { _id: {"artist": "$artist"}, movement:{$sum: 1} } }
    , { $sort : { "movement" : -1} }
    , { $limit : 1 }
])
printjson(r._batch)
printjson(r._batch.length)

//
//------------------------------------------------
// 7. QUIT
//------------------------------------------------                  
//
quit();

