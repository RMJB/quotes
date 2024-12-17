package nl.rmjb.quoteservice.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuoteTest {

    @Test
    void incrementLikes_ShouldIncreaseLikesByOne_WhenCalled() {
        // Arrange
        Quote quote = new Quote("Test quote", "Test author");
        int initialLikes = quote.getLikes();

        // Act
        quote.incrementLikes();

        // Assert
        assertEquals(initialLikes + 1, quote.getLikes());
    }

    @Test
    void incrementLikes_ShouldUpdateLikesProperly_WhenCalledMultipleTimes() {
        // Arrange
        Quote quote = new Quote("Test quote", "Test author");
        int initialLikes = quote.getLikes();

        // Act
        quote.incrementLikes();
        quote.incrementLikes();
        quote.incrementLikes();

        // Assert
        assertEquals(initialLikes + 3, quote.getLikes());
    }

    @Test
    void incrementLikes_ShouldWorkForNewlyCreatedQuote_WhenLikesAreDefault() {
        // Arrange
        Quote quote = new Quote("Fresh quote", "Test author");

        // Act
        quote.incrementLikes();

        // Assert
        assertEquals(1, quote.getLikes());
    }
}