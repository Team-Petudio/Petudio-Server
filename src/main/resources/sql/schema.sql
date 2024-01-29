DROP TABLE IF EXISTS `members`;
DROP TABLE IF EXISTS `settings`;
DROP TABLE IF EXISTS `points`;
DROP TABLE IF EXISTS `pets`;
DROP TABLE IF EXISTS `concepts`;
DROP TABLE IF EXISTS `albums`;
DROP TABLE IF EXISTS `posts`;


CREATE TABLE `members`
(
    `member_id`   bigint AUTO_INCREMENT PRIMARY KEY,
    `social_id`   varchar(300) NOT NULL,
    `social_type` varchar(30)  NOT NULL,
    `member_role` varchar(30)  NOT NULL,
    `email`       varchar(100) NOT NULL,
    `fcm_token`   varchar(300) NULL,
    `created_at`  timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `settings`
(
    `setting_id`          bigint AUTO_INCREMENT PRIMARY KEY,
    `member_id`           bigint    NOT NULL,
    `notification_status` boolean   NOT NULL,
    `created_at`          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `points`
(
    `point_id`     bigint AUTO_INCREMENT PRIMARY KEY,
    `member_id`    bigint    NOT NULL,
    `point_amount` int       NOT NULL,
    `created_at`   timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `concepts`
(
    `concept_id`                 bigint AUTO_INCREMENT PRIMARY KEY,
    `concept_info`               varchar(30)  NOT NULL,
    `concept_main_image_uri`     varchar(200) NOT NULL,
    `concept_success_image1_uri` varchar(200) NOT NULL,
    `concept_success_image2_uri` varchar(200) NOT NULL,
    `concept_success_image3_uri` varchar(200) NOT NULL,
    `concept_success_image4_uri` varchar(200) NOT NULL,
    `concept_fail_image1_uri`    varchar(200) NOT NULL,
    `concept_fail_image2_uri`    varchar(200) NOT NULL,
    `concept_fail_image3_uri`    varchar(200) NOT NULL,
    `concept_fail_image4_uri`    varchar(200) NOT NULL,
    `created_at`                 timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`                timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `pets`
(
    `pet_id`      bigint AUTO_INCREMENT PRIMARY KEY,
    `member_id`   bigint      NOT NULL,
    `pet_name`    varchar(30) NOT NULL,
    `pet_photos`  json        NOT NULL,
    `created_at`  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `albums`
(
    `album_id`       bigint AUTO_INCREMENT PRIMARY KEY,
    `pet_id`         bigint    NOT NULL,
    `member_id`      bigint    NOT NULL,
    `concept_id`     bigint    NOT NULL,
    `profile_images` json      NULL,
    `created_at`     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `posts`
(
    `post_id`           bigint AUTO_INCREMENT PRIMARY KEY,
    `pet_id`            bigint       NOT NULL,
    `member_id`         bigint       NOT NULL,
    `concept_id`        bigint       NOT NULL,
    `profile_image_uri` varchar(300) NOT NULL,
    `like`              int          NOT NULL,
    `created_at`        timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `modified_at`       timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
