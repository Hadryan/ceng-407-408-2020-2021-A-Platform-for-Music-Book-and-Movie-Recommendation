package com.bitirme.api.JSONParser;

public interface JSONParsingService {

    Object get(String url);

    String postForRecommendation(String url, String datasetPath);

    Object getForPage(String url, int page);

    Object getForObject(String url, String param, long index);

}
