setwd("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
source("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3/assignment3.R")


FILE <- "newSpamFile.csv"

# read file, apply naming (classification,text) and translate classiication to 1/0
data.raw<-prepareData(FILE)

#create corpus of data and clean it, we'll end up with collection of documents, from the texts
data.corpus <- create_corpus(data.raw$text)
# create a documnet term matrix with the term frequencies 
data.dtm = DocumentTermMatrix(data.corpus)

training_set_factor <- 0.75
set_len <- nrow(data.raw)
training_set_len <- round(training_set_factor * set_len)
testing_set_start_index <- training_set_len + 1

# split the raw data:
data.raw.train = data.raw[1:training_set_len, ]
data.raw.test  = data.raw[testing_set_start_index:set_len, ] # the rest

# then split the document-term matrix
data.dtm.train = data.dtm[1:training_set_len, ]
data.dtm.test  = data.dtm[testing_set_start_index:set_len, ]

# and finally the corpus
data.corpus.train = data.corpus[1:training_set_len]
data.corpus.test  = data.corpus[testing_set_start_index:set_len]

# let's shrink the doc term matrix, eliminating words which appear in less than 5 texts
freq_terms = findFreqTerms(data.dtm.train, 5)
data.dtm.train.reduced = DocumentTermMatrix(data.corpus.train, list(dictionary=freq_terms))
data.dtm.test.reduced = DocumentTermMatrix(data.corpus.test, list(dictionary=freq_terms))


# Convert to a data.frame for training and assign a classification (factor) to each document.
data.train <- as.matrix(data.dtm.train.reduced)
data.train <- cbind(data.train, data.raw.train[,1])
colnames(data.train)[ncol(data.train)] <- 'classification'
data.train <- as.data.frame(data.train)
data.train$classification <- as.factor(data.train$classification)

# Train.
classifier_model <- train(classification ~ ., data = data.train, method = 'bayesglm')
data.raw.test.predicted = predict(classifier_model, data.dtm.test.reduced)
outcome<-CrossTable(data.raw.test.predicted,
                    data.raw.test$classification,
                    prop.chisq = FALSE, # as before
                    prop.t     = FALSE, # eliminate cell proprtions
                    dnn        = c("predicted", "actual")) # relabels rows+cols

totalCases <- length(data.raw.test$classification)
falsePositives<-outcome$t[1,2]
falseNegatives<-outcome$t[2,1]

print(paste("total cases    : ", totalCases))
print(paste("false positives: ", falsePositives))
print(paste("false negatives: ", falseNegatives))

