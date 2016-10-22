# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table connection_request (
  cid                       bigint auto_increment not null,
  sender_uid                bigint,
  receiver_uid              bigint,
  status                    varchar(8),
  constraint ck_connection_request_status check (status in ('ACCEPTED','WAITING')),
  constraint pk_connection_request primary key (cid))
;

create table profile (
  pid                       bigint auto_increment not null,
  fname                     varchar(255),
  lname                     varchar(255),
  company                   varchar(255),
  constraint pk_profile primary key (pid))
;

create table user (
  uid                       bigint auto_increment not null,
  email                     varchar(255),
  password                  varchar(255),
  profile_pid               bigint,
  constraint uq_user_profile_pid unique (profile_pid),
  constraint pk_user primary key (uid))
;


create table user_connection (
  user_id                        bigint not null,
  connection_id                  bigint not null,
  constraint pk_user_connection primary key (user_id, connection_id))
;
alter table connection_request add constraint fk_connection_request_sender_1 foreign key (sender_uid) references user (uid) on delete restrict on update restrict;
create index ix_connection_request_sender_1 on connection_request (sender_uid);
alter table connection_request add constraint fk_connection_request_receiver_2 foreign key (receiver_uid) references user (uid) on delete restrict on update restrict;
create index ix_connection_request_receiver_2 on connection_request (receiver_uid);
alter table user add constraint fk_user_profile_3 foreign key (profile_pid) references profile (pid) on delete restrict on update restrict;
create index ix_user_profile_3 on user (profile_pid);



alter table user_connection add constraint fk_user_connection_user_01 foreign key (user_id) references user (uid) on delete restrict on update restrict;

alter table user_connection add constraint fk_user_connection_user_02 foreign key (connection_id) references user (uid) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table connection_request;

drop table profile;

drop table user;

drop table user_connection;

SET FOREIGN_KEY_CHECKS=1;

