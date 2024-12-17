package nl.rmjb.quoteservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import nl.rmjb.quoteservice.model.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class QuoteApiServiceTest {
    @Mock
    private QuoteService mockQuoteService;
    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private QuoteApiService quoteApiService;

    @BeforeEach
    void setUp() throws Exception {
        mockQuoteService = mock(QuoteService.class);
        mockRestTemplate = mock(RestTemplate.class);

        quoteApiService = new QuoteApiService(mockQuoteService);

        var restTemplateField = QuoteApiService.class.getDeclaredField("restTemplate");
        restTemplateField.setAccessible(true);
        restTemplateField.set(quoteApiService, mockRestTemplate);
    }

    @Test
    void testFetchRandomQuote_fromZenQuotes() {
        JsonNode mockResponse = mock(JsonNode.class);
        JsonNode[] responseArray = new JsonNode[]{mockResponse};

        when(mockResponse.get("q")).thenReturn(mock(JsonNode.class));
        when(mockResponse.get("q").asText()).thenReturn("Sample quote");
        when(mockResponse.get("a")).thenReturn(mock(JsonNode.class));
        when(mockResponse.get("a").asText()).thenReturn("Author");
        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode[].class))).thenReturn(responseArray);

        Map<String, String> result = quoteApiService.fetchRandomQuote();

        assertNotNull(result);
        assertEquals("Sample quote", result.get("text"));
        assertEquals("Author", result.get("author"));
    }

    @Test
    void testFetchRandomQuote_fromDummyJson() {
        JsonNode mockResponse = mock(JsonNode.class);

        when(mockResponse.has("quote")).thenReturn(true);
        when(mockResponse.get("quote")).thenReturn(mock(JsonNode.class));
        when(mockResponse.get("quote").asText()).thenReturn("Another sample quote");
        when(mockResponse.get("author")).thenReturn(mock(JsonNode.class));
        when(mockResponse.get("author").asText()).thenReturn("Another Author");
        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(mockResponse);

        Map<String, String> result = quoteApiService.fetchRandomQuote();

        assertNotNull(result);
        assertEquals("Another sample quote", result.get("text"));
        assertEquals("Another Author", result.get("author"));
    }

    @Test
    void testFetchRandomQuote_fallbackToDatabase() {
        Quote mockDatabaseQuote = new Quote();
        mockDatabaseQuote.setText("Database quote");
        mockDatabaseQuote.setAuthor("Database Author");

        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode[].class))).thenThrow(new RuntimeException("API failure"));
        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode.class))).thenThrow(new RuntimeException("API failure"));
        when(mockQuoteService.fetchRandomQuote()).thenReturn(mockDatabaseQuote);

        Map<String, String> result = quoteApiService.fetchRandomQuote();

        assertNotNull(result);
        assertEquals("Database quote", result.get("text"));
        assertEquals("Database Author", result.get("author"));
    }

    @Test
    void testFetchRandomQuote_allSourcesFail() {
        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode[].class))).thenThrow(new RuntimeException("API failure"));
        when(mockRestTemplate.getForObject(anyString(), eq(JsonNode.class))).thenThrow(new RuntimeException("API failure"));
        when(mockQuoteService.fetchRandomQuote()).thenThrow(new RuntimeException("No database quotes available"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> quoteApiService.fetchRandomQuote());
        assertEquals("No database quotes available", exception.getMessage());
    }
}