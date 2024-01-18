DROP TABLE IF EXISTS `members`;
DROP TABLE IF EXISTS `points`;
DROP TABLE IF EXISTS `settings`;
DROP TABLE IF EXISTS `albums`;
DROP TABLE IF EXISTS `concepts`;
DROP TABLE IF EXISTS `pets`;

CREATE TABLE `members` (
                           `member_id`	bigint	NOT NULL,
                           `social_id`	varchar(300)	NOT NULL,
                           `social_type`	varchar(30)	NOT NULL,
                           `member_role`	varchar(30)	NOT NULL,
                           `fcm_token`	varchar(300)	NULL,
                           `created_at`	datetime	NOT NULL,
                           `modified_at`	datetime	NOT NULL
);

CREATE TABLE `settings` (
                            `setting_id`	bigint	NOT NULL,
                            `member_id`	bigint	NOT NULL,
                            `notification_status`	boolean	NOT NULL,
                            `created_at`	datetime	NOT NULL,
                            `modified_at`	datetime	NOT NULL
);

CREATE TABLE `points` (
                          `coin_id`	bigint	NOT NULL,
                          `member_id`	bigint	NOT NULL,
                          `point_amount`	int	NOT NULL,
                          `created_at`	datetime	NOT NULL,
                          `modified_at`	datetime	NOT NULL
);

CREATE TABLE `concepts` (
                            `concept_id`	bigint	NOT NULL,
                            `concept_main_image`	varchar(200)	NOT NULL,
                            `concept_sub_image1`	varchar(200)	NOT NULL,
                            `concept_sub_image2`	varchar(200)	NOT NULL,
                            `concept_sub_image3`	varchar(200)	NOT NULL,
                            `concept_sub_image4`	varchar(200)	NOT NULL,
                            `concept_fail_image1`	varchar(200)	NOT NULL,
                            `concept_fail_image2`	varchar(200)	NOT NULL,
                            `concept_fail_image3`	varchar(200)	NOT NULL,
                            `concept_fail_image4`	varchar(200)	NOT NULL,
                            `created_at`	datetime	NOT NULL,
                            `modified_at`	datetime	NOT NULL
);

CREATE TABLE `pets` (
                        `pet_id`	bigint	NOT NULL,
                        `member_id`	bigint	NOT NULL,
                        `pet_name`	varchar(30)	NOT NULL,
                        `pet_photos`	json	NOT NULL,
                        `created_at`	datetime	NOT NULL,
                        `modified_at`	datetime	NOT NULL
);

CREATE TABLE `albums` (
                          `pet_id`	bigint	NOT NULL,
                          `member_id`	bigint	NOT NULL,
                          `concept_id`	bigint	NOT NULL,
                          `profile_images`	json	NULL,
                          `created_at`	datetime	NOT NULL,
                          `modified_at`	datetime	NOT NULL
);