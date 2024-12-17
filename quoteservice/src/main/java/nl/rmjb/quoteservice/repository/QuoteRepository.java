package nl.rmjb.quoteservice.repository;

import nl.rmjb.quoteservice.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, String>, CustomQueryMethods {
    // Find a quote by its text and author
    Quote findByTextAndAuthor(String text, String author);
}

interface CustomQueryMethods {
    String FIND_MOST_LIKED_QUOTES_QUERY = "SELECT q FROM Quote q ORDER BY q.likes DESC";
    String FIND_POPULAR_QUOTES_QUERY = "SELECT q FROM Quote q WHERE q.likes > :threshold";
    String FIND_DISTINCT_AUTHORS_QUERY = "SELECT DISTINCT q.author FROM Quote q";

    // Fetch the most liked quotes, sorted in descending order of likes
    @Query(FIND_MOST_LIKED_QUOTES_QUERY)
    List<Quote> findAllOrderByLikesDesc();

    // Fetch all quotes with likes above a certain threshold
    @Query(FIND_POPULAR_QUOTES_QUERY)
    List<Quote> findWithLikesAbove(@Param("threshold") int threshold);

    // Fetch distinct authors of all quotes
    @Query(FIND_DISTINCT_AUTHORS_QUERY)
    List<String> findDistinctAuthors();
}