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

# replacing missing values - all values with "?" are now replaced by NA
data <- read_csv(here("wip/data/data1.csv"), na="?")
head(data)

## tibbles - two-dimensional object with rows and columns
class(data)
as_tibble(data)
as.data.frame(data)

x <- c(1,2,3,4,5,6)
y <- c(7,8,9,10,11,12)
z <- c(1,3,5,7,11,65)

data <- tibble(x,y,z)
head(data)
print(data[1,])

## operations on data
mean(data$x)
median(data$z)
sd(data$z)
with(data, range(y))
summary(data$z)
summary(data)

# subsetting dataframe/tibble

id <- c(1,2,3,4,5,6)
type <- c("a", "a", "c", "a", "b", "c")
temp <- c(24,67,91,56,72,80)
humidity <- c(64,37,11,86,89,90)

data <- data.frame(id, type, temp, humidity)

# subset of rows
newdata <- subset(data, data$temp > 70)
newdata <- data[data$temp>70,]
# subset of columns
newdata <- subset(data, select=c(id,temp))
newdata <- data[,1:2]

# excluding data
# removing the first variable
data[,-1]
# removing first 3 lines
data[-1:-3,]
# using by function - summarize data on basis of a categorical variable
by(data[,3:4],data$type, colMeans, na.rm = T)

# sorting and ranking

temp_sorted <- sort(data$temp)
temp_sorted_rev <- rev(sort(data$temp))
temp_ranks <- rank(data$temp)

sorted <- data.frame(temp_sorted)
sorted_rev <- data.frame(temp_sorted_rev)
ranks <- data.frame(temp_ranks)

print(cbind(data,sorted, sorted_rev, ranks))

### --- tidy data

table1
table4a
#...translating columns to variables
tidya <- table4a %>% gather("1999","2000",key="year", value="cases")

table4b
tidyb <- table4b %>% gather("1999","2000", key="year", value="population")

#..tidy up everything in one table
left_join(tidya, tidyb)

#...spreading tables
table2
spread(table2, key=type, value = count)







