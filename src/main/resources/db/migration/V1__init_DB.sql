-- USERS
DROP SEQUENCE IF EXISTS user_seq;
create sequence user_seq start 1 increment 1;

DROP TABLE IF EXISTS users CASCADE;
create table users
(
    id       bigint not null,
    archive  boolean,
    email    varchar(255),
    name     varchar(255),
    password varchar(255),
    role     varchar(255) check (role in ('CLIENT', 'ADMIN', 'MANAGER')),
    primary key (id)
);


-- BUCKET
DROP SEQUENCE IF EXISTS bucket_seq;
create sequence bucket_seq start 1 increment 1;

DROP TABLE IF EXISTS buckets CASCADE;
create table buckets
(
    id          bigint not null,
    user_id     bigint unique,
    create_date timestamp(8),
    primary key (id)
);

-- LINK BETWEEN BUCKET AND USER
alter table if exists buckets
    add constraint buckets
    foreign key (user_id) references users;

-- PRODUCT
DROP SEQUENCE IF EXISTS product_seq;
create sequence product_seq start 1 increment 1;

DROP TABLE IF EXISTS products CASCADE;
create table products
(
    id       bigint not null,
    price    numeric(38, 2),
    title    varchar(255),
    primary key (id)
);


-- CATEGORY
DROP SEQUENCE IF EXISTS category_seq;
create sequence category_seq start 1 increment 1;

DROP TABLE IF EXISTS categories CASCADE;
create table categories
(
    id       bigint not null,
    title    varchar(255),
    primary key (id)
);


-- CATEGORY AND PRODUCT
DROP TABLE IF EXISTS product_categories CASCADE;
create table product_categories
(
    category_id bigint not null,
    product_id  bigint not null
);

alter table if exists product_categories
    add constraint product_categories_fk_categories
    foreign key (category_id) references categories;
alter table if exists product_categories
    add constraint product_categories_fk_products
    foreign key (product_id) references products;


-- PRODUCT IN BUCKET
DROP TABLE IF EXISTS buckets_products CASCADE;
create table buckets_products
(
    bucket_id  bigint not null,
    product_id bigint not null
);

alter table if exists buckets_products
    add constraint buckets_products_fk_products
    foreign key (product_id) references products;
alter table if exists buckets_products
    add constraint buckets_products_fk_buckets
    foreign key (bucket_id) references buckets;


-- ORDERS
DROP SEQUENCE IF EXISTS order_seq;
create sequence order_seq start 1 increment 1;

DROP TABLE IF EXISTS orders CASCADE;
create table orders
(
    id       bigint not null,
    sum      numeric(38, 2),
    created  timestamp(6),
    updated  timestamp(6),
    user_id  bigint,
    address  varchar(255),
    status   varchar(255) check (status in ('NEW', 'APPROVED', 'CANCELED', 'PAID', 'CLOSED')),
    primary key (id)
);
alter table if exists orders
    add constraint orders
    foreign key (user_id) references users;


-- ORDER DETAILS
DROP SEQUENCE IF EXISTS order_details_seq;
create sequence order_details_seq start 1 increment 1;

DROP TABLE IF EXISTS orders_details CASCADE;
create table orders_details
(
    id         bigint not null,
    amount     numeric(38, 2),
    price      numeric(38, 2),
    details_id bigint not null unique,
    order_id   bigint,
    product_id bigint,
    primary key (id)
);
alter table if exists orders_details
    add constraint orders_details_fk_orders
    foreign key (order_id) references orders;
alter table if exists orders_details
    add constraint orders_details_fk_products
    foreign key (product_id) references products;


