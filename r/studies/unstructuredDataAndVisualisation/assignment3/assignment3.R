#posteriorprob=likelihood∗prior/marginallikelihood
#P(Spam|Viagra)=P(Viagra|Spam)∗P(Spam)/P(Viagra)

source("/home/jtviegas/Documents/workspace/codepit/r/snippets/code.R")

FILE <- "SMSSpamCollection.csv"

d0 <- readCsv(FILE)

prop.table(table(d0$ham))*100
