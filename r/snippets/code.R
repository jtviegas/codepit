
### --- IO --- ###

readCsv <- function(filepath, with_header=TRUE, separator=","){
  file_content <- read.csv(file=filepath, header=with_header, sep=separator, stringsAsFactors = FALSE)
  return(file_content)
}

### --- strings --- ###

hasTerm <- function(text, term){
  r <- isTRUE(grep(term, text) == 1)
  return(r)
}