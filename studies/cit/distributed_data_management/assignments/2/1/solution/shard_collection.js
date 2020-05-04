//----------------------------------------------------
//
//  CREATE AND INITIATE THE REPLICA SETS AND SHARDS
//
//----------------------------------------------------
//
//------------------------------------------------
// 1. GLOBAL VARIABLES (I don't understand this)
//------------------------------------------------
//
db = db.getSisterDB("test");
var res = null;
//
//------------------------------------------------
// 2. ENABLING THE DATABASE FOR SHARDING
//------------------------------------------------
//
res = sh.enableSharding("test");
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Enable test for Sharding Failed. Trying it again");
        res = sh.enableSharding("test");
    }
}
print("test Enable for Sharding!");

//
//------------------------------------------------
// 3. CREATE INDEX FOR THE DATABASE
//------------------------------------------------
//
res = db.artists.createIndex({ "fc" : 1});
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Creating index for artists collection failed. Trying it again");
        res = db.artists.createIndex({ "fc" : 1 });
    }
}
res = db.artists.createIndex({ "birth.place.name" : 1});
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Creating index for artists collection failed. Trying it again");
        res = db.artists.createIndex({ "birth.place.name" : 1 });
    }
}
res = db.artists.createIndex({ "movements.name" : 1});
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Creating index for artists collection failed. Trying it again");
        res = db.artists.createIndex({ "movements.name" : 1 });
    }
}

print("artists Collection Index Created!");
//
//------------------------------------------------
// 4. SHARD THE COLLECTION
//------------------------------------------------
//
res = sh.shardCollection("test.artists", { "fc" : 1 } );
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Sharding artists collection failed. Trying it again");
        res = sh.shardCollection("test.artists",{ "fc" : 1 } );
    }
}
print("artists Collection Sharded!");
//
//------------------------------------------------
// 5. Print the status
//------------------------------------------------
//
for (i = 0; i < 20; i++) {
    sh.status();
    sleep(10000);
}

//
//------------------------------------------------
// 3. CREATE INDEX FOR THE DATABASE
//------------------------------------------------
//

res = db.artworks.createIndex({ "title": 1, "all_artists": 1 });
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Creating index for artworks collection failed. Trying it again");
        res = db.artworks.createIndex({ "title": 1, "all_artists": 1 });
    }
}
print("artworks Collection Index Created!");

res = db.artworks.createIndex({ "contributors.fc": 1 }, { partialFilterExpression: { "contributors.role" : "artist" } });
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Creating index for artworks collection failed. Trying it again");
        res = db.artworks.createIndex({ "contributors.fc": 1 }, { partialFilterExpression: { "contributors.role" : "artist" } });
    }
}
print("artworks Collection Index Created!");

//
//------------------------------------------------
// 4. SHARD THE COLLECTION
//------------------------------------------------
//
res = sh.shardCollection("test.artworks", { "title": 1, "all_artists": 1 } );
while (res.ok != 1) {
    sleep(10);
    if (res.ok != 1){
        print("Sharding artworks collection failed. Trying it again");
        res = sh.shardCollection("test.artworks", { "title": 1, "all_artists": 1 } );
    }
}
print("artworks Collection Sharded!");
//
//------------------------------------------------
// 5. Print the status
//------------------------------------------------
//
for (i = 0; i < 20; i++) {
    sh.status();
    sleep(10000);
}
//
//------------------------------------------------
// 7. QUIT
//------------------------------------------------
//
quit()


