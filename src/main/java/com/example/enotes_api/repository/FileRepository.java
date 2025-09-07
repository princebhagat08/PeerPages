package com.example.enotes_api.repository;

import com.example.enotes_api.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileDetails,Integer> {
}
