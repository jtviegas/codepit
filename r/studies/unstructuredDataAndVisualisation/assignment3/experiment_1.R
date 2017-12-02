#setwd("~/Documents/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
setwd("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3")
#source("/home/jtviegas/Documents/workspace/codepit/r/snippets/code.R")
source("H:/Documents/R00157699/workspace/codepit/r/studies/unstructuredDataAndVisualisation/assignment3/assignment3.R")

print("==============================================================")
print("experiment: Naive Bayes")

FILE <- "newSpamFile.csv"

# read file, apply naming (classification,text) and translate classiication to 1/0
data.raw<-prepareData(FILE)

# # check the ratio of classification
classification_factors <- prop.table(table(data.raw$classification))*100
print(classification_factors)

#create corpus of data and clean it, we'll end up with collection of documents, from the texts
data.corpus <- create_corpus(data.raw$text)
# create a documnet term matrix with the term frequencies 
data.dtm = DocumentTermMatrix(data.corpus)

# split the raw data:
data.raw.train = data.raw[1:4200, ]
data.raw.test  = data.raw[4201:5574, ] # the rest

# then split the document-term matrix
data.dtm.train = data.dtm[1:4200, ]
data.dtm.test  = data.dtm[4201:5574, ]

# and finally the corpus
data.corpus.train = data.corpus[1:4200]
data.corpus.test  = data.corpus[4201:5574]

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
print("experiment: Naive Bayes with Laplace smoothing")

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

