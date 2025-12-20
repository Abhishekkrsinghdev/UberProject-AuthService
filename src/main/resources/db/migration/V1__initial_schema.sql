
    create table booking (
        created_at datetime(6) not null,
        driver_id bigint,
        end_time datetime(6),
        id bigint not null auto_increment,
        passenger_id bigint,
        review_id bigint,
        start_time datetime(6),
        total_distance bigint,
        updated_at datetime(6) not null,
        booking_status enum ('ASSIGNING_DRIVER','CAB_ARRIVED','CANCELLED','COMPLETED','IN_RIDE','SCHEDULED'),
        primary key (id)
    ) engine=InnoDB;

    create table booking_review (
        rating float(53),
        created_at datetime(6) not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        content varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table driver (
        created_at datetime(6) not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        license_number varchar(255) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table passenger (
        created_at datetime(6) not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table passenger_review (
        id bigint not null,
        passenger_rating varchar(255) not null,
        passenger_review_content varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table booking 
       add constraint UK2c57floc70nhp4ehcsn9ctr71 unique (review_id);

    alter table driver 
       add constraint UK9yq25nknyh5y5gihylet1ypy9 unique (license_number);

    alter table booking 
       add constraint FKd3n9h18mu017cxfopghwkri7s 
       foreign key (driver_id) 
       references driver (id);

    alter table booking 
       add constraint FKabxd6qpdfkp11yan46jj1td90 
       foreign key (passenger_id) 
       references passenger (id);

    alter table booking 
       add constraint FKh1stionm0jgsyfg7fv98trhjj 
       foreign key (review_id) 
       references booking_review (id);

    alter table passenger_review 
       add constraint FKfum9d5y8kx247d5utum40ay6 
       foreign key (id) 
       references booking_review (id);
