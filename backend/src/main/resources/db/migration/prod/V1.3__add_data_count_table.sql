CREATE TABLE `data_count` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT,
                              `table_name` varchar(255) NOT NULL,
                              `status` varchar(255) NOT NULL,
                              `count` bigint(20) NOT NULL DEFAULT 0,
                              PRIMARY KEY (`id`),
                              UNIQUE KEY `idx_table_status` (`table_name`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;