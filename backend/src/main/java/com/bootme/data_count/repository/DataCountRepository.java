package com.bootme.data_count.repository;

import com.bootme.data_count.domain.DataCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataCountRepository extends JpaRepository<DataCount, Long>, QuerydslPredicateExecutor<DataCount> {

    Optional<DataCount> findByTableNameAndStatus(String tableName, String status);

}
