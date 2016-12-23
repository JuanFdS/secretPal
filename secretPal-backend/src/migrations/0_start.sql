drop table default_gift if exists
drop table email_template if exists
drop table friend_relation if exists
drop table unsent_message if exists
drop table user if exists
drop table wish if exists
drop table worker if exists
create table default_gift (id bigint generated by default as identity, amount_default varchar(255) not null, gift_default varchar(255) not null, primary key (id))
create table email_template (id bigint generated by default as identity, active boolean, body_text varchar(255), date_of_birth varchar(255), full_name varchar(255), subject varchar(255), primary key (id))
create table friend_relation (id bigint generated by default as identity, gift_giver_id bigint, gift_receiver_id bigint, primary key (id))
create table unsent_message (id bigint generated by default as identity, body varchar(255), error varchar(255), recipient varchar(255), subject varchar(255), primary key (id))
create table user (id bigint generated by default as identity, password varchar(255) not null, user_name varchar(255) not null, worker_id bigint, primary key (id))
create table wish (id bigint generated by default as identity, gift varchar(255) not null, created_by_id bigint not null, worker_id bigint not null, primary key (id))
create table worker (id bigint generated by default as identity, date_of_birth binary(255) not null, e_mail varchar(255) not null, full_name varchar(255) not null, gift_date_received binary(255), wants_to_participate boolean not null, primary key (id))
alter table friend_relation add constraint FK1730sriv23h34lur1qov9becn foreign key (gift_giver_id) references worker
alter table friend_relation add constraint FKjw6t51tehmn3ts82g7g5trujb foreign key (gift_receiver_id) references worker
alter table user add constraint FK8bskgw4ax526qht8uje8oknrc foreign key (worker_id) references worker
alter table wish add constraint FKsot0l3wvapqe309es3g83pq0i foreign key (created_by_id) references worker
alter table wish add constraint FK4dinfkf5guo4q7gw7mubh7ufs foreign key (worker_id) references worker