package com.ramdasgade.stock.dbservice.repository;

import com.ramdasgade.stock.dbservice.model.Quote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface QuotesRepository extends MongoRepository<Quote, Integer> {
    List<Quote> findByUserName(String username);

    //void deleteAllByQuote(List<Quote> quotes);
}
