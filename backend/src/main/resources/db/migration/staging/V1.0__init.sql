CREATE TABLE IF NOT EXISTS stack (
                                     stack_id BIGSERIAL PRIMARY KEY,
                                     name VARCHAR(100) NOT NULL,
    type VARCHAR(30) NOT NULL,
    icon VARCHAR(5000) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS oauth_info (
                                          oauth_info_id BIGSERIAL PRIMARY KEY,
                                          oauth_provider VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    nickname VARCHAR(255),
    name VARCHAR(255),
    profile_image VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS member (
                                      member_id BIGSERIAL PRIMARY KEY,
                                      oauth_info_id BIGINT REFERENCES oauth_info(oauth_info_id),
    email VARCHAR(255) NOT NULL UNIQUE,
    nickname VARCHAR(255),
    name VARCHAR(255),
    profile_image VARCHAR(255),
    phone_number VARCHAR(255),
    job VARCHAR(100),
    role_type VARCHAR(255) NOT NULL,
    visits_count BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS member_stack (
                                            member_stack_id BIGSERIAL PRIMARY KEY,
                                            member_id BIGINT NOT NULL,
                                            stack_id BIGINT NOT NULL,
                                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                            modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS notification (
                                            notification_id BIGSERIAL PRIMARY KEY,
                                            member_id BIGINT REFERENCES member(member_id),
    event VARCHAR(255),
    message VARCHAR(255),
    checked BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS company (
                                       company_id BIGSERIAL PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
    service_name VARCHAR(255),
    url VARCHAR(255),
    service_url VARCHAR(255),
    logo_url VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS course (
                                      course_id BIGSERIAL PRIMARY KEY,
                                      company_id BIGINT REFERENCES company(company_id),
    title VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    generation INTEGER NOT NULL,
    url VARCHAR(255) NOT NULL,
    location VARCHAR(255),
    cost INTEGER NOT NULL,
    period INTEGER NOT NULL,
    registration_start_date DATE,
    registration_end_date DATE,
    course_start_date DATE,
    course_end_date DATE,
    detail TEXT,
    is_recommended BOOLEAN NOT NULL,
    is_free BOOLEAN NOT NULL,
    is_kdt BOOLEAN NOT NULL,
    is_online BOOLEAN NOT NULL,
    is_tested BOOLEAN NOT NULL,
    is_prerequisite_required BOOLEAN NOT NULL,
    is_register_open BOOLEAN NOT NULL,
    clicks INTEGER NOT NULL,
    bookmarks INTEGER NOT NULL,
    status VARCHAR(50) DEFAULT 'DISPLAY' CHECK (status IN ('DISPLAY', 'HIDDEN', 'DELETED')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS course_category (
                                               course_id BIGINT NOT NULL REFERENCES course(course_id),
    name VARCHAR(50) NOT NULL
    );

CREATE TABLE IF NOT EXISTS course_stack (
                                            course_stack_id BIGSERIAL PRIMARY KEY,
                                            course_id BIGINT NOT NULL REFERENCES course(course_id),
    stack_id BIGINT NOT NULL REFERENCES stack(stack_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS post (
                                    post_id BIGSERIAL PRIMARY KEY,
                                    member_id BIGINT NOT NULL,
                                    topic VARCHAR(255),
    title VARCHAR(500) NOT NULL,
    content TEXT NOT NULL,
    likes INTEGER NOT NULL,
    clicks INTEGER NOT NULL,
    bookmarks INTEGER NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'DISPLAY' CHECK (status IN ('DISPLAY', 'HIDDEN', 'DELETED')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS vote (
                                    vote_id BIGSERIAL PRIMARY KEY,
                                    votable_type VARCHAR(255) NOT NULL,
    votable_id BIGINT NOT NULL,
    vote_type VARCHAR(255) NOT NULL,
    member_id BIGINT NOT NULL REFERENCES member(member_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comment (
                                       comment_id BIGSERIAL PRIMARY KEY,
                                       post_id BIGINT,
                                       member_id BIGINT,
                                       parent_id BIGINT,
                                       content TEXT,
                                       group_num INTEGER,
                                       level_num INTEGER,
                                       order_num INTEGER,
                                       likes INTEGER,
                                       status VARCHAR(50) CHECK (status IN ('DISPLAY', 'HIDDEN', 'DELETED')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS bookmark (
                                        bookmark_id BIGSERIAL PRIMARY KEY,
                                        type VARCHAR(50) NOT NULL CHECK (type IN ('COURSE', 'POST', 'COMMENT')),
    member_id BIGINT NOT NULL REFERENCES member(member_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS course_bookmark (
                                               course_bookmark_id BIGSERIAL PRIMARY KEY,
                                               bookmark_id BIGINT NOT NULL,
                                               course_id BIGINT NOT NULL REFERENCES course(course_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS post_bookmark (
                                             post_bookmark_id BIGSERIAL PRIMARY KEY,
                                             bookmark_id BIGINT NOT NULL,
                                             post_id BIGINT NOT NULL REFERENCES post(post_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS comment_bookmark (
                                                comment_bookmark_id BIGSERIAL PRIMARY KEY,
                                                bookmark_id BIGINT NOT NULL,
                                                comment_id BIGINT NOT NULL REFERENCES comment(comment_id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
    );
