CREATE TABLE `user` (
                        `userId` bigint NOT NULL AUTO_INCREMENT COMMENT '유저 식별자',
                        `name` varchar(4) NOT NULL COMMENT '이름',
                        `email` varchar(50) NOT NULL COMMENT '이메일',
                        `password` varchar(255) NOT NULL COMMENT '비밀번호',
                        `createdDate` bigint NOT NULL COMMENT '등록일',
                        `modifiedDate` bigint NOT NULL COMMENT '수정일',
                        PRIMARY KEY (`userId`)
);

CREATE TABLE `schedule` (
                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '일정 식별자',
                            `userId` bigint NOT NULL COMMENT '유저 식별자',
                            `title` varchar(10) NOT NULL COMMENT '할일 제목',
                            `contents` varchar(200) NOT NULL COMMENT '할일 내용',
                            `createdDate` bigint NOT NULL COMMENT '작성일',
                            `modifiedDate` bigint NOT NULL COMMENT '수정일',
                            PRIMARY KEY (`id`),
                            KEY `schedule_user_FK` (`userId`),
                            CONSTRAINT `schedule_user_FK` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`) ON DELETE CASCADE
);
