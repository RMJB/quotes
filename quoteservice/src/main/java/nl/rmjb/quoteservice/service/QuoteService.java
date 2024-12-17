package nl.rmjb.quoteservice.service;

import nl.rmjb.quoteservice.model.Quote;
import nl.rmjb.quoteservice.repository.QuoteRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class QuoteService {

    private static final int MAX_MOST_LIKED_QUOTES = 5;
    private static final int MAX_AUTHORS_TO_FETCH = 10;

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public Quote trackQuoteIfAbsent(String text, String author) {
        Quote existingQuote = quoteRepository.findByTextAndAuthor(text, author);
        if (existingQuote == null) {
            return quoteRepository.save(new Quote(text, author));
        }
        return existingQuote;
    }

    public Quote fetchRandomQuote() {
        long quoteCount = quoteRepository.count();
        if (quoteCount == 0) {
            throw new NoSuchElementException("No quotes found in the database.");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt((int) quoteCount);
        List<Quote> allQuotes = quoteRepository.findAll();
        return allQuotes.get(randomIndex);
    }

    public void likeQuote(String id) {
        updateQuoteReaction(id, true);
    }

    public void dislikeQuote(String id) {
        updateQuoteReaction(id, false);
    }

    private void updateQuoteReaction(String id, boolean isLike) {
        quoteRepository.findById(id).ifPresentOrElse(quote -> {
            if (isLike) {
                quote.incrementLikes();
            } else {
                quote.incrementDislikes();
            }
            quoteRepository.save(quote);
        }, () -> System.out.println("Quote with ID " + id + " not found"));
    }

    public List<Quote> getMostLikedQuotes() {
        List<Quote> allQuotes = quoteRepository.findAll();
        return allQuotes.stream()
                .sorted(Comparator.comparingInt(Quote::getLikes).reversed())
                .limit(MAX_MOST_LIKED_QUOTES)
                .toList();
    }

    public List<String> getAuthors() {
        List<String> distinctAuthors = quoteRepository.findDistinctAuthors();
        return distinctAuthors.stream()
                .limit(MAX_AUTHORS_TO_FETCH)
                .toList();
    }
}