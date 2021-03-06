show variables like 'character%';
# set character_set_database = utf8mb4;
# set character_set_server = utf8mb4;
show variables like '%time_zone%';

create database if not exists hook;
use hook;

drop table if exists carousel;
drop table if exists star;
drop table if exists bookVideo;
drop table if exists book;
drop table if exists bookCategory;
drop table if exists principalCfg;
drop table if exists user;

create table user
(
    id          bigint unsigned  NOT NULL AUTO_INCREMENT,
    username    varchar(200)     NOT NULL unique,
    password    varchar(200)     NOT NULL,
    avatarPath  varchar(200)     NOT NULL,
    roleId      integer unsigned not null,
    gmtCreate   datetime COMMENT '创建时间',
    gmtModified datetime COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB;

create table principalCfg
(
    id                    bigint unsigned  NOT NULL AUTO_INCREMENT,
    userId                bigint unsigned  NOT NULL unique,
    phoneNumber           varchar(200)     NOT NULL unique,
    licensePath           varchar(200)     NOT NULL unique,
    kindergartenName      varchar(200) default NULL,
    certificationStatusId integer unsigned not null,
    PRIMARY KEY (id),
    constraint fk_principalCfg_userId foreign key (userId) references user (id)
) ENGINE = InnoDB;

create table bookCategory
(
    id     bigint unsigned NOT NULL AUTO_INCREMENT,
    `desc` varchar(200)    not null unique,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

create table book
(
    id           bigint unsigned  NOT NULL AUTO_INCREMENT,
    name         varchar(200)     NOT NULL unique,
    categoryId   bigint unsigned  NOT NULL,
    coverPath    varchar(200)     NOT NULL unique,
    price        DECIMAL(9, 2)    not null,
    author       varchar(200)     not null,
    publisher    varchar(200)     not null,
    publishTime  varchar(20)      not null,
    isbn         varchar(200)     not null,
    introduction varchar(500)     NOT NULL unique,
    statusId     integer unsigned not null,
    gmtCreate    datetime COMMENT '创建时间',
    gmtModified  datetime COMMENT '更新时间',
    PRIMARY KEY (id),
    constraint fk_book_categoryId foreign key (categoryId) references bookCategory (id)
) ENGINE = InnoDB;

create table bookVideo
(
    id           bigint unsigned  NOT NULL AUTO_INCREMENT,
    bookId       bigint unsigned  NOT NULL,
    chapterNum   integer unsigned not null,
    chapterTitle varchar(200)     NOT NULL unique,
    videoPath    varchar(200)     NOT NULL unique,
    PRIMARY KEY (id),
    constraint fk_bookVideo_bookId foreign key (bookId) references book (id)
) ENGINE = InnoDB;

create table star
(
    id     bigint unsigned NOT NULL AUTO_INCREMENT,
    bookId bigint unsigned NOT NULL,
    userId bigint unsigned not null,
    PRIMARY KEY (id),
    constraint fk_star_bookId foreign key (bookId) references book (id),
    constraint fk_star_userId foreign key (userId) references user (id),
    constraint uk_star_bookId_userId unique key (bookId, userId)
) ENGINE = InnoDB;

create table carousel
(
    id          bigint unsigned NOT NULL AUTO_INCREMENT,
    chartPath   varchar(200)    NOT NULL unique,
    gmtCreate   datetime COMMENT '创建时间',
    gmtModified datetime COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB;


# dev data
insert into user (username, password, avatarPath, roleId)
values ('admin', '$2a$10$Zf4m4uW.3h8GMRWaNjgss.z6MtNkXB2DDZDd9xcJ4Eyeze6TRUXe2', '/111', 0);

insert into bookCategory (`desc`)
values ('童话书')
     , ('教课书');

insert into book (name, categoryId, coverPath, price, author, publisher, publishTime, isbn, introduction, statusId)
values ('灰姑娘', 1, 1, 1, 1, 1, 1, 1, 1, 1),
       ('小学语文', 2, 222, 1, 1, 1, 1, 1, 222, 1);

insert into bookVideo (bookId, chapterNum, chapterTitle, videoPath)
values (1, 1, '第一章 哈哈哈', 1);

insert into carousel (chartPath)
values (1);








