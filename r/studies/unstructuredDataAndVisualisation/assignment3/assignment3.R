#posteriorprob=likelihood∗prior/marginallikelihood
#P(Spam|Viagra)=P(Viagra|Spam)∗P(Spam)/P(Viagra)
# https://rpubs.com/mzc/mlwr_nb_sms_spam

setwd("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
source("H:/Documents/R00157699/workspace/codepit/r/snippets/code.R")

install_if_missing("tm")
library(tm)
install_if_missing("wordcloud")
library(wordcloud)
install_if_missing("e1071")
library(e1071)
install_if_missing("gmodels")
library(gmodels)
install_if_missing("tidytext")
library(tidytext)
install_if_missing("dplyr")
library(dplyr)
install_if_missing("caret")
library(caret)
install_if_missing("arm")
library(arm)
install_if_missing("SnowballC")
library(SnowballC)
install_if_missing("RTextTools")
library(RTextTools)
install_if_missing("abind")
library(abind)
install_if_missing("automap")
library(automap)
install_if_missing("BayesTree")
library(BayesTree)
install_if_missing("bipartite")
library(bipartite)
install_if_missing("bitops")
library(bitops)
install_if_missing("boot")
library(boot)
install_if_missing("Boruta")
library(Boruta)
install_if_missing("BradleyTerry2")
library(BradleyTerry2)

### --- classification -- ###

translate_classification <- function(x){
  x = ifelse( x == "spam", 1, 0)
  x = factor(x, levels = c(0, 1), labels=c("No", "Yes"))
  return (x)
}

# read file, apply naming (classification,text) and translate classiication to 1/0
prepareData <- function(file){
  d <- readCsv(file, with_header=FALSE)
  #let's apply column naming
  names(d) <- c("classification","text")
  d$classification = factor(d$classification)
  d <- data.frame(apply(d[1],2, translate_classification), d[2])
  return(d)
}

# create corpus of data and clean it, we'll end up with collection of documents, from the texts
create_corpus <- function(v, stemming = FALSE){
  corpus = Corpus(VectorSource(v))
  corpus_clean = tm_map(corpus, tolower)                    # convert to lower case
  corpus_clean = tm_map(corpus_clean, removeNumbers)            # remove digits
  corpus_clean = tm_map(corpus_clean, removeWords, stopwords()) # and but or you etc
  corpus_clean = tm_map(corpus_clean, removePunctuation)        # you'll never guess!
  corpus_clean = tm_map(corpus_clean, stripWhitespace)          # reduces w/s to 1
  if(TRUE == stemming){
    corpus_clean <- tm_map(corpus_clean, stemDocument, language = "english")
  }
  
  return(corpus_clean)
}

convert_counts = function(x) {
  x = ifelse(x > 0, 1, 0)
  x = factor(x, levels = c(0, 1), labels=c("No", "Yes"))
  return (x)
}

# apply() allows us to work either with rows or columns of a matrix.
# MARGIN = 1 is for rows, and 2 for columns
binarize_dtm_counts <- function(dtm){
  r <- apply(dtm, MARGIN=2, convert_counts)
  return(r)
}


classification_model_bayes_glm <- function(d, scores){
  
  #https://gist.github.com/primaryobjects/094d24084d1045c011b7
  tdm <- convert_vector_to_dtm_2(d)
  
  train <- as.matrix(tdm)
  train <- cbind(train, scores)
  colnames(train)[ncol(train)] <- 'classification'
  train <- as.data.frame(train)
  train$classification <- as.factor(train$classification)
  
  # Train.
  model <- train(classification ~ ., data = train, method = 'bayesglm')
  
  return(model)
  
  
}

