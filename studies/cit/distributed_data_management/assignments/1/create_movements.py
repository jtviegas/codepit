from bson.code import Code

def create_movements(db):
    mapper = Code("""
        function () {
            this.movements.forEach(function(z) {
                          emit(z, 1);
                        });
        }
     """)
    reducer = Code("""
                   function (key, values) {
                      var total = 0;
                      for (var i = 0; i < values.length; i++) {
                        total += values[i];
                      }
                      return total;
                    }
                   """)
    result = {}
    cursor = db.artists.map_reduce(mapper, reducer, "myresults")
    for _doc in cursor.find():
        result[str(_doc["_id"]["name"])] = _doc["_id"]
    return result

