SCRIPTS_DIR <- Sys.getenv("R_SCRIPTS_DIR")
if( 0 == nchar(SCRIPTS_DIR) ){
  stop('!!! have to provide scripts folder in env !!! ...leaving.')
}

#print('...setting R repository mirror...')
#r <- getOption("repos")
#r["CRAN"] <- "http://ftp.heanet.ie/mirrors/cran.r-project.org"
#options(repos=r)
#print('...R is ready!')

source(paste(SCRIPTS_DIR,'commons.R',sep='/'))
#------------ LIBS
install_if_missing("rkafka")
library(rkafka)

# ----- constants
ZK_PORT <- 2181
KF_PORT <- 9092
NO_CONSUMER_TIMEOUT <- 60
NO_CONSUMER_LIMIT <- 60
KAFKA_HOST<- 'kafka'

# ----- local vars
consumerCreationIterations <- 0

args<-commandArgs(TRUE)
#Sys.setenv(KF_HOST="169.44.6.146", R_SCRIPTS_DIR="/home/joaovieg/Documents/workspace/github/techdaysTeam/microservices/rrunner")
Sys.setenv(SERVICE_STATUS=1)
print('@topicConsumer.R ...')

# ...... check for the topic argument 
if( 0 == length(args) ){
  stop('!!! no argument topic !!! ...leaving.')
}

topic <- as.character(args[1])
print(paste('...going to handle topic:', topic, sep=' '))
createLockFile(topic)

ZK_CNX <- paste(paste(KAFKA_HOST, ZK_PORT, sep=':'), '/', sep='')
KF_CNX <- paste(paste(KAFKA_HOST, KF_PORT, sep=':') , '/', sep='')
print(paste('...kafka connection is ->', KF_CNX, sep=' '))
print(paste('...zookeeper connection is ->', ZK_CNX, sep=' '))


# ...... loading the topic related script 
script <- paste(paste(SCRIPTS_DIR, topic, sep='/'),'R', sep='.')
print(paste('...goint to load script: ', script, sep=''))
source(paste(paste(SCRIPTS_DIR, topic, sep='/'),'R', sep='.'))

# ...... create the kafka consumer
while(onStatus(topic)){
  consumer <- NULL
  tryCatch({
    consumer<-rkafka.createConsumer(ZK_CNX, topic, 
                          groupId="test-consumer-group", zookeeperConnectionTimeoutMs="100000", 
                          consumerTimeoutMs="10000", autoCommitEnable="NULL", 
                          autoCommitInterval="NULL", autoOffsetReset="NULL")
    print('...created consumer...')
  }, error = function(e) {
    print(paste('!!! failed to create consumer:', e))
    print(paste('...sleeping...'))
  })

  
  if(!is.null(consumer))
    break
  
  consumerCreationIterations <- consumerCreationIterations + 1
  if(consumerCreationIterations == NO_CONSUMER_LIMIT)
    stop('!!! could not create consumer !!! ...leaving.')
  
  Sys.sleep(NO_CONSUMER_TIMEOUT)
}

# ...... read messages
while(onStatus(topic)){
  tryCatch({
    message <- rkafka.read(consumer)
    if(0 < nchar(message)){
      print('...received a new message...')
      handleMessage(message)
    }
  }, error = function(e) {
    print(paste('!!! failed to receive message:', e))
  })

}

removeLockFile(topic)
print('...removed lock file....')
rkafka.closeConsumer(consumer)
print('...closed consumer....')

print('... topicConsumer.R@')

