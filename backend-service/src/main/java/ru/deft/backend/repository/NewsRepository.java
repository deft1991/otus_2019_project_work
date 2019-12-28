package ru.deft.backend.repository;

/*
 * Created by sgolitsyn on 12/17/19
 */

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.deft.backend.model.News;

import java.util.UUID;

@Repository
public interface NewsRepository extends CrudRepository<News, UUID> {

    /**
     * Get earliest not published News
     *
     * @return earliest not published News
     */
    @Query(value = "select n.* from News n where n.IS_PUBLISHED = false order by n.CREATED_DATE asc limit 1", nativeQuery = true)
    News geNewsForPublish();
}
