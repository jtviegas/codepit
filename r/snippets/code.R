### --- utils --- ###
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
  if (p %in% rownames(installed.packages()) == FALSE) {
    install.packages(p, dependencies = TRUE)
  }
  else {
    cat(paste("Skipping already installed package:", p, "\n"))
  }
}

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



