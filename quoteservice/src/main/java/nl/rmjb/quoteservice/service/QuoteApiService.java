package nl.rmjb.quoteservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import nl.rmjb.quoteservice.model.Quote;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class QuoteApiService {

    private static final List<String> QUOTE_API_SOURCES = List.of(
            "https://zenquotes.io/api/random",
            "https://dummyjson.com/quotes/random"
    );
    private static final String ZEN_QUOTES_AUTHOR = "zenquotes.io";
    private static final String RATE_LIMIT_MESSAGE = "Rate limit exceeded for ";
    private static final String ERROR_MESSAGE = "Error fetching quote from ";

    private final RestTemplate restTemplate = new RestTemplate();
    private final QuoteService quoteService;

    public QuoteApiService(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    public Map<String, String> fetchRandomQuote() {
        List<String> shuffledSources = new ArrayList<>(QUOTE_API_SOURCES);
        Collections.shuffle(shuffledSources);

        for (String source : shuffledSources) {
            try {
                return fetchQuoteBySource(source);
            } catch (HttpStatusCodeException e) {
                if (e.getStatusCode().value() == 429) { // Rate limit exceeded
                    System.err.println(RATE_LIMIT_MESSAGE + source + ". Trying next source...");
                }
            } catch (Exception e) {
                System.err.println(ERROR_MESSAGE + source + ": " + e.getMessage());
            }
        }

        // Fallback to database if all external sources fail
        return fetchFromDatabase();
    }

    private Map<String, String> fetchQuoteBySource(String source) {
        switch (getApiSourceType(source)) {
            case ZEN_QUOTES:
                return fetchQuoteFromZenQuotes(source);
            case DUMMY_JSON:
                return fetchQuoteFromDummyJson(source);
            default:
                throw new IllegalArgumentException("Unknown source type: " + source);
        }
    }

    private ApiSourceType getApiSourceType(String source) {
        if (source.contains("zenquotes")) {
            return ApiSourceType.ZEN_QUOTES;
        } else if (source.contains("dummyjson")) {
            return ApiSourceType.DUMMY_JSON;
        } else {
            return ApiSourceType.UNKNOWN;
        }
    }

    private Map<String, String> fetchQuoteFromDummyJson(String source) {
        JsonNode response = restTemplate.getForObject(source, JsonNode.class);
        if (response == null || !response.has("quote")) {
            throw new RuntimeException("Invalid response from DummyJSON");
        }
        return formatQuote(response.get("quote").asText(), response.get("author").asText());
    }

    private Map<String, String> fetchQuoteFromZenQuotes(String source) {
        JsonNode[] response = restTemplate.getForObject(source, JsonNode[].class);
        if (response == null || response.length == 0) {
            throw new RuntimeException("Invalid response from ZenQuotes");
        }

        JsonNode quote = response[0];
        if (ZEN_QUOTES_AUTHOR.equals(quote.get("a").asText())) {
            return fetchFromDatabase(); // Fallback
        }
        return formatQuote(quote.get("q").asText(), quote.get("a").asText());
    }

    private Map<String, String> fetchFromDatabase() {
        System.out.println("Fetching a quote from the database...");
        Quote databaseQuote = quoteService.fetchRandomQuote();
        if (databaseQuote == null) {
            throw new RuntimeException("No quotes found in the database");
        }
        return formatQuote(databaseQuote.getText(), databaseQuote.getAuthor());
    }

    private Map<String, String> formatQuote(String text, String author) {
        Map<String, String> quote = new HashMap<>();
        quote.put("text", text);
        quote.put("author", author);
        return quote;
    }

    private enum ApiSourceType {
        ZEN_QUOTES,
        DUMMY_JSON,
        UNKNOWN
    }
}