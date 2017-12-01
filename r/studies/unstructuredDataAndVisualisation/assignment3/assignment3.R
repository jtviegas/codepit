#posteriorprob=likelihood∗prior/marginallikelihood
#P(Spam|Viagra)=P(Viagra|Spam)∗P(Spam)/P(Viagra)
# https://rpubs.com/mzc/mlwr_nb_sms_spam

setwd("~/Documents/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
source("/home/jtviegas/Documents/workspace/codepit/r/snippets/code.R")

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

FILE <- "SMSSpamCollection.csv"
FILE <- "newSpamFile.csv"

sms <- readCsv(FILE, with_header=FALSE)
#let's apply some column naming
names(sms) <- c("type","text")
str(sms)
sms$type = factor(sms$type)
# factorize the ham column data
prop.table(table(sms$type))*100

#create a corpus of documents from sms text
sms_corpus = Corpus(VectorSource(sms$text))
print(sms_corpus)
inspect(sms_corpus[1:3])

corpus_clean = tm_map(sms_corpus, tolower)                    # convert to lower case
corpus_clean = tm_map(corpus_clean, removeNumbers)            # remove digits
corpus_clean = tm_map(corpus_clean, removeWords, stopwords()) # and but or you etc
corpus_clean = tm_map(corpus_clean, removePunctuation)        # you'll never guess!
corpus_clean = tm_map(corpus_clean, stripWhitespace)          # reduces w/s to 1
inspect(corpus_clean[1:3])

# we'll tokenize the messages now
dtm = DocumentTermMatrix(corpus_clean)
print (dtm)

# split the raw data:
sms.train = sms[1:4200, ] # about 75%
sms.test  = sms[4201:5574, ] # the rest

# then split the document-term matrix
dtm.train = dtm[1:4200, ]
dtm.test  = dtm[4201:5574, ]

# and finally the corpus
corpus.train = corpus_clean[1:4200]
corpus.test  = corpus_clean[4201:5574]

# let's just assert that our split is reasonable: raw data should have about 87% ham
# in both training and test sets:
round(prop.table(table(sms$type))*100)
round(prop.table(table(sms.train$type))*100)
round(prop.table(table(sms.test$type))*100)


wordcloud(corpus.train,
          min.freq=40,          # 10% of num docs in corpus is rough standard
          random.order = FALSE) # biggest words are nearer the centre


spam = subset(sms.train, type == "spam")
ham  = subset(sms.train, type == "ham")

wordcloud(spam$text,
          max.words=40,     # look at the 40 most common words
          scale=c(3, 0, 5)) # adjust max and min font sizes for words shown

wordcloud(ham$text,
          max.words=40,     # look at the 40 most common words
          scale=c(3, 0, 5)) # adjust max and min font sizes for words shown

#So looks like the biggest words don’t overlap much between ham and spam 
# this suggests NB has a fighting chance.

#Creating indicator features for frequent words
#As shown earlier, DTMs have more than 7000 columns - that’s way too much, so let’s shrink it down: 
#  eliminate words which appear in less than 5 SMS messages (about 0.1%). 
#This should reduce the feature-set to a far more manageable number. 
#We’ll use tm’s findFreqTerms() function.
freq_terms = findFreqTerms(dtm.train, 5)
reduced_dtm.train = DocumentTermMatrix(corpus.train, list(dictionary=freq_terms))
reduced_dtm.test =  DocumentTermMatrix(corpus.test, list(dictionary=freq_terms))

# have we reduced the number of features?
ncol(reduced_dtm.train)
ncol(reduced_dtm.test)
# we did from 7912 to 1229

#Remember that NB works on factors, but our DTM only has numerics. 
# Let’s define a function which converts counts to yes/no factor, and apply it to our reduced matrices.
convert_counts = function(x) {
  x = ifelse(x > 0, 1, 0)
  x = factor(x, levels = c(0, 1), labels=c("No", "Yes"))
  return (x)
}

# apply() allows us to work either with rows or columns of a matrix.
# MARGIN = 1 is for rows, and 2 for columns
reduced_dtm.train = apply(reduced_dtm.train, MARGIN=2, convert_counts)
reduced_dtm.test  = apply(reduced_dtm.test, MARGIN=2, convert_counts)

# store our model in sms_classifier
# here we are using the reduced terms and its values and assigning them the type in hte model
sms_classifier = naiveBayes(reduced_dtm.train, sms.train$type)
sms_test.predicted = predict(sms_classifier, reduced_dtm.test)

CrossTable(sms_test.predicted,
           sms.test$type,
           prop.chisq = FALSE, # as before
           prop.t     = FALSE, # eliminate cell proprtions
           dnn        = c("predicted", "actual")) # relabels rows+cols


sms_classifier2 = naiveBayes(reduced_dtm.train,
                             sms.train$type,
                             laplace = 1)
sms_test.predicted2 = predict(sms_classifier2,
                              reduced_dtm.test)
CrossTable(sms_test.predicted2,
           sms.test$type,
           prop.chisq = FALSE, # as before
           prop.t     = FALSE, # eliminate cell proprtions
           dnn        = c("predicted", "actual")) # relabels rows+cols

sms_classifier3 = naiveBayes(reduced_dtm.train,
                             sms.train$type,
                             laplace = 2)
sms_test.predicted3 = predict(sms_classifier3,
                              reduced_dtm.test)
CrossTable(sms_test.predicted3,
           sms.test$type,
           prop.chisq = FALSE, # as before
           prop.t     = FALSE, # eliminate cell proprtions
           dnn        = c("predicted", "actual")) # relabels rows+cols



# Check accuracy on training.
predict(model, newdata = train)


# Convert to a data.frame for training and assign a classification (factor) to each document.
train <- as.matrix(tdm)
train <- cbind(train, c(0, 1))
colnames(train)[ncol(train)] <- 'y'
train <- as.data.frame(train)
train$y <- as.factor(train$y)

# Train.
fit <- train(y ~ ., data = train, method = 'bayesglm')

# Check accuracy on training.
predict(fit, newdata = train)

# Test data.
data2 <- c('Bats eat bugs.')
corpus <- VCorpus(VectorSource(data2))
tdm <- DocumentTermMatrix(corpus, control = list(dictionary = Terms(tdm), removePunctuation = TRUE, stopwords = TRUE, stemming = TRUE, removeNumbers = TRUE))
test <- as.matrix(tdm)

# Check accuracy on test.
predict(fit, newdata = test)


