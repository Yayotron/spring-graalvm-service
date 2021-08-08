create table if not exists demo_management.demo
(
    id         uuid,
    name       varchar(255),
    start_time timestamp,
    end_time   timestamp,

    primary key (id)
);

create table if not exists demo_management.participant
(
    id      uuid,
    name    varchar(255),
    email   varchar(255),
    demo_id uuid references demo_management.demo (id) on update cascade on delete cascade,

    primary key (id)
);

create index if not exists participants_demo_id on demo_management.participant using hash (demo_id);