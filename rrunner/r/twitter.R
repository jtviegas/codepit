SCRIPTS_DIR <- Sys.getenv("R_SCRIPTS_DIR")
if( 0 == nchar(SCRIPTS_DIR) ){
  stop('!!! have to provide scripts folder in env !!! ...leaving.')
}

source(paste(SCRIPTS_DIR,'commons.R',sep='/'))
source(paste(SCRIPTS_DIR,'sentiment.R',sep='/'))
source(paste(SCRIPTS_DIR,'store.R',sep='/'))

#------------ LIBS
install_if_missing("jsonlite")
library(jsonlite)

#------------ CONST
IBM_RELATED_USERNAME_TWEET_NAME <- 'ibmUsrTweetSent'
NON_IBM_RELATED_USERNAME_TWEET_NAME <- 'nonIbmUsrTweetSent'
COLLECTION_NAME <- 'analysis'

containsIBM <- function(s){
  result <- FALSE
  foundIBMInThere <- grep(' IBM', s, ignore.case = TRUE)
  if(0 < length(foundIBMInThere) && 0 < foundIBMInThere ){
    result<-TRUE
  }
  else {
    foundIBMInThere <- grep('IBM ', s, ignore.case = TRUE)
    if(0 < length(foundIBMInThere) && 0 < foundIBMInThere ){
      result<-TRUE
    }
  }
  return(result)
}

msg2Obj<-function(message){
  msg<-fromJSON(message)
  username<-msg['user']$user$name
  text<-msg$text
  print(paste( paste( paste('handling message from', username, sep=' '), 'with text:', sep=' ' ),  text, sep=' ' ))
  score <- getSentiment(text)
  ts <- msg$timestamp_ms
  
  if( containsIBM(username) ){
    name<-IBM_RELATED_USERNAME_TWEET_NAME
  }
  else {
    name<-NON_IBM_RELATED_USERNAME_TWEET_NAME
  }
  
  c1 <- c(ts) 
  c2 <- c(name)
  c3 <- c(score)
  
  df <- data.frame(c1, c2, c3)
  colnames(df) <- c('timestamp', 'name', 'value')
  
  #result <- toJSON(df, pretty=TRUE, simplifyVector=TRUE)
  return(df)
}

handleMessage <- function(msg){
  obj<-msg2Obj(msg)
  tryCatch({
    print('...going to store obj...')
    store(obj, COLLECTION_NAME)
    print('...stored obj successfully.')
  }, error = function(e) {
    print(paste('failed to store obj:', e, sep=' '))
  })
}


#{   id:___, timestamp:______, name:______,value:_____, attributes: [   {key:___, value:___}, {key:___, value:___}   ]   } 
#message<-'{"in_reply_to_status_id_str":null,"in_reply_to_status_id":null,"created_at":"Sun May 15 08:32:53 +0000 2016","in_reply_to_user_id_str":null,"source":"<a href=\\"http://www.facebook.com/twitter\\" rel=\\"nofollow\\">Facebook<\\/a>","retweet_count":0,"retweeted":false,"geo":null,"filter_level":"low","in_reply_to_screen_name":null,"is_quote_status":false,"id_str":"731764274528460802","in_reply_to_user_id":null,"favorite_count":0,"id":731764274528460802,"text":"I posted a new photo to Facebook https://t.co/ZuUOIwEhA2","place":null,"lang":"en","favorited":false,"possibly_sensitive":false,"coordinates":null,"truncated":false,"timestamp_ms":"1463301173227","entities":{"urls":[{"display_url":"fb.me/2KzoxuJCw","indices":[33,56],"expanded_url":"http://fb.me/2KzoxuJCw","url":"https://t.co/ZuUOIwEhA2"}],"hashtags":[],"user_mentions":[],"symbols":[]},"contributors":null,"user":{"utc_offset":7200,"friends_count":182,"profile_image_url_https":"https://pbs.twimg.com/profile_images/1239745364/favicon2_normal.jpg","listed_count":2,"profile_background_image_url":"http://pbs.twimg.com/profile_background_images/203529936/eskuvomarketing_twitter_bg.jpg","default_profile_image":false,"favourites_count":3,"description":"Wedding marketing, Esküvő Marketing, Minőségi ügyfelek- magasabb árbevétel","created_at":"Wed Feb 09 23:30:41 +0000 2011","is_translator":false,"profile_background_image_url_https":"https://pbs.twimg.com/profile_background_images/203529936/eskuvomarketing_twitter_bg.jpg","protected":false,"screen_name":"eskuvomarketing","id_str":"249871693","profile_link_color":"0084B4","id":249871693,"geo_enabled":true,"profile_background_color":"FAFAFA","lang":"en","profile_sidebar_border_color":"C0DEED","profile_text_color":"333333","verified":false,"profile_image_url":"http://pbs.twimg.com/profile_images/1239745364/favicon2_normal.jpg","time_zone":"Budapest","url":"http://www.eskuvomarketing.hu","contributors_enabled":false,"profile_background_tile":false,"statuses_count":9937,"follow_request_sent":null,"followers_count":257,"profile_use_background_image":true,"default_profile":false,"following":null,"name":"Esküvő Marketing","location":"Hungary- Budapest","profile_sidebar_fill_color":"DDEEF6","notifications":null}}'