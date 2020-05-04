####################################################################
#               STAT9004 Exercise Sheet 1 Solutions
#                   
####################################################################
library(tidyverse)
library(here)
library(VIM)
library(nycflights13)
##########################
# Q1 #

#   Find all flights that:
#a.	Had an arrival delay of two or more hours
Q1a <-filter(flights, dep_delay >= 2)

#b.	Flew to Houston (IAH or HOU)
Q2b <-filter(flights, dest=="IAH" | dest== "HOU")

#c.	Departed in summer (July, August, and September)
Q2c <- filter(flights, month == 7 | month == 8 | month ==9)
Q2c <- filter(flights, between(month, 7, 9))

#d.	Were delayed by at least an hour, but made up over 30 minutes in flight
Q2d <-mutate(flights, made_up = dep_delay - arr_delay) %>%
filter(dep_delay >= 60 & made_up >30)
#e.	Departed between midnight and 6am (inclusive) 
Q2e <-filter(flights, between(dep_time, 0, 600) | dep_time == 2400)

##########################
# Q2 # 

#	How many flights have a missing dep_time? 
# What other variables are missing? 
# We find that the data set containing observations where dep_time is 
# missing has length 8255. We see that oobservations missing the dep_time 
# are also missing arr_time, dep_delay, arr_delay and air_time
Q2 <-filter(flights, is.na(dep_time))

##########################
# Q3 #

#  Convert dep_time and sched_dep_time to represent the number of minutes
# since midnight.

# the floor function will round down to the nearest integer
Q3<-mutate(flights,
       dep_time = 60 * floor(dep_time/100) + (dep_time - floor(dep_time/100) * 100),
       sched_dep_time = 60 * floor(sched_dep_time/100) + (sched_dep_time - floor(sched_dep_time/100) * 100))

##########################
# Q4 # 	

# Find the 10 most delayed flights using a ranking function. 
# the most delayed flights have the largest values for dep_delay
# the rank() function ranks from smallest to largest (smallest number gets rank 1)
# the desc() function tranforms a vector into a format that will be sorted in 
# descending order. We can use this with the filter command to select the observations 
# where dep_delay has a rank of 10 or less

Q4<-filter(flights, rank(desc(dep_delay))<=10)

##########################
# Q5 # 	

#	Download the Pb_messy.csv and Pb_tidy.csv data sets from Blackboard.
# Can you create an R script that will transform the messy data set into 
# the tidy data set?

# read in the data and send it to the gather function using the pipe
#  create two new columns "site" and "Pb" ang gather the columns
#  the key is the name of the variable that forms the column names i.e. site 
#  the values are spread over the cells i.e. levels of Pb
#  use separate() to split the site column into two new columns, one showing site (1 or 2)
# the other showing the plot (A or B)
  Pb_tidy <-read_csv(here("Pb_messy.csv")) %>% 
  gather("site", "Pb", 2:5) %>%         
  separate("site", c("site", "plot"), sep = "_" )

write_csv(Pb_tidy,"Pb_tidy.csv")
  
##########################
# Q6 # 	

# Age has 4 missing values and Income has 5 missing values.	There is a 
# missing data pattern for the Income variable – data is more likely to 
# be missing from Income when Age is high. There is no missing data 
# pattern from Age.				
  
##########################
   # Q7 #
##########################

# Read in and then summarise the cholesterol data set
# Note that read.csv will read it in as a dataframe
cholesterol<-read_csv(here("cholesterol.csv"))
summary(cholesterol)


# There are 7 variables. 
# Age, Cholesterol, SysolicBP and BMI are continuous variables. 
# Gender, Smoking and Education are categorical (the variable type is character)
# Notice that there are 16 missing data in the Cholesterol variable.

# convert the categorical variables (currently stored as characters to factors)
cholesterol$Gender <- as_factor(cholesterol$Gender)
cholesterol$Smoking <- as_factor(cholesterol$Smoking)
cholesterol$Smoking <- as_factor(cholesterol$Smoking)

### Histograms ###

# We can plot histograms of the continuous variables in one plot as follows:
windows(16,5) # opens a new graphics window
par(mfrow=c(1,4)) # splits the window into one row and four columns
# next we plot histograms for all 4 variables in the graphics window
# the distribution of all the variables apart from SystolicBPlook normal
# SystolicBP looks skewed a transformation may help but the number of large
# values seems small on the histogram so lets investigate further.
for(i in c(1,3,4,5)) hist(cholesterol[[i]], xlab = names(cholesterol[i]), main = names(cholesterol[i]))

### Boxplots ###

# the box plots below show that distribution of the continuous variables are similar 
# for males and females, etc.
#there appear to be some potential outliers, one particular value for SystolicBP
# looks very high - it has a value of 1750 - this is not a valid measurement for
# blood pressure so it could be replaced with an NA 
windows(16,5) 
par(mfrow=c(1,4)) 
for(i in c(1,3,4,5)) boxplot(cholesterol[[i]]~cholesterol$Gender, xlab = names(cholesterol[i]), main = names(cholesterol[i]))

# replace the value of 1750 with an NA and recheck the distributions 
# Skew has gone from SystolicBP
cholesterol<- cholesterol %>% mutate(SystolicBP = (replace(SystolicBP, SystolicBP==1750, NA)))

### Scatterplots ###
# the scatterplots don't show any relationships between the continuous variables

windows(20,20)
pairs(cholesterol[,c(1,3,4,5)])

# The aggr function gives us an overview of the missing data.
# 16 missing data in Cholesterol and 1 in SystolicBP
a <- aggr(cholesterol)

# to check for missing data patterns we can use the histMiss() and marginplot()
# functions for the continuous variables and the barMiss() function
# for the categorical variables. We need to conver the cholesterol tibble to
# a dataframe to use some of the functions in VIM
cholesterol<-as.data.frame(cholesterol)
windows(10,10)
barMiss(cholesterol[,c("Gender", "Cholesterol")])
barMiss(cholesterol[,c("Education", "Cholesterol")])
barMiss(cholesterol[c("Smoking","Cholesterol")])

# there appear to be more missing cholesterol values for female patients
#
by(cholesterol$Cholesterol,cholesterol$Gender, summary)

# out of a total of 250 observations (143 female and 107 male)
# there are 10 missing values for females and 6 for males
# we can test to see whether there is a significant difference using
# prop.test you can ook up the help for this function to find out  how it works
prop.test(c(10,6), c(143,107))

# there appears to be a missing data pattern for Cholesterol and BMI
# data is more likely to be missing from Cholesterol when BMI is high. 
histMiss(select(cholesterol, "BMI","Cholesterol"))
histMiss(select(cholesterol, "SystolicBP","Cholesterol"))
histMiss(select(cholesterol, "Age","Cholesterol"))

