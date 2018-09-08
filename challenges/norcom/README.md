# elasticsearch exercise


## notes

This is still an ongoing development, there is still quite a few things to brush up:
- backend needs custom exception handling;
- indexer is interpreting zip files, example is the enron.zip file in the root of the project, use this one instead of the json file;
- UI can benefit of loads of additional usability/design work, the focus was till now mainly on functionality;

## running the solution

1. load _elasticsearch_ docker container (requires docker machine): `$./load_elasticsearch.sh`
2. load test set of documents into the _indexer_ ( requires java 8 ): `$./index_docs.sh`
3. load _backend_ ( requires java 8, maven ): `$./load_backend.sh` , you can access swagger at `http://localhost:8080/swagger-ui.html`
4. load _UI_ ( requires nodeNodeJS ^9.11, npm ^6.1 ): `$./load_ui.sh` , you can now access the UI app pointing the browser to `http://localhost:3000` . Try searching for `paper` for starters.

## other features

* to index docs:
  * run _indexer_ tests (just one so far) - will load the `enron.zip` into elasticsearch; all configuration can be changed in `application.properties`, if needed we can use custom ones outside the jar;
  * we can also `mvn clean install`, take the jar from target folder and execute in the command line as in `java -jar indexer-0.0.1-SNAPSHOT.jar -input enron.zip`;
  * or we might just run the script `index_docs.sh`;
* load indexer and backend eclipse projects or as maven projects into any other IDE:
    * run _backend_ tests (just one so far), it will check the retrieval of a set of documents;
    

    
