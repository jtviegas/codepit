package org.challenges.camunda.bpmn;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@Slf4j
class XmlFetcherImpl implements XmlFetcher {

    private final String url;

    public XmlFetcherImpl(@Value("${bpmn.url:https://n35ro2ic4d.execute-api.eu-central-1.amazonaws.com/prod/engine-rest/process-definition/key/invoice/xml}") final String url){
        this.url = url;
    }

    @Override
    public String fetch() {
        log.debug("[fetch|in]");

        HttpResponse<Bpmn> response = Unirest.get(url)
                .header("accept", "application/json")
                .asObject(Bpmn.class);

        if( ! response.isSuccess() )
            throw new RuntimeException(format("failed to fetch Bpmn model from: %s", url));

        String result = response.getBody().bpmn20Xml;
        log.debug("[fetch|out] => result: {}", result);
        return result;
    }

    @Data
    private static class Bpmn {
        private String id;
        private String bpmn20Xml;
    }

}
