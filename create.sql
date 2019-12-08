# show variables like 'character%';
# set character_set_database = utf8mb4;
# set character_set_server = utf8mb4;
# show variables like '%time_zone%';

create database if not exists hook;
use hook;

drop table if exists bookVideo;
drop table if exists book;
drop table if exists bookCategory;
drop table if exists principalCfg;
drop table if exists user;
drop table if exists carousel;

create table user
(
    id          bigint unsigned  NOT NULL AUTO_INCREMENT,
    username    varchar(50)      NOT NULL unique,
    password    varchar(200)     NOT NULL,
    avatarPath  varchar(50)      NOT NULL unique,
    roleId      integer unsigned not null,
    star        varchar(500) default null,
    gmtCreate   datetime COMMENT '创建时间',
    gmtModified datetime COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB;

create table principalCfg
(
    id                    bigint unsigned  NOT NULL AUTO_INCREMENT,
    userId                bigint unsigned  NOT NULL,
    phoneNumber           varchar(200)     NOT NULL unique,
    licensePath           varchar(50)      NOT NULL unique,
    kindergartenName      varchar(50) default NULL,
    certificationStatusId integer unsigned not null,
    PRIMARY KEY (id),
    foreign key fk_principalCfg_userId (userId) references user (id)
) ENGINE = InnoDB;

create table bookCategory
(
    id     bigint unsigned NOT NULL AUTO_INCREMENT,
    `desc` varchar(50)     not null unique,
    PRIMARY KEY (id)
) ENGINE = InnoDB;

create table book
(
    id           bigint unsigned  NOT NULL AUTO_INCREMENT,
    name         varchar(50)      NOT NULL unique,
    categoryId   bigint unsigned  NOT NULL unique,
    coverPath    varchar(50)      NOT NULL unique,
    price        DECIMAL(9, 2)    not null,
    author       varchar(50)      not null,
    publisher    varchar(200)     not null,
    publishTime  varchar(20)      not null,
    isbn         varchar(50)      not null,
    introduction varchar(500)     NOT NULL unique,
    statusId     integer unsigned not null,
    gmtCreate    datetime COMMENT '创建时间',
    gmtModified  datetime COMMENT '更新时间',
    PRIMARY KEY (id),
    foreign key fk_book_categoryId (categoryId) references bookCategory (id)
) ENGINE = InnoDB;

create table bookVideo
(
    id           bigint unsigned NOT NULL AUTO_INCREMENT,
    bookId       bigint unsigned NOT NULL,
    chapterNum   integer unsigned not null,
    chapterTitle varchar(50)     NOT NULL unique,
    videoPath    varchar(50)     NOT NULL unique,
    PRIMARY KEY (id),
    foreign key fk_bookVideo_bookId (bookId) references book (id)
) ENGINE = InnoDB;

create table carousel
(
    id          bigint unsigned NOT NULL AUTO_INCREMENT,
    chartPath   varchar(50)     NOT NULL unique,
    gmtCreate   datetime COMMENT '创建时间',
    gmtModified datetime COMMENT '更新时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB;
