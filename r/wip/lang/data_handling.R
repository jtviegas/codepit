install.packages("devtools")
library(devtools)
install_github("jtviegas/myrpack")
library(myrpack)
ls("package:myrpack")
install_if_missing("tidyverse")
library(tidyverse)
install_if_missing("readxl")
library(readxl)
install_if_missing("here")
library(here)

setwd("~/Documents/github/R/wip/lang")

## importing from excel
data <- read_excel("~/Documents/github/R/wip/data/data1.xlsx")

## importing from csv
data <- read_csv(here("wip/data/data1.csv"))

# apply column names on read - adds columns row and removes the existing one
data <- read_csv(here("wip/data/data1.csv"), col_names = c("a", "b", "c", "d","e", "f"), skip=1)

# replacing missing values - all values with "?" are now replaced by NA
data <- read_csv(here("wip/data/data1.csv"), na="?")
head(data)
class(data)



