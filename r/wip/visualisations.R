source("functions.R")
install_if_missing("webreadr")
library(webreadr)
install_if_missing("cowplot")
require(cowplot)


init <- function(sDataFolder, vFiles){
  datasets <- list()
  for( f in vFiles){
    dset_name <- sub('.csv', '', f)[[1]]
    fname <- paste(sDataFolder,"/",f, sep="")
    print(paste("loading", dset_name, "from", fname))
    dataset <- read_csv(fname)
    levels(dataset$LOCATION)
    is.factor(dataset$LOCATION)
    dataset$LOCATION <- as.factor(dataset$LOCATION)
    is.factor(dataset$LOCATION)
    levels(dataset$LOCATION)
    datasets[[dset_name]] <- dataset
  }
  return(datasets)
}


filter_datasets <- function(lDatasets, vCountries){
  ns <- names(lDatasets)
  ds<-list()
  for(i in 1:length(lDatasets)){
      dataset <- lDatasets[[i]]
      name <- ns[[i]]
      ds[[name]]<- filter(dataset, LOCATION %in% vCountries )
  }
  return(ds)
}

populationAndHealthSpending <- function(datasets){
  
  pop<-filter(datasets$population, MEASURE=="MLN_PER", SUBJECT=="TOT")
  wa_pop<-datasets$work_age_population
  h_spend<-filter(datasets$health_spending, MEASURE=="USD_CAP", SUBJECT=="TOT")
  ph_spend<-filter(datasets$pharma_spending, MEASURE=="USD_CAP", SUBJECT=="TOT")
  gdp<-filter(datasets$gdp, MEASURE=="USD_CAP", SUBJECT=="TOT")
  gdp_m<-filter(datasets$gdp, MEASURE=="MLN_USD", SUBJECT=="TOT")
  

  g_pop <- ggplot() + 
    ggtitle("population (millions)") +
    geom_line(data = pop, mapping = aes(x = TIME, y = Value, color = LOCATION)) 
  g_w_a_pop <- ggplot() + 
    ggtitle("working age population %") +
    geom_line(data = wa_pop, mapping = aes(x = TIME, y = Value, color = LOCATION)) 
  g_t_h_s <- ggplot() + 
    ggtitle("total health spending $/cap") +
    geom_line(data = h_spend, mapping = aes(x = TIME, y = Value, color = LOCATION)) 
  g_ph_s <- ggplot() + 
    ggtitle("pharma spending $/cap") +
    geom_line(data = ph_spend, mapping = aes(x = TIME, y = Value, color = LOCATION)) 
  g_gdp_cap <- ggplot() + 
    ggtitle("GDP $/cap") +
    geom_line(data = gdp, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_gdp_m <- ggplot() + 
    ggtitle("GDP M$") +
    geom_line(data = gdp_m, mapping = aes(x = TIME, y = Value, color = LOCATION))

  plot_grid(g_pop, g_w_a_pop, g_gdp_cap,g_gdp_m, g_t_h_s, g_ph_s, labels = "auto", ncol=2, align = 'v')
}

healthResources <- function(datasets){
  
  nurses<-filter(datasets$nurses, MEASURE=="1000HAB", SUBJECT=="TOT")
  doctors<-filter(datasets$doctors, MEASURE=="1000HAB", SUBJECT=="TOT")
  hospital_beds<-filter(datasets$hospital_beds, MEASURE=="1000HAB", SUBJECT=="TOT")
  mammography_machines<-filter(datasets$mammography_machines, MEASURE=="1000000HAB", SUBJECT=="TOT")
  mri_units<-filter(datasets$mri_units, MEASURE=="1000000HAB", SUBJECT=="TOT")
  comp_tomo_scanners<-filter(datasets$comp_tomo_scanners, MEASURE=="1000000HAB", SUBJECT=="TOT")
  radiotherapy_equipment<-filter(datasets$radiotherapy_equipment, MEASURE=="1000000HAB", SUBJECT=="TOT")
  
  g_nurses <- ggplot() + 
    ggtitle("nurses/1K inhabitants") +
    geom_line(data = nurses, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_doctors <- ggplot() + 
    ggtitle("doctors/1K inhabitants") +
    geom_line(data = doctors, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_hospital_beds <- ggplot() + 
    ggtitle("hospital_beds/1K inhabitants") +
    geom_line(data = hospital_beds, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_mammography_machines <- ggplot() + 
    ggtitle("mammography_machines/1M inhabitants") +
    geom_line(data = mammography_machines, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_mri_units <- ggplot() + 
    ggtitle("mri_units/1M inhabitants") +
    geom_line(data = mri_units, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_comp_tomo_scanners <- ggplot() + 
    ggtitle("comp_tomo_scanners/1M inhabitants") +
    geom_line(data = comp_tomo_scanners, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_radiotherapy_equipment <- ggplot() + 
    ggtitle("radiotherapy_equipment/1M inhabitants") +
    geom_line(data = radiotherapy_equipment, mapping = aes(x = TIME, y = Value, color = LOCATION))
  
  plot_grid(g_nurses, g_doctors, g_hospital_beds, g_mammography_machines, g_mri_units
            , g_comp_tomo_scanners, g_radiotherapy_equipment
            , labels = "auto", ncol=2, align = 'v')
  
  
}

healthOutcomes <- function(datasets){
  
  consultations<-filter(datasets$consultations, MEASURE=="CAP", SUBJECT=="TOT")
  child_vaccination<-filter(datasets$child_vaccination, MEASURE=="PC_CHILD", SUBJECT=="DTP")
  mri_exams<-filter(datasets$mri_exams, MEASURE=="1000HAB", SUBJECT=="TOT")
  comp_tomography_exams<-filter(datasets$comp_tomography_exams, MEASURE=="1000HAB", SUBJECT=="TOT")
  hospital_stay<-filter(datasets$hospital_stay, MEASURE=="DAY", SUBJECT=="ACUTE")
  hospital_discharge<-filter(datasets$hospital_discharge, MEASURE=="100000HAB", SUBJECT=="TOT")
  life_expectancy_at_birth<-filter(datasets$life_expectancy_at_birth, MEASURE=="YR", SUBJECT=="TOT")
  infant_mortality_rate<-filter(datasets$infant_mortality_rate, MEASURE=="DEATH_1000BIRTH", SUBJECT=="TOT")
  deaths_cancer<-filter(datasets$deaths_cancer, MEASURE=="100000PER", SUBJECT=="TOT")

  g_consultations <- ggplot() + 
    ggtitle("consultations/capita") +
    geom_line(data = consultations, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_child_vaccination <- ggplot() + 
    ggtitle("child vaccination %") +
    geom_line(data = child_vaccination, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_mri_exams <- ggplot() + 
    ggtitle("mri exams/1K inhabitants") +
    geom_line(data = mri_exams, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_comp_tomography_exams <- ggplot() + 
    ggtitle("comp tomography exams/1K inhabitants") +
    geom_line(data = comp_tomography_exams, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_hospital_stay <- ggplot() + 
    ggtitle("avg hospital stay acute illness: days") +
    geom_line(data = hospital_stay, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_hospital_discharge <- ggplot() + 
    ggtitle("hospital_discharge/100K inhabitants") +
    geom_line(data = hospital_discharge, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_life_expectancy_at_birth <- ggplot() + 
    ggtitle("life expectancy at birth: years") +
    geom_line(data = life_expectancy_at_birth, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_infant_mortality_rate <- ggplot() + 
    ggtitle("infant mortality rate/1K births") +
    geom_line(data = infant_mortality_rate, mapping = aes(x = TIME, y = Value, color = LOCATION))
  g_deaths_cancer <- ggplot() + 
    ggtitle("deaths_cancer/100K person") +
    geom_line(data = deaths_cancer, mapping = aes(x = TIME, y = Value, color = LOCATION))
  
  plot_grid( c(g_consultations, g_child_vaccination), labels = "auto", ncol=2, align = 'v')
  
  
  #plot_grid(g_consultations, g_child_vaccination, g_mri_exams, g_comp_tomography_exams
  #          , g_hospital_stay, g_hospital_discharge, g_life_expectancy_at_birth
  #          , g_infant_mortality_rate, g_deaths_cancer
  #          , labels = "auto", ncol=2, align = 'v')
  
  
}



financeView <- function(countries){
  countries <- c("DEU","GBR", "USA", "PRT")
  folder<-"visualisations"
  files <- c("gdp.csv", "household_disp_income.csv", "household_savings.csv", "household_debt.csv"
             , "inflation.csv")
  dset <- init(folder, files)
  dset<-filter_datasets(dset, countries)
  
  dset_index <- c(1,1,2,3, 4, 5)
  dset_subj <- c("TOT", "TOT", "NET", "TOT", "TOT", "TOT")
  dset_measure <- c("USD_CAP", "MLN_USD", "AGRWTH", "PC_HHDI", "PC_NDI", "AGRWTH")
  titles <- c("GDP $/cap", "GDP M$", "new disposable income annual growth rate %", 
              "Household savings % of disposable income", "Household debt % of net disposable income"
              , "Inflation (CPI) Annual growth rate %")
  
  plots <- list()
  for(i in 1:length(dset_index)){
    ds<-filter(dset[[ dset_index[[i]] ]], MEASURE==dset_measure[[i]], SUBJECT==dset_subj[[i]])
    plots[[i]] <- ggplot() + 
      ggtitle(titles[[i]]) +
      geom_line(data = ds, mapping = aes(x = TIME, y = Value, color = LOCATION))
  }
  
  plot_grid( plotlist = plots, labels = "auto", ncol=2, align = 'v')
  
}

FOLDER<-"visualisations"
files <- c("population.csv", "work_age_population.csv", 
           "health_spending.csv", "gdp.csv", "pharma_spending.csv",
           "nurses.csv", "doctors.csv", "hospital_beds.csv"
           , "mammography_machines.csv", "mri_units.csv", "comp_tomo_scanners.csv"
           , "radiotherapy_equipment.csv"
           , "consultations.csv", "child_vaccination.csv", "mri_exams.csv"
           , "comp_tomography_exams.csv", "hospital_stay.csv", "hospital_discharge.csv"
           , "life_expectancy_at_birth.csv", "infant_mortality_rate.csv", "deaths_cancer.csv"
           
           )
#COUNTRIES <- c("DEU", "GBR", "IRL", "PRT", "RUS", "USA")
COUNTRIES <- c("DEU","RUS", "GBR", "USA", "PRT","LTU")

datasets<-init(FOLDER, files)
datasets<-filter_datasets(datasets, COUNTRIES)
populationAndHealthSpending(datasets)
healthResources(datasets)
healthOutcomes(datasets)
financeView(COUNTRIES)





