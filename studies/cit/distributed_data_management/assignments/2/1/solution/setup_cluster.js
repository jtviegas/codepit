//----------------------------------------------------
//
//  CREATE AND INITIATE THE REPLICA SETS AND SHARDS
//
//----------------------------------------------------
//
//------------------------------------------------
// 0. Set myIP to the name of the computer or leave as is
//------------------------------------------------
//
var myIP = "127.0.0.1";
//
//------------------------------------------------
// 1. GLOBAL VARIABLES 
//------------------------------------------------
//
db = db.getSisterDB("config");
var mongosConn = db;
var res = null;
//
//------------------------------------------------
// 2. SET THE CHUNK SIZE
//------------------------------------------------
//
mongosConn.settings.save( { _id:"chunksize", value: 1 } )
//
//------------------------------------------------
// 3. LONDON
//------------------------------------------------
//
// 1. CONNECT TO THE PROPER mongod PROCESS
//
db = connect(myIP+":37000/test");
//
// 2. INITIATE THE REPLICA SET
//
res = rs.initiate(
    {
        "_id" : "london",
        "members" : [
            { _id:0,host:myIP+":"+"37000" },
            { _id:1,host:myIP+":"+"37001" },
            { _id:2,host:myIP+":"+"37002" }
        ]
    }
);
//
// 3. WAIT UNTIL ALL THE NODES OF THE REPLICA SET ARE UP AND RUNNING
//
while (res.ok != 1){
    sleep(10);
}
print("London Replica Set Created!");
while (((rs.status().members[0].state != 1) && (rs.status().members[0].state != 2)) || ((rs.status().members[1].state != 1) && (rs.status().members[1].state != 2)) || ((rs.status().members[2].state != 1) && (rs.status().members[2].state != 2))) {
    sleep(10);
}
print("London Replica Set Up!");
//
// 4. ADD THE SHARD
//
db = mongosConn;
res = sh.addShard("london/"+myIP+":37000");
while (res.ok != 1){
    sleep(60);
    if (res.ok != 1){
        print("Adding Shard Failed. Trying it again");
        res = sh.addShard("london/"+myIP+":37000");
    }
}
print("London Shard Added!");
//
//------------------------------------------------
// 4. AMSTERDAM
//------------------------------------------------
//
// 1. CONNECT TO THE PROPER mongod PROCESS
//
db = connect(myIP+":37100/test");
//
// 2. INITIATE THE REPLICA SET
//
res = rs.initiate(
    {
        "_id" : "amsterdam",
        "members" : [
            { _id:0,host:myIP+":"+"37100" },
            { _id:1,host:myIP+":"+"37101" },
            { _id:2,host:myIP+":"+"37102" }
        ]
    }
);
//
// 3. WAIT UNTIL ALL THE NODES OF THE REPLICA SET ARE UP AND RUNNING
//
while (res.ok != 1){
    sleep(10);
}
print("Amsterdam Replica Set Created!");
while (((rs.status().members[0].state != 1) && (rs.status().members[0].state != 2)) || ((rs.status().members[1].state != 1) && (rs.status().members[1].state != 2)) || ((rs.status().members[2].state != 1) && (rs.status().members[2].state != 2))) {
    sleep(10);
}
print("Amsterdam Replica Set Up!");
//
// 4. ADD THE SHARD
//
db = mongosConn;
res = sh.addShard("amsterdam/"+myIP+":37100");
while (res.ok != 1){
    sleep(60);
    if (res.ok != 1){
        print("Adding Shard Failed. Trying it again");
        res = sh.addShard("amsterdam/"+myIP+":37100");
    }
}
print("Amsterdam Shard Added!");
//
//------------------------------------------------
// 5. NEW YORK
//------------------------------------------------
//
// 1. CONNECT TO THE PROPER mongod PROCESS
//
db = connect(myIP+":37200/test");
//
// 2. INITIATE THE REPLICA SET
//
res = rs.initiate(
    {
        "_id" : "newyork",
        "members" : [
            { _id:0,host:myIP+":"+"37200" },
            { _id:1,host:myIP+":"+"37201" },
            { _id:2,host:myIP+":"+"37202" },
            { _id:3,host:myIP+":"+"37203" }
        ]
    }
);
//
// 3. WAIT UNTIL ALL THE NODES OF THE REPLICA SET ARE UP AND RUNNING
//
while (res.ok != 1){
    sleep(10);
}
print("New York Replica Set Created!");
while (((rs.status().members[0].state != 1) && (rs.status().members[0].state != 2)) || ((rs.status().members[1].state != 1) && (rs.status().members[1].state != 2)) || ((rs.status().members[2].state != 1) && (rs.status().members[2].state != 2)) || ((rs.status().members[3].state != 1) && (rs.status().members[3].state != 2)) ) {
    sleep(10);
}
print("New York Replica Set Up!");
//
// 4. ADD THE SHARD
//
db = mongosConn;
res = sh.addShard("newyork/"+myIP+":37200");
while (res.ok != 1){
    sleep(60);
    if (res.ok != 1){
        print("Adding Shard Failed. Trying it again");
        res = sh.addShard("newyork/"+myIP+":37200");
    }
}
print("New York Shard Added!");

//
//------------------------------------------------
// 7. QUIT
//------------------------------------------------
//
quit()
