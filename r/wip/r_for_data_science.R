#setwd("~/Documents/github/R/wip")
source("functions.R")
install_if_missing("tidyverse")
library(tidyverse)
library(readr)
require(cowplot)

### =============================== Data visualisation ===============================

##### ggplot2

### -- scatterplot
ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, color=class))

# avoid overplotting
ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, color=class), position="jitter")


ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, size=class))


ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, alpha=class))

ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, shape=class))

# setting aes manually, independent of data
ggplot(data=mpg) + geom_point(mapping = aes(x=displ,y=hwy, shape=class), color="blue")

# facets
ggplot(data=mpg) + 
  geom_point(mapping = aes(x=displ,y=hwy)) +
  facet_wrap(~ class, nrow=2)

# facet grids
ggplot(data=mpg) + 
  geom_point(mapping = aes(x=displ,y=hwy)) +
  facet_grid(drv ~ cyl)

ggplot(data=mpg) + 
  geom_point(mapping = aes(x=displ,y=hwy)) +
  facet_grid(. ~ cyl)

## geoms
ggplot(data=mpg) + 
  geom_smooth(mapping = aes(x=displ,y=hwy))
  
ggplot(data=mpg) + 
  geom_smooth(mapping = aes(x=displ,y=hwy, linetype=drv))

ggplot(data=mpg) + 
  geom_smooth(mapping = aes(x=displ,y=hwy, linetype=drv), show.legend = F)

# combinig geoms

ggplot(data=mpg) + 
  geom_smooth(mapping = aes(x=displ,y=hwy)) +
                geom_point(mapping = aes(x=displ,y=hwy, linetype=drv))
# better not to duplicate settings
ggplot(data=mpg, mapping = aes(x=displ,y=hwy)) + 
  geom_point(mapping = aes(color=drv)) +
  geom_smooth(mapping = aes(linetype=drv))

# different data in the geoms
ggplot(data=mpg, mapping = aes(x=displ,y=hwy)) + 
  geom_point(mapping = aes(color=drv)) +
  geom_smooth(data=filter(mpg, class=='subcompact'))

### -- bar chart

# with default count stat
ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut))

## overriding the default stat
# with frequencies already defined in data
demo <- tribble(
  ~cut, ~freq,
  "fair", 1610,
  "good", 4906,
  "very good", 12082,
  "premium", 13791,
  "ideal", 21551
  )

ggplot(data=demo) +
  geom_bar(mapping = aes(x=cut,y=freq), stat = "identity")
# mapping to transformed/computed variables - proportion with total=1
ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, y=..prop.., group=1))
# more detail
ggplot(data=diamonds) +
  stat_summary(
    mapping = aes(x=cut, y=depth),
    fun.ymin = min,
    fun.ymax = max,
    fun.y = median
    )

## more magic in the barchart
# color borders
ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, color=cut))
# color bars
ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, fill=cut))

ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, fill=clarity))

ggplot(data=diamonds, mapping = aes(x=cut, fill=clarity)) +
  geom_bar(alpha=1/5, position = "identity")

ggplot(data=diamonds, mapping = aes(x=cut, color=clarity)) +
  geom_bar(fill=NA, position = "identity")

ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, fill=clarity), position="fill")

ggplot(data=diamonds) +
  geom_bar(mapping = aes(x=cut, fill=clarity), position="dodge")

### --- coordinates

## coord_flip: switches x and y
ggplot(data=mpg, mapping = aes(x=class, y=hwy)) +
  geom_boxplot() 

ggplot(data=mpg, mapping = aes(x=class, y=hwy)) +
  geom_boxplot() +
  coord_flip()

## spatial data - coord_quickmap() sets the aspect ratio correctly for maps
nz <- map_data("nz")

ggplot(nz, aes(long, lat, group = group)) +
  geom_polygon(fill = "white", colour = "black")

ggplot(nz, aes(long, lat, group = group)) +
  geom_polygon(fill = "white", colour = "black") +
  coord_quickmap()


# coord_polar() uses polar coordinates. 
# Polar coordinates reveal an interesting connection between a bar chart and a Coxcomb chart.

bar <- ggplot(data = diamonds) + 
  geom_bar(
    mapping = aes(x = cut, fill = cut), 
    show.legend = FALSE,
    width = 1
  ) + 
  theme(aspect.ratio = 1) +
  labs(x = NULL, y = NULL)

bar + coord_flip()
bar + coord_polar()

###The layered grammar of graphics

#  ggplot(data = <DATA>) + 
#  <GEOM_FUNCTION>(
#    mapping = aes(<MAPPINGS>),
#    stat = <STAT>, 
#    position = <POSITION>
# ) +
#  <COORDINATE_FUNCTION> +
#  <FACET_FUNCTION>
  

### =============================== Data transformation ===============================
install_if_missing("nycflights13")
library(nycflights13)
View(flights)

### dplyr main functions: filter, arrange, select, mutate and summarise - all in conjuction with group_by

## filter rows
( jan2 <- filter(flights, month==1, day==2) )
#...when filtering rown with numerical equality, some floating number precision might need to use near(sqrt(2)^2,2)
( jan2And3 <- filter(flights, month==1, day==2 | day==3) )
( jan2And3 <- filter(flights, month==1, day %in% c(2,3)) )
# include NA's
( jan2And3 <- filter(flights, is.na(day) | day %in% c(2,3)) )

## arrange / reorder
arrange(flights, year, month, day)
arrange(flights, desc(arr_delay))

## select columns
select(flights, year, month,dep_delay)
select(flights, year:dep_delay)
select(flights, - (year:day))
#...other available select functions: starts_with("abc"), ends_with("xyz"), contains("ijk"), matches("(.)\\1"), num_range("x", 1:3)
#...rename is a variant of select that renames the column
rename(flights, tail_num = tailnum)
#...reorder columns
select(flights, time_hour, air_time, everything())

## add new variables with mutate()
( flights_sml <- select(flights, year:day, ends_with("delay"), distance, air_time) )
mutate(flights_sml, gain=arr_delay-dep_delay, speed=distance/air_time * 60, hours=air_time/60, gain_per_hour = gain / hours)
#...if we just want the new variables, we use transmute
(dx <- transmute(flights_sml, gain=arr_delay-dep_delay, speed=distance/air_time * 60, hours=air_time/60, gain_per_hour = gain / hours) )
#...other available functions to mutate
mutate(dx, lag(gain), lead(gain), cumsum(gain), cummean(gain), min_rank(desc(gain)))

## summarise

summarise(flights, delay=mean(dep_delay, na.rm = TRUE))

( by_day <- group_by(flights, year,month, day) )
summarise(by_day, delay=mean(dep_delay, na.rm=TRUE))

by_dest <- group_by(flights, dest)
( delay <- summarise(by_dest, count=n(), dist=mean(distance, na.rm=T), delay=mean(arr_delay, na.rm=T) ) )

ggplot(data = delay, mapping = aes(x = dist, y = delay)) +
  geom_point(aes(size = count), alpha = 1/3) +
  geom_smooth(se = FALSE)
#... combining operations with pipe
by_dest <- group_by(flights, dest)
( delay <- summarise(by_dest, count=n(), dist=mean(distance, na.rm=T), delay=mean(arr_delay, na.rm=T) ) )
delay <- filter(delay, count > 20, dest != "HNL")

ggplot(data = delay, mapping = aes(x = dist, y = delay)) +
  geom_point(aes(size = count), alpha = 1/3) +
  geom_smooth(se = FALSE)
#...this is the same as
delays <- group_by(flights, dest) %>% 
  summarise(count=n(), dist=mean(distance, na.rm=T), delay=mean(arr_delay, na.rm=T) ) %>%
  filter(count > 20, dest != "HNL")

ggplot(data = delays, mapping = aes(x = dist, y = delay)) +
  geom_point(aes(size = count), alpha = 1/3) +
  geom_smooth(se = FALSE)

#...dealing with mising values
flights %>% group_by(year, month,day) %>%
  summarise(mean=mean(dep_delay))
#...We get a lot of missing values! That’s because aggregation functions obey the usual 
#... rule of missing values: if there’s any missing value in the input, the output will be a missing value.
flights %>% group_by(year, month,day) %>%
  summarise(mean=mean(dep_delay, na.rm=T))
#...we cold filter it beforehand
not_cancelled <- flights %>% 
  filter(!is.na(dep_delay), !is.na(arr_delay))

not_cancelled %>% 
  group_by(year, month, day) %>% 
  summarise(mean = mean(dep_delay))

#...counting missing values
delays <- not_cancelled %>% 
  group_by(tailnum) %>% 
  summarise(
    delay = mean(arr_delay)
  )

ggplot(data = delays, mapping = aes(x = delay)) + 
  geom_freqpoly(binwidth = 10)

delays <- not_cancelled %>% 
  group_by(tailnum) %>% 
  summarise(
    delay = mean(arr_delay, na.rm = TRUE),
    n = n()
  )

ggplot(data = delays, mapping = aes(x = n, y = delay)) + 
  geom_point(alpha = 1/10)

delays %>% 
  filter(n > 25) %>% 
  ggplot(mapping = aes(x = n, y = delay)) + 
  geom_point(alpha = 1/10)

# Convert to a tibble so it prints nicely
install_if_missing("Lahman")
library(Lahman)
batting <- as_tibble(Lahman::Batting)

batters <- batting %>% 
  group_by(playerID) %>% 
  summarise(
    ba = sum(H, na.rm = TRUE) / sum(AB, na.rm = TRUE),
    ab = sum(AB, na.rm = TRUE)
  )

batters %>% 
  filter(ab > 100) %>% 
  ggplot(mapping = aes(x = ab, y = ba)) +
  geom_point() + 
  geom_smooth(se = FALSE)

#...useful functions for summary
# Why is distance to some destinations more variable than to others?
not_cancelled %>% 
  group_by(dest) %>% 
  summarise(distance_sd = sd(distance)) %>% 
  arrange(desc(distance_sd))

# When do the first and last flights leave each day?
not_cancelled %>% 
  group_by(year, month, day) %>% 
  summarise(
    first = min(dep_time),
    last = max(dep_time)
  )

not_cancelled %>% 
  group_by(year, month, day) %>% 
  summarise(
    first_dep = first(dep_time), 
    last_dep = last(dep_time)
  )


not_cancelled %>% 
  group_by(year, month, day) %>% 
  mutate(r = min_rank(desc(dep_time))) %>% 
  filter(r %in% range(r))

# Which destinations have the most carriers?
not_cancelled %>% 
  group_by(dest) %>% 
  summarise(carriers = n_distinct(carrier)) %>% 
  arrange(desc(carriers))

# How many flights left before 5am? (these usually indicate delayed
# flights from the previous day)
not_cancelled %>% 
  group_by(year, month, day) %>% 
  summarise(n_early = sum(dep_time < 500))

# What proportion of flights are delayed by more than an hour?
not_cancelled %>% 
  group_by(year, month, day) %>% 
  summarise(hour_perc = mean(arr_delay > 60))

#If you need to remove grouping, and return to operations on ungrouped data, use ungroup().
daily <- group_by(flights, year, month, day)
daily %>% 
  ungroup() %>%             # no longer grouped by date
  summarise(flights = n())  # all flights

## grouped mutates
flights_sml %>% 
  group_by(year, month, day) %>%
  filter(rank(desc(arr_delay)) < 10)
flights_sml %>% mutate(rank(desc(arr_delay)))

### =============================== Exploratory Data Analysis  ===============================


### =============================== Wrangle: Tibbles ===============================

as_tibble(iris)

tibble(x=1:5, y=1, z=x^2+y)

tribble(
  ~x, ~y, ~z,
  #--|--|----
  "a", 2, 3.6,
  "b", 1, 8.5
)

## tibbles vs data.frame
#...printing
nycflights13::flights %>% 
  print(n = 10, width = Inf)

#options(tibble.print_max = n, tibble.print_min = m)# if more than m rows, print only n rows. 
#...Use 
options(dplyr.print_min = Inf) #to always show all rows.
#...Use 
options(tibble.width = Inf) #to always print all columns, regardless of the width of the screen.
#...see all options in package help
package?tibble

#...we can always use View:
nycflights13::flights %>% 
  View()

df <- tibble(
  x = runif(5),
  y = rnorm(5)
)

# Extract by name
df$x
df[["x"]]
# Extract by position
df[[1]]
# using pipe
df %>% .$x
df %>% .[["x"]]
# interacting with older code
class(as.data.frame(df))

### =============================== Wrangle: Data Import ===============================

install_if_missing("webreadr")
library(webreadr)

# read_csv() - reads comma delimited files
# read_csv2() - reads semicolon separated files
# read_tsv() - reads tab delimited files
# read_delim() - reads in files with any delimiter.
# read_fwf() - reads fixed width files. You can specify fields either by their widths with fwf_widths() or their position with fwf_positions(). 
# read_table() reads a common variation of fixed width files where columns are separated by white space.
# read_log() - reads Apache style log files. (But also check out webreadr which is built on top of read_log() and provides many more helpful tools.)

pewb<-read_csv("/home/jtviegas/Documents/github/R/datasets/povertyAndEquity_worldBank_Data.csv")
head(pewb)

#...we can read an inline csv
(dummy <- read_csv("a,b,c
         1,2,3
         4,5,6")
)
# no header in file
(dummy <- read_csv("1,2,3
         4,5,6", col_names=F)
)
# specifying what is NA
read_csv("a,b,c\n1,2,.", na = ".")

## parsing a vector
str(parse_logical(c("TRUE", "FALSE", "NA")))
str(parse_integer(c("1", "2", "3")))
str(parse_date(c("2011-09-17", "2001-09-02")))

# parse_logical()
# parse_integer()
# parse_double() is a strict numeric parser, and parse_number() is a flexible numeric parser. These are more complicated than you might expect because different parts of the world write numbers in different ways.
# parse_character() seems so simple that it shouldn’t be necessary. But one complication makes it quite important: character encodings.
# parse_factor() create factors, the data structure that R uses to represent categorical variables with fixed and known values.
# parse_datetime(), parse_date(), and parse_time() allow you to parse various date & time specifications. These are the most complicated because there are so many different ways of writing dates.



## parsing numbers
parse_double("1.23")
parse_double("1,23", locale = locale(decimal_mark = ","))

parse_number("$100")
parse_number("20%")
parse_number("It cost $123.45")

# Used in America
parse_number("$123,456,789")
# Used in many parts of Europe
parse_number("123.456.789", locale = locale(grouping_mark = "."))
# Used in Switzerland
parse_number("123'456'789", locale = locale(grouping_mark = "'"))

## parsing strings

charToRaw("hadley")
(x1 <- "El Ni\xf1o was particularly bad this year")
(x2 <- "\x82\xb1\x82\xf1\x82\xc9\x82\xbf\x82\xcd")
parse_character(x1, locale = locale(encoding = "Latin1"))
parse_character(x2, locale = locale(encoding = "Latin1"))
parse_character(x2, locale = locale(encoding = "Shift-JIS"))
#...sometimes we don't know the encoding
guess_encoding(charToRaw(x1))
guess_encoding(charToRaw(x2))
parse_character(x2, locale = locale(encoding = "KOI8-R"))

## factors
fruit <- c("apple", "banana")
parse_factor(c("apple", "banana", "bananana"), levels = fruit)

## dates and times
parse_datetime("2010-10-01T2010")
# If time is omitted, it will be set to midnight
parse_datetime("20101010")
parse_date("2010-10-01")
# this fails -> parse_date("20101001")
library(hms)
parse_time("01:10 am")
#> 01:10:00
parse_time("20:10:01")
## custom format
parse_date("01/02/15", "%m/%d/%y")
parse_date("01/02/15", "%d/%m/%y")
parse_date("01/02/15", "%y/%m/%d")

## specifying lang when using %b or %B
parse_date("1 janvier 2015", "%d %B %Y", locale = locale("fr"))

##parsing a file

#...readr column parsing strategy
guess_parser("2010-10-01")
guess_parser("15:01")
guess_parser(c("TRUE", "FALSE"))
guess_parser(c("1", "5", "9"))
guess_parser(c("12,352,561"))
str(parse_guess("2010-10-10"))
#...when parsing a file sometimes we have to specify collumns
challenge <- read_csv(
  readr_example("challenge.csv"), 
  col_types = cols(
    x = col_double(),
    y = col_character()
  )
)
#...specifying the size to parse
challenge2 <- read_csv(readr_example("challenge.csv"), guess_max = 1001)
#...specifying the default
challenge2 <- read_csv(readr_example("challenge.csv"), 
                       col_types = cols(.default = col_character())
)

## writing to a file

write_csv(challenge, "challenge.csv")
#...but this will forget the column types

# we can save the data using the R's custom binary format
write_rds(challenge, "challenge.rds")
rds <- read_rds("challenge.rds")
head(rds)

# or we can use the feather binary format
install_if_missing("feather")
library(feather)
write_feather(challenge, "challenge.feather")
f<-read_feather("challenge.feather")
head(f)


### =============================== Wrangle: Tidy data ===============================

## gathering

# ...A common problem is a dataset where some of the column names are not names of variables, but values of a variable.
table4a
table4a %>% gather(`1999`, `2000`, key = "year", value = "cases")
table4b
table4a %>% gather(`1999`, `2000`, key = "year", value = "population")
#...To combine the tidied versions of table4a and table4b into a single tibble, we need to use dplyr::left_join()
tidy4a <- table4a %>% gather(`1999`, `2000`, key = "year", value = "cases")
tidy4b <- table4b %>% gather(`1999`, `2000`, key = "year", value = "population")
left_join(tidy4a, tidy4b)

## spreading

table2
spread(table2, key = type,value = count)

## separating and uniting

#...separate() pulls apart one column into multiple columns, by splitting wherever a separator character appears
table3
table3 %>% separate(rate, into=c("cases","population"), convert=TRUE)
table3 %>% separate(year, into=c("century","year"), convert=TRUE, sep=2)

#...unite() is the inverse of separate(): it combines multiple columns into a single column

table5
table5 %>% unite(new, century, year, sep="")

tibble(x = c("a,b,c", "d,e", "f,g,i")) %>% 
  separate(x, c("one", "two", "three"),extra = "")


## missing values

stocks <- tibble(
  year   = c(2015, 2015, 2015, 2015, 2016, 2016, 2016),
  qtr    = c(   1,    2,    3,    4,    2,    3,    4),
  return = c(1.88, 0.59, 0.35,   NA, 0.92, 0.17, 2.66)
)

stocks %>% 
  spread(year, return)

stocks %>% 
  spread(year, return) %>% 
  gather(year, return, `2015`:`2016`, na.rm = TRUE)

#...complete() takes a set of columns, and finds all unique combinations. It then ensures the original dataset
#...contains all those values, filling in explicit NAs where necessary.
stocks %>% 
  complete(year, qtr)

#...Sometimes when a data source has primarily been used for data entry, missing values indicate that 
# ...the previous value should be carried forward:
treatment <- tribble(
  ~ person,           ~ treatment, ~response,
  "Derrick Whitmore", 1,           7,
  NA,                 2,           10,
  NA,                 3,           9,
  "Katherine Burke",  1,           4
)

treatment %>% 
  fill(person)


### =============================== Wrangle: Factors ===============================

library(forcats)

# using strings as variables sometimes is not acceptable
# take for instance, months
x1 <- c("Dec", "Apr", "Mar", "Jan")
#...prone to typos
x2 <- c("Dec", "Apr", "Mar", "Jam")
# it does not sort in a useful way
sort(x1)
# so.... lets create a factors
month_levels <- c(
  "Jan", "Feb", "Mar", "Apr", "May", "Jun", 
  "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
)
(y1 <- factor(x1, levels=month_levels))
sort(y1)
#...now we get an NA for unmatching level values
(y2 <- factor(x2, levels=month_levels))
#...if we want to get warned abut any issues we use readr::parse_factor:
parse_factor(x2, levels = month_levels)




