# elastic search exercise


## notes

This is still an ongoing development, there is still the UI to be developed and there are quite a few things to brush up:
- backend tests are having a class loading issue, have to sort it out, but for the meantime we can use the swagger UI (`http://localhost:8080/swagger-ui.html`) to test the api;
- indexer is interpreting zip files, example is the enron.zip file in the root of the project, use this one instead of e json file;

## running the solution

1. load elasticsearch docker container (requires docker machine): `$./load_elasticsearch.sh`
2. load both projects, indexer and backend, into eclipse(or any other ide) as maven projects;
3. run indexer tests - will load the enron.zip into elastic search; all configuration can be changed in application.properties, if needed be we can use custom ones outside the jar;
4. run backend project and test the search query in the swagger-ui;

