REM #
REM #---------------------------------------------------#
REM #						        #
REM # 5. Insert collection from File		        #
REM #						        #
REM #---------------------------------------------------#
REM #	
mongoimport.exe --db test --collection artists --drop --file artists.json
mongoimport.exe --db test --collection artworks --drop --file artworks.json
REM #



