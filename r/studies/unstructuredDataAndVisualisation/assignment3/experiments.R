#source("/home/jtviegas/Documents/workspace/codepit/r/snippets/code.R")
source("H:/Documents/R00157699/workspace/codepit/r/snippets/code.R")




training_set_factor <- 0.75

FILE <- "newSpamFile.csv"
data <- readCsv(FILE, with_header=FALSE)
#let's apply some column naming
data(sms) <- c("type","text")
data$type = factor(data$type)
data <- data.frame(apply(data[1],2, translate_classification), data[2])

set_len <- nrow(data)
training_set_len <- round(training_set_factor * set_len)
testing_set_start_index <- training_set_len + 1

# split the raw data:
data.train = data[1:training_set_len, ]
data.test  = data[testing_set_start_index:set_len, ] # the rest

model <- classification_model_bayes_glm(data.train[,2], data.train[,1])


# factorize the ham column data
prop.table(table(sms$type))*100




