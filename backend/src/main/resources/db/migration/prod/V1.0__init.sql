ALTER DATABASE bootme CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `stack` (
                         `stack_id` bigint(20) NOT NULL AUTO_INCREMENT,
                         `name` varchar(100) NOT NULL,
                         `type` varchar(30) NOT NULL,
                         `icon` varchar(5000) NOT NULL,
                         `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                         `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                         PRIMARY KEY (`stack_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `oauth_info` (
                                            `oauth_info_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                            `oauth_provider` varchar(255) NOT NULL,
                                            `email` varchar(255) NOT NULL,
                                            `nickname` varchar(255) DEFAULT NULL,
                                            `name` varchar(255) DEFAULT NULL,
                                            `profile_image` varchar(255) DEFAULT NULL,
                                            `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                            `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                            PRIMARY KEY (`oauth_info_id`),
                                            UNIQUE KEY `UK_oauth_info_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `member` (
                          `member_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `oauth_info_id` bigint(20) DEFAULT NULL,
                          `email` varchar(255) NOT NULL,
                          `nickname` varchar(255) DEFAULT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          `profile_image` varchar(255) DEFAULT NULL,
                          `phone_number` varchar(255) DEFAULT NULL,
                          `job` varchar(100) DEFAULT NULL,
                          `role_type` varchar(255) NOT NULL,
                          `visits_count` bigint(20) NOT NULL,
                          `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                          `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                          PRIMARY KEY (`member_id`),
                          UNIQUE KEY `UK_mbmcqelty0fbrvxp1q58dn57t` (`email`),
                          KEY `FK_member_oauth_info` (`oauth_info_id`),
                          CONSTRAINT `FK_member_oauth_info` FOREIGN KEY (`oauth_info_id`) REFERENCES `oauth_info` (`oauth_info_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `member_stack` (
                                `member_stack_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `member_id` bigint(20) NOT NULL,
                                `stack_id` bigint(20) NOT NULL,
                                `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                PRIMARY KEY (`member_stack_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE IF NOT EXISTS `notification` (
                                `notification_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `member_id` bigint(20) DEFAULT NULL,
                                `event` varchar(255) DEFAULT NULL,
                                `message` varchar(255) DEFAULT NULL,
                                `checked` bit(1) NOT NULL,
                                `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                PRIMARY KEY (`notification_id`),
                                KEY `FK1xep8o2ge7if6diclyyx53v4q` (`member_id`),
                                CONSTRAINT `FK1xep8o2ge7if6diclyyx53v4q` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `company` (
                           `company_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL,
                           `service_name` varchar(255) DEFAULT NULL,
                           `url` varchar(255) DEFAULT NULL,
                           `service_url` varchar(255) DEFAULT NULL,
                           `logo_url` varchar(255) DEFAULT NULL,
                           `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                           `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                           PRIMARY KEY (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `course` (
                          `course_id` bigint(20) NOT NULL AUTO_INCREMENT,
                          `company_id` bigint(20) DEFAULT NULL,
                          `title` varchar(255) NOT NULL,
                          `name` varchar(255) NOT NULL,
                          `generation` int(11) NOT NULL,
                          `url` varchar(255) NOT NULL,
                          `location` varchar(255) DEFAULT NULL,
                          `cost` int(11) NOT NULL,
                          `period` int(11) NOT NULL,
                          `registration_start_date` date DEFAULT NULL,
                          `registration_end_date` date DEFAULT NULL,
                          `course_start_date` date DEFAULT NULL,
                          `course_end_date` date DEFAULT NULL,
                          `detail` text DEFAULT NULL,
                          `is_recommended` bit(1) NOT NULL,
                          `is_free` bit(1) NOT NULL,
                          `is_kdt` bit(1) NOT NULL,
                          `is_online` bit(1) NOT NULL,
                          `is_tested` bit(1) NOT NULL,
                          `is_prerequisite_required` bit(1) NOT NULL,
                          `is_register_open` bit(1) NOT NULL,
                          `clicks` int(11) NOT NULL,
                          `bookmarks` int(11) NOT NULL,
                          `status` enum('DISPLAY','HIDDEN','DELETED') DEFAULT 'DISPLAY',
                          `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                          `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                          PRIMARY KEY (`course_id`),
                          KEY `FKqoox7hj85mstdjri9gdve124s` (`company_id`),
                          CONSTRAINT `FKqoox7hj85mstdjri9gdve124s` FOREIGN KEY (`company_id`) REFERENCES `company` (`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `course_category` (
                                   `course_id` bigint(20) NOT NULL,
                                   `name` varchar(50) NOT NULL,
                                   KEY `course_category_FK` (`course_id`),
                                   CONSTRAINT `course_category_FK` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `course_stack` (
                                `course_stack_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                `course_id` bigint(20) NOT NULL,
                                `stack_id` bigint(20) NOT NULL,
                                `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                PRIMARY KEY (`course_stack_id`),
                                KEY `course_stack_FK` (`course_id`),
                                KEY `course_stack_FK_1` (`stack_id`),
                                CONSTRAINT `course_stack_FK` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
                                CONSTRAINT `course_stack_FK_1` FOREIGN KEY (`stack_id`) REFERENCES `stack` (`stack_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `post` (
                        `post_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `member_id` bigint(20) NOT NULL,
                        `topic` varchar(255) DEFAULT NULL,
                        `title` varchar(500) NOT NULL,
                        `content` text NOT NULL,
                        `likes` int(11) NOT NULL,
                        `clicks` int(11) NOT NULL,
                        `bookmarks` int(11) NOT NULL,
                        `status` enum('DISPLAY','HIDDEN','DELETED') NOT NULL DEFAULT 'DISPLAY',
                        `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                        `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                        PRIMARY KEY (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `vote` (
                        `vote_id` bigint(20) NOT NULL AUTO_INCREMENT,
                        `votable_type` varchar(255) NOT NULL,
                        `votable_id` bigint(20) NOT NULL,
                        `vote_type` varchar(255) NOT NULL,
                        `member_id` bigint(20) NOT NULL,
                        `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                        `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                        PRIMARY KEY (`vote_id`),
                        KEY `FKvotable_member` (`member_id`),
                        CONSTRAINT `FKvotable_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `comment` (
                           `comment_id` bigint(20) NOT NULL AUTO_INCREMENT,
                           `post_id` bigint(20) DEFAULT NULL,
                           `member_id` bigint(20) DEFAULT NULL,
                           `parent_id` bigint(20) DEFAULT NULL,
                           `content` text DEFAULT NULL,
                           `group_num` int(11) DEFAULT NULL,
                           `level_num` int(11) DEFAULT NULL,
                           `order_num` int(11) DEFAULT NULL,
                           `likes` int(11) DEFAULT NULL,
                           `status` enum('DISPLAY','HIDDEN','DELETED') DEFAULT NULL,
                           `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                           `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                           PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `bookmark` (
                            `bookmark_id` bigint(20) NOT NULL AUTO_INCREMENT,
                            `type` enum('COURSE','POST','COMMENT') NOT NULL,
                            `member_id` bigint(20) NOT NULL,
                            `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                            `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                            PRIMARY KEY (`bookmark_id`),
                            KEY `FKbookmark_member` (`member_id`),
                            CONSTRAINT `FKbookmark_member` FOREIGN KEY (`member_id`) REFERENCES `member` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `course_bookmark` (
                                   `course_bookmark_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                   `bookmark_id` bigint(20) NOT NULL,
                                   `course_id` bigint(20) NOT NULL,
                                   `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                   `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                   PRIMARY KEY (`course_bookmark_id`),
                                   KEY `FKtf4rcsxl5wwef0lgjq2yh763o` (`course_id`),
                                   CONSTRAINT `FKtf4rcsxl5wwef0lgjq2yh763o` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `post_bookmark` (
                                 `post_bookmark_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                 `bookmark_id` bigint(20) NOT NULL,
                                 `post_id` bigint(20) NOT NULL,
                                 `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                 `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                 PRIMARY KEY (`post_bookmark_id`),
                                 KEY `FKabc123xyz456` (`post_id`),
                                 CONSTRAINT `FKabc123xyz456` FOREIGN KEY (`post_id`) REFERENCES `post` (`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE IF NOT EXISTS `comment_bookmark` (
                                    `comment_bookmark_id` bigint(20) NOT NULL AUTO_INCREMENT,
                                    `bookmark_id` bigint(20) NOT NULL,
                                    `comment_id` bigint(20) NOT NULL,
                                    `created_at` datetime NOT NULL DEFAULT current_timestamp(),
                                    `modified_at` datetime NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                    PRIMARY KEY (`comment_bookmark_id`),
                                    KEY `FKtf7cscxl5wwf6lgj72yh899j` (`comment_id`),
                                    CONSTRAINT `FKtf7cscxl5wwf6lgj72yh899j` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
