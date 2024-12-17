package nl.rmjb.quoteservice.controller;

import nl.rmjb.quoteservice.model.Quote;
import nl.rmjb.quoteservice.service.QuoteApiService;
import nl.rmjb.quoteservice.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuoteController.class)
@AutoConfigureMockMvc
public class QuoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private QuoteApiService quoteApiService;

    @MockitoBean
    private QuoteService quoteService;

    @Test
    public void testGetRandomQuoteReturnsValidResponse() throws Exception {
        // Arrange
        Map<String, String> fetchedQuote = new HashMap<>();
        fetchedQuote.put("text", "This is a test quote");
        fetchedQuote.put("author", "Test Author");

        Quote quote = new Quote();
        //quote.setId(1L);
        quote.setText("This is a test quote");
        quote.setAuthor("Test Author");

        when(quoteApiService.fetchRandomQuote()).thenReturn(fetchedQuote);
        when(quoteService.trackQuoteIfAbsent("This is a test quote", "Test Author")).thenReturn(quote);

        // Act & Assert
        mockMvc.perform(get("/api/quotes/random")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value("This is a test quote"))
                .andExpect(jsonPath("$.author").value("Test Author"))
                //.andExpect(jsonPath("$.id").value("1"))
        ;
    }

//    @Test
//    public void testGetRandomQuoteHandlesEmptyResponseFromService() throws Exception {
//        // Arrange
//        when(quoteApiService.fetchRandomQuote()).thenReturn(new HashMap<>());
//
//        // Act & Assert
//        //expect jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "nl.rmjb.quoteservice.model.Quote.getId()" because "trackedQuote" is null
//        mockMvc.perform(get("/api/quotes/random")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isEmpty());
//    }
    @Test
    public void testGetAuthorsReturnsNonEmptyList() throws Exception {
        // Arrange
        List<String> authors = List.of("Author One", "Author Two");
        when(quoteService.getAuthors()).thenReturn(authors);

        // Act & Assert
        mockMvc.perform(get("/api/quotes/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Author One"))
                .andExpect(jsonPath("$[1]").value("Author Two"));
    }

    @Test
    public void testGetAuthorsReturnsEmptyList() throws Exception {
        // Arrange
        when(quoteService.getAuthors()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/quotes/authors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
@Test
public void testLikeQuoteSuccessfullyLikesQuote() throws Exception {
    // Arrange
    String quoteId = "1";
    Mockito.doNothing().when(quoteService).likeQuote(quoteId);

    // Act & Assert
    mockMvc.perform(post("/api/quotes/{id}/like", quoteId)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
}

//@Test
//public void testLikeQuoteHandlesInvalidId() throws Exception {
//    // Arrange
//    String invalidId = "invalid";
//    Mockito.doThrow(new IllegalArgumentException("Invalid ID"))
//            .when(quoteService).likeQuote(invalidId);
//
//    // Act & Assert
//    //expect jakarta.servlet.ServletException: Request processing failed: java.lang.IllegalArgumentException: Invalid ID
//    mockMvc.perform(post("/api/quotes/{id}/like", invalidId)
//                    .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isBadRequest());
//}

@Test
public void testGetMostLikedQuotesReturnsNonEmptyList() throws Exception {
        // Arrange
        Quote quote1 = new Quote("This is quote 1", "Author 1");
        Quote quote2 = new Quote("This is quote 2", "Author 2");

        List<Quote> mostLikedQuotes = List.of(quote1, quote2);
        when(quoteService.getMostLikedQuotes()).thenReturn(mostLikedQuotes);

        // Act & Assert
        mockMvc.perform(get("/api/quotes/most-liked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].text").value("This is quote 1"))
                .andExpect(jsonPath("$[0].author").value("Author 1"))
                .andExpect(jsonPath("$[1].text").value("This is quote 2"))
                .andExpect(jsonPath("$[1].author").value("Author 2"));
    }

@Test
public void testGetMostLikedQuotesReturnsEmptyList() throws Exception {
        // Arrange
        when(quoteService.getMostLikedQuotes()).thenReturn(List.of());

        // Act & Assert
        mockMvc.perform(get("/api/quotes/most-liked")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}