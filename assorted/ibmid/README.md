# ibm blueid authentication experiment


- to get the client_id and secret_id and other configurations, you must register your application. Please, have a look at:
  https://w3-connections.ibm.com/wikis/home?lang=en-us#!/wiki/BlueID%20Single%20Sign-On%20%28SSO%29%20Self-Boarding%20Process/page/SSO%20with%20blueID%20%28IBM%20ID%29

- add the values to config.js;
- log in to bluemix using the cf tools
- build: ./scripts/build.sh
- deploy to bluemix: ./scripts/deploy_cf_bluemix.sh
- run it locally (won't be able to test the auth callback): ./scripts/run.sh