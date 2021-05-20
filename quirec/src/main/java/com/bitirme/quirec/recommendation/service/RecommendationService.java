package com.bitirme.quirec.recommendation.service;

import com.bitirme.quirec.questionnarie.model.CategoryType;
import com.bitirme.quirec.recommendation.model.Recommendation;

import java.io.IOException;

public interface RecommendationService {

    Recommendation get(long userId);

    void rate(long userId, CategoryType type, long itemId, double rate) throws IOException;

}
