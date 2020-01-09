CREATE SEQUENCE users_id_seq START 1;


create table NEWS
(
    ID                 uuid                  not null,
    USER_NAME          varchar(255)          not null,
    NEWS_TEXT          varchar(2000)         not null,
    LIKE_COUNT         int                   null,
    DISLIKE            int                   null,
    IS_APPROVED        boolean default false not null,
    IS_PUBLISHED       boolean default false not null,
    CREATED_DATE       date                  not null,
    CREATED_BY         varchar(255)          not null,
    LAST_MODIFIED_DATE date                  null,
    LAST_MODIFIED_BY   varchar(255)          null
);

create unique index NEWS_ID_uindex
    on NEWS (ID);

alter table NEWS
    add constraint NEWS_pk
        primary key (ID);

