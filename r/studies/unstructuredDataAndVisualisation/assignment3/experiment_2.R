setwd("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
source("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3/assignment3.R")

print("==============================================================")
print("experiment: Naive Bayes with 75% test data partition and word stemming")

FILE <- "newSpamFile.csv"

# read file, apply naming (classification,text) and translate classiication to 1/0
data.raw<-prepareData(FILE)

# # check the ratio of classification
classification_factors <- prop.table(table(data.raw$classification))*100
print(classification_factors)

#create corpus of data and clean it, we'll end up with collection of documents, from the texts
data.corpus <- create_corpus(data.raw$text, stemming = TRUE)
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

# check the ratio of classification
round(prop.table(table(data.raw$classification))*100)
round(prop.table(table(data.raw.train$classification))*100)
round(prop.table(table(data.raw.test$classification))*100)

# let's shrink the doc term matrix, eliminating words which appear in less than 5 texts
freq_terms = findFreqTerms(data.dtm.train, 5)
data.dtm.train.reduced = DocumentTermMatrix(data.corpus.train, list(dictionary=freq_terms))
data.dtm.test.reduced = DocumentTermMatrix(data.corpus.test, list(dictionary=freq_terms))

# check how many terms we ended up with
ncol(data.dtm.train)
ncol(data.dtm.train.reduced)

# simplify term count to binary
data.dtm.train.reduced <- binarize_dtm_counts(data.dtm.train.reduced)
data.dtm.test.reduced <- binarize_dtm_counts(data.dtm.test.reduced)



classifier_model = naiveBayes(data.dtm.train.reduced, data.raw.train$classification)
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

print("==============================================================")
print("experiment: Naive Bayes with 75% test data partition, word stemming and with Laplace smoothing")

classifier_model = naiveBayes(data.dtm.train.reduced, data.raw.train$classification, laplace = 1)
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
