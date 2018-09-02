# elasticsearch exercise


## notes

This is still an ongoing development, there is still the UI to be developed and there are quite a few things to brush up:
- backend needs custom exception handling;
- indexer is interpreting zip files, example is the enron.zip file in the root of the project, use this one instead of the json file;

## running the solution

1. load elasticsearch docker container (requires docker machine): `$./load_elasticsearch.sh`
2. load both projects, _indexer_ and _backend_, into eclipse(or any other ide) as maven projects;
3. to index docs:
  * run _indexer_ tests (just one so far) - will load the `enron.zip` into elasticsearch; all configuration can be changed in `application.properties`, if needed we can use custom ones outside the jar;
  * we can also `mvn clean install`, take the jar from target folder and execute in the command line as in `java -jar indexer-0.0.1-SNAPSHOT.jar -input enron.zip`;
  * or we might just run the script `index_docs.sh`;
4. run _backend_ tests (just one so far), it will check the retrieval of a set of documents;
5. run _backend_ project and test any search query in `http://localhost:8080/swagger-ui.html`;

