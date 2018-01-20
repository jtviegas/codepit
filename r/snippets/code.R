
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



classification_model_bayes_glm <- function(data, scores){
  
  #https://gist.github.com/primaryobjects/094d24084d1045c011b7
  #install_if_missing("caret")
  #library(caret)
  install_if_missing("tm")
  library(tm)
  
  # Training data.
  corpus <- VCorpus(VectorSource(data))
  # Create a document term matrix.
  tdm <- DocumentTermMatrix(corpus, list(removePunctuation = TRUE, stopwords = TRUE, stemming = TRUE, removeNumbers = TRUE))
  train <- as.matrix(tdm)
  train <- cbind(train, scores)
  colnames(train)[ncol(train)] <- 'classification'
  train <- as.data.frame(train)
  train$classification <- as.factor(train$classification)
  
  # Train.
  model <- train(classification ~ ., data = train, method = 'bayesglm')
  
  return(model)

  
}


