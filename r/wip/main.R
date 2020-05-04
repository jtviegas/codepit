setwd("~/Documents/github/R/wip")
source("functions.R")

ts_ds <- "/home/jtviegas/Documents/github/R/datasets/dow_jones_index.data"
ct_ds <- "http://archive.ics.uci.edu/ml/machine-learning-databases/wine-quality/winequality-white.csv"

dt_all <- loadCsv(ts_ds)
dt<-dt[dt$stock=="IBM",]
ww <- loadCsv2(ct_ds)

pe_wb <- "/home/jtviegas/Documents/github/R/datasets/povertyAndEquity_worldBank_Data.csv"
pe <- loadCsv(pe_wb)

head(pe)
head(ww)
subset(ww, pH==3)



ggplot(data=dt) + geom_point(mapping = aes(y = close, x = date))

head(dc)
ggplot(data=dc) + geom_point(mapping = aes(y = alcohol, x = `residual sugar`))

hist(x = as.numeric(dt$close,na.rm=TRUE))


plot(d_r)
summary(d_w)
hist(competition$biomass,  breaks = seq(400, 750, 35), xlab="Biomass", ylab="Frequency", main="")

install_if_missing("ggmap")
library(ggmap)

map<-get_map("London", zoom = 10)
ggmap(map, extent = "normal")


hdf <- get_map("houston")
ggmap(hdf, extent = "normal")
ggmap(hdf) # extent = "panel", note qmap defaults to extent = "device"
ggmap(hdf, extent = "device")



# make some fake spatial data
mu <- c(-95.3632715, 29.7632836); nDataSets <- sample(4:10,1)
chkpts <- NULL
for(k in 1:nDataSets){
  a <- rnorm(2); b <- rnorm(2);
  si <- 1/3000 * (outer(a,a) + outer(b,b))
  chkpts <- rbind(
    chkpts,
    cbind(MASS::mvrnorm(rpois(1,50), jitter(mu, .01), si), k)
  )
}
chkpts <- data.frame(chkpts)
names(chkpts) <- c("lon", "lat","class")
chkpts$class <- factor(chkpts$class)
qplot(lon, lat, data = chkpts, colour = class)

# show it on the map
ggmap(hdf, extent = "normal") +
  geom_point(aes(x = lon, y = lat, colour = class), data = chkpts, alpha = .5)

install_if_missing("tidyverse")


