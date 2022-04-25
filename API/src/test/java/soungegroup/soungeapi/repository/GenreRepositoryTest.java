package soungegroup.soungeapi.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for genre repository")
class GenreRepositoryTest {
    @Autowired
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Find by name returns genre when names match")
    void findByName_ReturnsGenre_WhenNamesMatch() {
        Genre genre = createGenre();
        genreRepository.save(genre);

        Optional<Genre> genreOptional = genreRepository.findByName(genre.getName());

        Assertions.assertTrue(genreOptional.isPresent());
        Assertions.assertEquals(genre.getName(), genreOptional.get().getName());
    }

    @Test
    @DisplayName("Find by name returns empty optional when names don't match")
    void findByName_ReturnsEmptyOptional_WhenNamesDontMatch() {
        Genre genre = createGenre();
        genreRepository.save(genre);

        Optional<Genre> genreOptional = genreRepository.findByName(GenreName.ROCK);

        Assertions.assertFalse(genreOptional.isPresent());
    }

    private Genre createGenre() {
        Genre genre = new Genre();
        genre.setName(GenreName.METAL);

        return genre;
    }
}