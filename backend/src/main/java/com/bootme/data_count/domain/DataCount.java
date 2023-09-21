package com.bootme.data_count.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "data_count")
public class DataCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false)
    private String tableName;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "count", nullable = false)
    private Long count;

    @Builder
    public DataCount(String tableName, String status, Long count) {
        this.tableName = tableName;
        this.status = status;
        this.count = count;
    }

    public void updateCount(Long count) {
        this.count = count;
    }

}
