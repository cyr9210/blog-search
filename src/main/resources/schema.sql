create table keyword_history
(
    id         bigint generated by default as identity,
    created_at timestamp,
    updated_at timestamp,
    count      bigint,
    keyword    varchar(255),
    primary key (id),

    constraint uk_keyword unique (keyword)
);

create index idx_count on keyword_history (count);