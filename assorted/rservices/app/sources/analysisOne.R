
# ----- includes
source('commons.R')
#------------ LIBS

process <- function(msg){
  close <- msg$fields['close']
  volume <- msg$fields['volume']
  msg$fields['analysisOne'] <- volume[[1]]*close[[1]]

  return(msg)
}



