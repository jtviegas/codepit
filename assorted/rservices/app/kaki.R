TOPIC_OUT <- Sys.getenv("TOPIC_OUT")
#TOPIC_OUT <- 'tickers-r'
if( 0 == nchar(TOPIC_OUT) ){
  stop('!!! have to provide variable TOPIC_OUT in environment !!! ...leaving.')
}
TOPIC_IN <- Sys.getenv("TOPIC_IN")
#TOPIC_IN <- 'tickers'
if( 0 == nchar(TOPIC_IN) ){
  stop('!!! have to provide variable TOPIC_IN in environment !!! ...leaving.')
}
ZOOKEEPER <- Sys.getenv("ZOOKEEPER")
#ZOOKEEPER <- 'localhost'
if( 0 == nchar(ZOOKEEPER) ){
  stop('!!! have to provide variable ZOOKEEPER in environment !!! ...leaving.')
}
KAFKA <- Sys.getenv("KAFKA")
#KAFKA <- 'localhost'
if( 0 == nchar(KAFKA) ){
  stop('!!! have to provide variable KAFKA in environment !!! ...leaving.')
}

STRATEGY <- Sys.getenv("STRATEGY")
#STRATEGY <- 'analysisOne'
if( 0 == nchar(STRATEGY) ){
  stop('!!! have to provide variable STRATEGY in environment !!! ...leaving.')
}

# ----- constants
ZK_PORT <- 2181
KF_PORT <- 9092
NO_CONNECTION_TIMEOUT <- 60
NO_CONNECTION_LIMIT <- 60
SOURCES_DIR <- 'sources'

source('commons.R')

#------------ LIBS
install_if_missing("rkafka")
library(rkafka)
install_if_missing("jsonlite")
library(jsonlite)

Sys.setenv(SERVICE_STATUS=1)
print('@kaki ...')

print(paste('...going to handle TOPIC_IN:', TOPIC_IN, sep=' '))
createLockFile(TOPIC_IN)

ZK_CNX <- paste(ZOOKEEPER, ZK_PORT, sep=':')
print(paste('...zookeeper connection is ->', ZK_CNX, sep=' '))
KF_CNX <- paste(KAFKA, KF_PORT, sep=':')
print(paste('...kafka connection is ->', KF_CNX, sep=' '))

doWeHaveStrategy<-FALSE
file.names <- dir(SOURCES_DIR, pattern =".R")
for(fileName in file.names){
  scriptName<-gsub('.R', '', fileName)
  if( STRATEGY == scriptName ){
    script <- paste(SOURCES_DIR, fileName, sep='/')
    print(paste('sourcing script:', script, sep=' '))
    source(script)
    doWeHaveStrategy<-TRUE
    break
  }
}

if(!doWeHaveStrategy){
  stop(paste('!!! could not find the source file for strategy have to provide variable: ', STRATEGY, sep=' '))
}

#------------ FUNCTIONS

cleanup <- function(){
  print('@cleanup ...')
  removeLockFile(TOPIC_IN)
  print('...removed lock file....')
  
  if(!is.null(consumer)){
    rkafka.closeConsumer(consumer)
    print('...closed consumer....')
  }
  
  if(!is.null(producer)){
    rkafka.closeProducer(producer)
    print('...closed producer...')
  }
  print('... cleanup@')
  print('... kaki@')
}

handleMessage <- function(msg){
  result <- NULL
  tryCatch({
    print(paste('processing message:', msg, sep = ' '))
    
    obj<-fromJSON(msg)
    obj <- process(obj)
    result <- toJSON(obj, auto_unbox = TRUE, digits = 6)
    
    print(paste('processed message:', result, sep = ' '))
  }, error = function(e) {
    print(paste('failed to process message:', e, sep=' '))
  })
  return(result)
}

#------------ MAIN

# ...... create the kafka consumer
connectionCreationIterations <- 0
while(onStatus(TOPIC_IN)){
  consumer <- NULL
  tryCatch({
    consumer<-rkafka.createConsumer(ZK_CNX, TOPIC_IN,  groupId="kaki-consumer-group", 
                                    zookeeperConnectionTimeoutMs="100000", consumerTimeoutMs="10000")
    print('...created consumer...')
  }, error = function(e) {
    print(paste('!!! failed to create consumer:', e))
    print(paste('...sleeping...'))
  })

  if(!is.null(consumer))
    break
  
  connectionCreationIterations <- connectionCreationIterations + 1
  if(connectionCreationIterations == NO_CONNECTION_LIMIT){
    cleanup()
    stop('!!! could not create consumer !!! ...leaving.')
  }

  Sys.sleep(NO_CONNECTION_TIMEOUT)
}

# ...... create the kafka producer
connectionCreationIterations <- 0
while(onStatus(TOPIC_IN)){
  producer <- NULL
  tryCatch({
    producer<-rkafka.createProducer(KF_CNX, producerType="async", compressionCodec="none"
                                    , serializerClass="kafka.serializer.StringEncoder"
                                    , queueBufferingMaxTime="30000", queueBufferingMaxMessages="128"
                                    , queueEnqueueTimeoutTime="0", batchNumMessages="32")
    print('...created producer...')
  }, error = function(e) {
    print(paste('!!! failed to create producer:', e))
    print(paste('...sleeping...'))
  })

  if(!is.null(producer))
    break
  
  connectionCreationIterations <- connectionCreationIterations + 1
  if(connectionCreationIterations == NO_CONNECTION_LIMIT){
    cleanup()
    stop('!!! could not create producer !!! ...leaving.')
  }

  Sys.sleep(NO_CONNECTION_TIMEOUT)
}

# ...... read messages
withCallingHandlers(
  while(onStatus(TOPIC_IN)){
    tryCatch({
      message <- rkafka.read(consumer)
      if(0 < nchar(message)){
        print('...received a new message, going to handle it...')
        handledMessage <- handleMessage(message)
        rkafka.send(producer, TOPIC_OUT, KF_CNX, handledMessage)
        print('...handled and sent the message...')
      }
    }, error = function(e) {
      print(paste('!!! failed to receive message:', e))
    })
  }
, error = function(i) {
  cleanup()
})

cleanup()
