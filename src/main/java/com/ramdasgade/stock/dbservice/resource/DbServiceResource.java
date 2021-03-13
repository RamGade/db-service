package com.ramdasgade.stock.dbservice.resource;

import com.ramdasgade.stock.dbservice.model.Quote;
import com.ramdasgade.stock.dbservice.model.Quotes;
import com.ramdasgade.stock.dbservice.repository.QuotesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest/db")
public class DbServiceResource {

    private final QuotesRepository quotesRepository;

    public DbServiceResource(QuotesRepository quotesRepository) {
        this.quotesRepository = quotesRepository;
    }

    @GetMapping("/{username}")
    public List<String> getQuotes(@PathVariable("username") final String username){
        List<String> quotes = getQuotesByUserName(username);
        System.out.println("inside getQuotes, username : "+username+ ", Quotes : "+quotes);
        return quotes;
    }

    @PostMapping("/add")
    public List<String> add(@RequestBody final Quotes quotes){

        quotes.getQuotes()
                .stream()
                .map(quote -> new Quote(quotes.getUserName(), quote))
                .forEach(quotesRepository::save);
        return getQuotesByUserName(quotes.getUserName());
    }

    @DeleteMapping("/delete/{username}")
    public List<String> delete(@PathVariable("username") final String username){

        List<Quote> quotes = quotesRepository.findByUserName(username);
        System.out.println("quotes @delete() : "+quotes.toString());
        quotes.forEach(quotesRepository::delete);
        return getQuotesByUserName(username);
    }

    private List<String> getQuotesByUserName(String username) {
        return quotesRepository.findByUserName(username)
                .stream()
                .map(Quote::getQuote)
                .collect(Collectors.toList());
    }
}
