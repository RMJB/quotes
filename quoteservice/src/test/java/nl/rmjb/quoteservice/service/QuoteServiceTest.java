package nl.rmjb.quoteservice.service;

import nl.rmjb.quoteservice.model.Quote;
import nl.rmjb.quoteservice.repository.QuoteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepository;

    @InjectMocks
    private QuoteService quoteService;

    @Test
    void fetchRandomQuote_WithNoQuotes_ThrowsException() {
        when(quoteRepository.count()).thenReturn(0L);

        assertThrows(NoSuchElementException.class, () -> quoteService.fetchRandomQuote());

        verify(quoteRepository, times(1)).count();
        verifyNoMoreInteractions(quoteRepository);
    }

    @Test
    void fetchRandomQuote_WithQuotes_ReturnsRandomQuote() {
        Quote quote1 = new Quote("Life is what happens when you're busy making other plans.", "John Lennon");
        Quote quote2 = new Quote("The unexamined life is not worth living.", "Socrates");
        List<Quote> quotes = List.of(quote1, quote2);

        when(quoteRepository.count()).thenReturn((long) quotes.size());
        when(quoteRepository.findAll()).thenReturn(quotes);

        int index = ThreadLocalRandom.current().nextInt(quotes.size());
        Quote randomQuote = quotes.get(index);

        Quote fetchedQuote = quoteService.fetchRandomQuote();

        assertNotNull(fetchedQuote);
        assertTrue(quotes.contains(fetchedQuote));

        verify(quoteRepository, times(1)).count();
        verify(quoteRepository, times(1)).findAll();
        verifyNoMoreInteractions(quoteRepository);
    }
}