source("assignment3.R")

print("==============================================================")
print("experiment: classification using Support Vector Machines (SVM)")

FILE <- "newSpamFile.csv"

# read file, apply naming (classification,text) and translate classification to 1/0
data.raw<-prepareData(FILE)

training_set_factor <- 0.75
set_len <- nrow(data.raw)
training_set_len <- round(training_set_factor * set_len)
testing_set_start_index <- training_set_len + 1
testing_set_len <- set_len - training_set_len

# split the raw data:
data.raw.train = data.raw[1:training_set_len, ]
data.raw.test  = data.raw[testing_set_start_index:set_len, ] # the rest

# Create the training document term matrix
dtm_training_matrix <- create_matrix(data.raw.train$text)

# Configure the training data
training_container <- create_container(dtm_training_matrix, data.raw.train$classification, trainSize=1:training_set_len, virgin=FALSE)

# train a SVM Model
classification_model <- train_model(training_container, "SVM", kernel="linear", cost=1)

#################################
### IMPORTANT ###
### ...ther is a bug in RTextTools affecting this calculation ( https://github.com/timjurka/RTextTools/issues/4 )
### a window will open here in order for the code to be fixed, in line 42 make the following change:
###from: 
#   if (attr(weighting, "Acronym") == "tf-idf")
### to:
#   if (attr(weighting, "acronym") == "tf-idf") 
trace("create_matrix", edit=T)
#################################

# create a prediction document term matrix taking the dictionnary from the training matrix
dtm_testing_matrix <- create_matrix(data.raw.test$text, originalMatrix=dtm_training_matrix)

# create the corresponding container
testing_container <- create_container(dtm_testing_matrix, labels=rep(0,testing_set_len), testSize=1:testing_set_len, virgin=FALSE)

# predict
data.raw.test.predicted <- classify_model(testing_container, classification_model)

outcome<-CrossTable(data.raw.test.predicted$SVM_LABEL,
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

