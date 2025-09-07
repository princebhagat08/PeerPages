package com.example.enotes_api.repository;

import com.example.enotes_api.entity.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends JpaRepository<Notes,Integer> {

    Page<Notes> findByCreatedBy(Integer userId, PageRequest request);
}
