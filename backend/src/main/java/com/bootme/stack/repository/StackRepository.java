package com.bootme.stack.repository;

import com.bootme.stack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {

    Boolean existsByName(String name);

    Optional<Stack> findByName(String name);

    List<Stack> findByNameIn(List<String> names);

    List<Stack> findByType(String type);

}
