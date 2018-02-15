#------------ LIBS
if(length(find.package("reader",quiet=TRUE))==0){
  install.packages("reader")
}
library(reader)

# ----- CONSTANTS
TMP_DIR <- '/tmp'
LOCK_FILE_EXT <- 'lock'

createLockFilePath <- function(name){
  result <- cat.path(TMP_DIR, paste(name, LOCK_FILE_EXT, sep="."),  must.exist = FALSE)
  return(result)
}
createLockFile <- function(name){
  lockFilePath <- createLockFilePath(name)
  if(! file.exists(lockFilePath))
    file.create(lockFilePath)
}

removeLockFile <- function(name){
  lockFilePath <- createLockFilePath(name)
  if(file.exists(lockFilePath))
    file.remove(lockFilePath)
}

existsLockFile <- function(name){
  lockFilePath <- createLockFilePath(name)
  return(file.exists(lockFilePath))
}

onStatus <- function(name){
  return(existsLockFile(name))
}

detach_package <- function(pkg, character.only = FALSE)
{
  if(!character.only)
  {
    pkg <- deparse(substitute(pkg))
  }
  search_item <- paste("package", pkg, sep = ":")
  while(search_item %in% search())
  {
    detach(search_item, unload = TRUE, character.only = TRUE)
  }
}

install_if_missing <- function(p) {
  if (length(find.package(p,quiet=TRUE))==0) {
    install.packages(p, dependencies = TRUE)
  }
  else {
    cat(paste("Skipping already installed package:", p, "\n"))
  }
}
