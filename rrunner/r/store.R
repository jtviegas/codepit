SCRIPTS_DIR <- Sys.getenv("R_SCRIPTS_DIR")
if( 0 == nchar(SCRIPTS_DIR) ){
  stop('!!! have to provide scripts folder in env !!! ...leaving.')
}
# ----- includes
source(paste(SCRIPTS_DIR,'commons.R',sep='/'))
# ----- LIBS
install_if_missing("mongolite")
library(mongolite)

# ----- const vars
DB_HOST <- 'mongo'
DB_PORT <- '27017'
DB_NAME <- 'local'

# ----- local vars
collectionObj <- NULL
connectionString <- paste(paste(paste('mongodb://', DB_HOST, sep=''), DB_PORT, sep=':'), DB_NAME, sep='/')

getCollection <- function(name){
  if( is.null(collectionObj) ){
    tryCatch({
      print(paste('...going to get collection', name, sep=' '))
      collectionObj <- mongo(collection=name, url = connectionString)
      print('...got collection successfully.')
    }, error = function(e) {
      print(paste('failed to get collection:', e, sep=' '))
      collectionObj <- NULL
      stop(e)
    })
  }
  return(collectionObj)
}

store <- function(obj, collectionName){
  colObj <- getCollection(collectionName)
  colObj$insert(obj)
}
