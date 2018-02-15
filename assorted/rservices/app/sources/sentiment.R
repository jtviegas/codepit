
# ----- includes
source('commons.R')
#------------ LIBS
install_if_missing("syuzhet")
library(syuzhet)

process <- function(msg){
  sentiment <- get_nrc_sentiment(msg$fields[[text]])
  score <- sentiment$positive - sentiment$negative
  msg$fields['sentiment'] <- score
  return(msg)
}



