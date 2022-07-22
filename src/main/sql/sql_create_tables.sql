create table players
(
    id   serial
        constraint players_pk
            primary key,
    name varchar(20) unique not null
);

create table matches
(
    id      serial primary key,
    player1 int references players (id),
    player2 int references players (id),
    winner  int references players (id)
);

create index matches_player1_index
    on matches (player1);

create index matches_player2_index
    on matches (player2);

