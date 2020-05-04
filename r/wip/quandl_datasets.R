install.packages("devtools")
library(devtools)
install_github("quandl/quandl-r")
library(Quandl)
Quandl.api_key('xQ9Sq7ybYvkuhLnyC1JF')

# Housing Market
#...US realHomePriceIndex 
rhpi <- as.tibble(Quandl("YALE/RHPI", api_key="xQ9Sq7ybYvkuhLnyC1JF"))


#Chained CPI - All Urban Consumers - Medical care
# U.S. all-city averages - Not Seasonally Adjusted - Frequency: Monthly - Base Year: 1982-84=100
Quandl("BLSI/SUUR0000SAM", api_key="xQ9Sq7ybYvkuhLnyC1JF")

#Chained CPI - All Urban Consumers - Housing
#U.S. all-city averages - Not Seasonally Adjusted - Frequency: Monthly - Base Year: 1982-84=100
Quandl("BLSI/SUUR0000SAH", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Chained CPI - All Urban Consumers - Education
# U.S. all-city averages - Not Seasonally Adjusted - Frequency: Monthly - Base Year: 1982-84=100
Quandl("BLSI/SUUR0000SAE1", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Chained CPI - All Urban Consumers - Apparel
# U.S. all-city averages - Not Seasonally Adjusted - Frequency: Monthly - Base Year: 1982-84=100
Quandl("BLSI/SUUR0000SAA", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Chained CPI - All Urban Consumers - Energy
# Quandl("BLSI/SUUR0000SA0E", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Social Security Beneficiary Data - All Beneficiaries
# Number of recipients and average monthly amount. - Monthly
Quandl("SOCSEC/ALL", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Population Projections - United States of America
Quandl("UGID/POPTOT_USA", api_key="xQ9Sq7ybYvkuhLnyC1JF")

# Education Enrolment - United States of America
Quandl("UGID/ENROL_USA", api_key="xQ9Sq7ybYvkuhLnyC1JF")


