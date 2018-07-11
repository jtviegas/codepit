package org.aprestos.labs.concurrency.pragmatic;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class Exchange {

  private static String API_KEY = "xQ9Sq7ybYvkuhLnyC1JF", API_URI = "https://www.alphavantage.co/query",
      API_FUNCTION = "CURRENCY_EXCHANGE_RATE";

  private final RestClient client;

  public Exchange() {
    client = new RestClient();
  }

  @SuppressWarnings("unchecked")
  public double getExchangeRate(String fromCur, String toCur) {
    Double result = null;
    System.out.println(String.format("[getExchangeRate|IN] %s - >%s ", fromCur, toCur));
    try {

      MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<String, String>();
      queryParams.add("function", API_FUNCTION);
      queryParams.add("apikey", API_KEY);
      queryParams.add("from_currency", fromCur);
      queryParams.add("to_currency", toCur);

      ResponseEntity<Map<String, Object>> r = client.getResponse(new ParameterizedTypeReference<Map<String, Object>>() {
      }, API_URI, queryParams, (Object) null);

      result = Double.parseDouble(
          (String) ((Map<String, Object>) r.getBody().get("Realtime Currency Exchange Rate")).get("5. Exchange Rate"));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      System.out.println(String.format("[getExchangeRate|OUT] %f", result));
    }
    return result;
  }

  public double getPageLength(String site) {
    Double result = null;
    System.out.println(String.format("[getPageLength|IN] %s", site));
    try {

      ResponseEntity<String> r = client.getResponse(new ParameterizedTypeReference<String>() {
      }, site, null, (Object) null);

      result = (double) r.getBody().length();

    } catch (Exception e) {
      System.out.println(e.getMessage());
    } finally {
      System.out.println(String.format("[getPageLength|OUT] %f", result));
    }
    return result;
  }

}
