package soungegroup.soungeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import soungegroup.soungeapi.enums.GenreName;
import soungegroup.soungeapi.model.Genre;
import soungegroup.soungeapi.response.GenreSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(GenreName name);

    @Query("SELECT new soungegroup.soungeapi.response.GenreSimpleResponse(g.id, g.name) " +
            "FROM Genre g " +
            "JOIN g.usersWhoLike u " +
            "WHERE u.id = :id")
    List<GenreSimpleResponse> findByProfile(Long id);

    @Query("SELECT new soungegroup.soungeapi.response.GenreSimpleResponse(g.id, g.name) " +
            "FROM Genre g " +
            "JOIN g.groups gr " +
            "WHERE gr.id = :id")
    List<GenreSimpleResponse> findByPage(Long id);
}
