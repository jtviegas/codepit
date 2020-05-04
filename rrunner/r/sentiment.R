SCRIPTS_DIR <- Sys.getenv("R_SCRIPTS_DIR")
if( 0 == nchar(SCRIPTS_DIR) ){
  stop('!!! have to provide scripts folder in env !!! ...leaving.')
}
# ----- includes
source(paste(SCRIPTS_DIR,'commons.R',sep='/'))
#------------ LIBS
install_if_missing("syuzhet")
library(syuzhet)

getSentiment <- function(message){
  sentiment <- get_nrc_sentiment(message)
  score <- sentiment$positive - sentiment$negative
  return(score)
}



