install.packages("devtools")
library(devtools)
install_github("jtviegas/myrpack")
library(myrpack)
ls("package:myrpack")
install_if_missing("tidyverse")
library(tidyverse)
install_if_missing("ggplot2")
library(ggplot2)

loadCsv2 <- function(sUrl){
  x <- read_csv2(sUrl)
  return (x)
}

loadCsv <- function(sUrl){
  x <- read_csv(sUrl)
  return (x)
}

# Rstudio tweak in windows
# trace(utils:::unpackPkgZip, edit=TRUE)
# I edit line 140 (line 142 in R 3.4.4):
#  Sys.sleep(0.5)
# to:
#  Sys.sleep(2)