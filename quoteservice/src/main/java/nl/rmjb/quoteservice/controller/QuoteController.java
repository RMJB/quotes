package nl.rmjb.quoteservice.controller;

import nl.rmjb.quoteservice.model.Quote;
import nl.rmjb.quoteservice.service.QuoteApiService;
import nl.rmjb.quoteservice.service.QuoteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private static final String FIELD_TEXT = "text";
    private static final String FIELD_AUTHOR = "author";
    private static final String FIELD_ID = "id";

    private final QuoteApiService quoteApiService;
    private final QuoteService quoteService;

    public QuoteController(QuoteApiService quoteApiService, QuoteService quoteService) {
        this.quoteApiService = quoteApiService;
        this.quoteService = quoteService;
    }

    @GetMapping("/random")
    public Map<String, String> getRandomQuote() {
        Map<String, String> fetchedQuote = quoteApiService.fetchRandomQuote();
        return processFetchedQuote(fetchedQuote);
    }

    @GetMapping("/authors")
    public List<String> getAuthors() {
        return quoteService.getAuthors();
    }

    @PostMapping("/{id}/like")
    public void likeQuote(@PathVariable String id) {
        quoteService.likeQuote(id);
    }

    @PostMapping("/{id}/dislike")
    public void dislikeQuote(@PathVariable String id) {
        quoteService.dislikeQuote(id);
    }

    @GetMapping("/most-liked")
    public List<Quote> getMostLikedQuotes() {
        return quoteService.getMostLikedQuotes();
    }

    private Map<String, String> processFetchedQuote(Map<String, String> fetchedQuote) {
        String text = fetchedQuote.get(FIELD_TEXT);
        String author = fetchedQuote.get(FIELD_AUTHOR);
        Quote trackedQuote = quoteService.trackQuoteIfAbsent(text, author);
        fetchedQuote.put(FIELD_ID, String.valueOf(trackedQuote.getId()));
        return fetchedQuote;
    }
}