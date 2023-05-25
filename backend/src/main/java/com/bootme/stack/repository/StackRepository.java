package com.bootme.stack.repository;

import com.bootme.stack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StackRepository extends JpaRepository<Stack, Long> {

    Long findIdByName(String name);

}
