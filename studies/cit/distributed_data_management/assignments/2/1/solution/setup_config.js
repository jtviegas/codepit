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
var res = null;
//
// 1. CONNECT TO THE PROPER mongod PROCESS
//
db = connect(myIP+":36050/test");
//
// 2. INITIATE THE REPLICA SET
//
res = rs.initiate(
    {
        "_id" : "cfg",
        configsvr: true,
        "members" : [
            { _id:0,host:"127.0.0.1:36050" },
            { _id:1,host:"127.0.0.1:36051" },
            { _id:2,host:"127.0.0.1:36052" }
        ]
    }
);

//
// 3. WAIT UNTIL ALL THE NODES OF THE REPLICA SET ARE UP AND RUNNING
//
while (res.ok != 1){
    sleep(10);
}
print("Config Replica Set Created!");
while (((rs.status().members[0].state != 1) && (rs.status().members[0].state != 2)) || ((rs.status().members[1].state != 1) && (rs.status().members[1].state != 2)) || ((rs.status().members[2].state != 1) && (rs.status().members[2].state != 2))) {
    sleep(10);
}
print("Config Replica Set Up!");

// Sleep for a bit to make sure all is set up
sleep(30)
quit()
