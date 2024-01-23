drop sequence if exists user_seq;
create sequence user_seq start with 1 increment by 1;
create table users
(
    id        bigint not null,
    archive   boolean,
    bucket_id bigint unique,
    email     varchar(255),
    name      varchar(255),
    password  varchar(255),
    role      varchar(255) check (role in ('CLIENT', 'ADMIN', 'MANAGER')),
    seq_name  varchar(255),
    primary key (id)
);


drop sequence if exists bucket_seq;
create sequence bucket_seq start with 1 increment by 1;
create table buckets
(
    id       bigint not null,
    user_id  bigint unique,
    seq_name varchar(255),
    primary key (id)
);
alter table if exists buckets
    add constraint buckets foreign key (user_id) references users;
alter table if exists users
    add constraint users foreign key (bucket_id) references buckets;


create table buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null
);


drop sequence if exists category_seq;
create sequence category_seq start with 1 increment by 1;
create table categories
(
    id       bigint not null,
    seq_name varchar(255),
    title    varchar(255),
    primary key (id)
);

create table product_categories
(
    category_id bigint not null,
    product_id  bigint not null
);


drop sequence if exists product_seq;
create sequence product_seq start with 1 increment by 1;
create table products
(
    id       bigint not null,
    price    numeric(38, 2),
    seq_name varchar(255),
    title    varchar(255),
    primary key (id)
);

drop sequence if exists order_seq;
create sequence order_seq start with 1 increment by 1;
create table orders
(
    id       bigint not null,
    sum      numeric(38, 2),
    created  timestamp(6),
    updated  timestamp(6),
    user_id  bigint,
    address  varchar(255),
    seq_name varchar(255),
    status   varchar(255) check (status in ('NEW', 'APPROVED', 'CANCELED', 'PAID', 'CLOSED')),
    primary key (id)
);
alter table if exists orders
    add constraint orders foreign key (user_id) references users;


drop sequence if exists order_details_seq;
create sequence order_details_seq start with 1 increment by 1;
create table orders_details
(
    id         bigint not null,
    amount     numeric(38, 2),
    price      numeric(38, 2),
    details_id bigint not null unique,
    order_id   bigint,
    product_id bigint,
    seq_name   varchar(255),
    primary key (id)
);
alter table if exists orders_details
    add constraint orders_details_fk_orders foreign key (order_id) references orders;
alter table if exists orders_details
    add constraint orders_details_fk_products foreign key (product_id) references products;
alter table if exists orders_details
    add constraint orders_details_fk_order_details foreign key (details_id) references orders_details;


alter table if exists buckets_products
    add constraint buckets_products_fk_products foreign key (product_id) references products;
alter table if exists buckets_products
    add constraint buckets_products_fk_buckets foreign key (bucket_id) references buckets;
alter table if exists product_categories
    add constraint product_categories_fk_categories foreign key (category_id) references categories;
alter table if exists product_categories
    add constraint product_categories_fk_products foreign key (product_id) references products;
