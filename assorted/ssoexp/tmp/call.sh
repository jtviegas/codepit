#!/bin/sh

#https://idaas.ng.bluemix.net/sps/oauth20sp/oauth20/token
tokenEndpointUrl="https://ssoexp-ucv0ssb7w7-ct20.iam.ibmcloud.com/idaas/oidc/endpoint/default/token"
      

curl -v -k -d \
"client_id=AJJlNwM5Sb&client_secret=S069vcWAlb&grant_type=password&username=app&password=password&scope=profile" $tokenEndpointUrl


bearerTokenEndpoint="http://login.ng.bluemix.net/UAALoginServerWAR/oauth/token"
