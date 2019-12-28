DROP TABLE IF EXISTS NEWS;

create table NEWS
(
    ID                 BINARY(36)            not null,
    USER_NAME          varchar(255)          not null,
    NEWS_TEXT          varchar(2000)         not null,
    LIKE_COUNT         int                   null,
    DISLIKE            int                   null,
    IS_PUBLISHED       boolean default false not null,
    CREATED_DATE       datetime              not null,
    CREATED_BY         varchar(255)          not null,
    LAST_MODIFIED_DATE datetime              null,
    LAST_MODIFIED_BY   int                   null
);

create unique index NEWS_ID_uindex
    on NEWS (ID);

alter table NEWS
    add constraint NEWS_pk
        primary key (ID);

