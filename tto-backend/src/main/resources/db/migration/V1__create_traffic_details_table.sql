CREATE TABLE traffic_details (
    uuid varchar(255) primary key not null,
    origin varchar(255) not null,
    destination varchar(255) not null,
    timestamp timestamp with time zone not null ,
    duration int,
    distance int,
    duration_in_traffic int
);
