package com.bootme.stack.repository;

import com.bootme.stack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {

    Boolean existsByName(String name);

    @Query("SELECT s.id FROM Stack s WHERE s.name = :name")
    Optional<Long> findIdByName(@Param("name") String name);

    Optional<Stack> findByName(String name);

}
