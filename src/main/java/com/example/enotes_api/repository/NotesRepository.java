package com.example.enotes_api.repository;

import com.example.enotes_api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer> {

    Page<Notes> findByCreatedBy(Integer userId, PageRequest request);


    List<Notes> findByCreatedByAndIsDeletedTrue(Integer userId);

    Page<Notes> findByCreatedByAndIsDeletedFalse(Integer userId, PageRequest request);

    List<Notes> findByIsDeletedAndDeletedOn(boolean b, LocalDateTime cutOffDate);


    @Query("select n from Notes n where (Lower(n.title) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.description) like lower(concat('%',:keyword,'%')) "
            + "or lower(n.category.name) like lower(concat('%',:keyword,'%'))) "
            + "and n.isDeleted=false "
            + "and n.createdBy=:userId")
    Page<Notes> searchNotes(@Param("keyword") String keyword, @Param("userId")Integer userId, Pageable pageable);
}
