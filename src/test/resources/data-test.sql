
insert into app_currencies (code) values ('USD');
insert into app_currencies (code) values ('UAH');
insert into app_currencies (code) values ('NonUsed');

insert into app_locales(lang) values('en');
insert into app_locales(lang) values('uk');
insert into app_locales (lang) values ('NonUsed');

insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('redstrike@gmail.com','Red','Strike','ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f','+380 63 108 7165','RedStriker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');
insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('darkstrike@gmail.com','Dark','Strike','f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8','+380 63 108 7165','DarkStriker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');
insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('masterstrike@gmail.com','Master','Strike','f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8','+380 63 108 9165','MasterStriker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');
insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('customerstrike@gmail.com','Customer','Strike','ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f','+380 63 108 9165','CustomerStriker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');
insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('secondstrike@gmail.com','Second','Strike','ffaad1ad2481038994f8d0ce7849ddaf7d6d25d242542c64419936a9bc5cbeb71e32bf88c926166f','+380 63 108 9185','SecondStriker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');
insert into users (email, first_name, last_name, password, phone, username,creation_date,last_modified_by,last_modified_date) values ('master2strike@gmail.com','Master2','Strike','f2a9f539b05c420042681336df6c130ed7a3f276001748856cfc3d604e7d50a16cbd0030e4fe7fe8','+380 63 108 9154','Master2Striker','2021-01-10T14:23:22Z','test','2021-02-10T14:23:22Z');

insert into wallets (money_amount, currency_id, name, user_id) values (100.0,1,'Default',1);
insert into wallets (money_amount, currency_id, name, user_id) values (100.0,1,'Default',2);
insert into wallets (money_amount, currency_id, name, user_id) values (100.0,1,'Special',1);
insert into wallets (money_amount, currency_id, name, user_id) values (120.0,1,'Default',4);
insert into wallets (money_amount, currency_id, name, user_id) values (20.0,1,'Default',5);

insert into roles (name) values ('UNVERIFIED');
insert into roles (name) values ('BLOCKED');
insert into roles (name) values ('CUSTOMER');
insert into roles (name) values ('MASTER');
insert into roles (name) values ('MANAGER');
insert into roles (name) values ('ADMIN');

insert into user_has_role (user_id, role_id) values (1,1);
insert into user_has_role (user_id, role_id) values (1,3);
insert into user_has_role (user_id, role_id) values (2,4);
insert into user_has_role (user_id, role_id) values (3,4);
insert into user_has_role (user_id, role_id) values (3,5);
insert into user_has_role (user_id, role_id) values (4,3);
insert into user_has_role (user_id, role_id) values (5,3);
insert into user_has_role (user_id, role_id) values (6,4);

insert into repair_categories (key_name) values ('notebook');
insert into repair_categories (key_name) values ('pc');
insert into repair_categories (key_name) values ('mobile');

insert into repair_works (key_name,category_id) values ('battery-replace',1);
insert into repair_works (key_name,category_id) values ('screen-replace',1);
insert into repair_works (key_name,category_id) values ('data-recovery',1);
insert into repair_works (key_name,category_id) values ('data-recovery',2);
insert into repair_works (key_name,category_id) values ('battery-replace',3);

insert into repair_work_prices (lower_border, upper_border, currency_id, work_id) values (20.40,20.40,1,1);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id) values (52.40,52.40,1,2);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id) values (12.10,12.10,1,3);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id) values (10.50,10.50,1,4);
insert into repair_work_prices (lower_border, upper_border, currency_id, work_id) values (22.40,22.40,1,5);

insert into repair_work_local_parts (name, language_id, work_id) values ('Battery replacement',1,1);
insert into repair_work_local_parts (name, language_id, work_id) values ('Заміна батареї',2,1);

insert into receipt_statuses (name) values ('CREATED');
insert into receipt_statuses (name) values ('WAIT_FOR_PAYMENT');
insert into receipt_statuses (name) values ('PAID');
insert into receipt_statuses (name) values ('IN_WORK');
insert into receipt_statuses (name) values ('DONE');
insert into receipt_statuses (name) values ('CANCELED');

insert into receipts (user_id,receipt_status_id,category_id,master_id,total_price, currency_id,note,creation_date, last_modified_by, last_modified_date) values (4,3,1,3,84.9,1,'Typical note','2022-01-10T14:23:22Z','test','2022-01-10T14:23:22Z');
insert into receipts (user_id,receipt_status_id,category_id,master_id,total_price, currency_id,note,creation_date, last_modified_by, last_modified_date) values (5,2,1,3,40.3,1,'Typical note','2021-12-14T16:00:40Z','test','2021-12-14T16:00:40Z');
insert into receipts (user_id,receipt_status_id,category_id,master_id,total_price, currency_id,note,creation_date, last_modified_by, last_modified_date) values (5,6,1,6,40.3,1,'Typical note','2021-10-14T17:00:40Z','test','2021-10-14T17:00:40Z');
insert into receipts (user_id,receipt_status_id,category_id,master_id,total_price, currency_id,note,creation_date, last_modified_by, last_modified_date) values (4,5,1,6,78.9,1,'Typical note','2022-02-10T17:34:31Z','test','2022-02-10T17:34:31Z');


insert into receipt_items (receipt_id,repair_work_id, price_amount) values (1,1,20.40);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (1,2,52.40);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (1,3,12.10);

insert into receipt_items (receipt_id,repair_work_id, price_amount) values (2,1,11.10);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (2,2,14.10);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (2,3,15.10);

insert into receipt_items (receipt_id,repair_work_id, price_amount) values (3,2,11.10);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (3,3,14.10);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (3,3,15.10);

insert into receipt_items (receipt_id,repair_work_id, price_amount) values (4,1,18.40);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (4,2,50.40);
insert into receipt_items (receipt_id,repair_work_id, price_amount) values (4,3,10.10);

insert into receipt_deliveries (receipt_id, city, country, local_address) values (1,'Kyiv','Ukraine','some street2');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (2,'Kyiv','Poland','some street3');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (3,'Kyiv','Ukraine','some street3');
insert into receipt_deliveries (receipt_id, city, country, local_address) values (4,'Kyiv','Ukraine','some street3');

insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (1,2,5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (2,3,5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (1,6,5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (2,6,5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (3,6,5);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (3,4,4);
insert into receipt_status_flows (from_status_id, to_status_id, role_id) values (4,5,4);



