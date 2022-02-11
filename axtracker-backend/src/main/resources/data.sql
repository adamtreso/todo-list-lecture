insert into work_log_entry(id, username, description, target_date, is_done)
values(1001, 'gipszjakab', 'Finish the project', sysdate(), true);
insert into work_log_entry(id, username, description, target_date, is_done)
values(1002, 'gipszjakab', 'Make the presentation', sysdate(), true);

insert into work_log_entry(id, username, description, target_date, is_done)
values(1003, 'ppal', 'Go shopping', sysdate(), true);
insert into work_log_entry(id, username, description, target_date, is_done)
values(1004, 'ppal', 'Make sandwiches', sysdate(), false);

insert into work_log_entry(id, username, description, target_date, is_done)
values(1005, 'tadam', 'Take the dog for a walk', sysdate(), false);




insert into users(id, username, password)
values(1, 'ppal', '$2a$10$b7Z..lIRXx0l8Ju/3zPTAuo8IZ315ymWKtJ2nBfKF8vIrKVcb8Aa2');
insert into users(id, username, password)
values(2, 'tadam', '$2a$10$AxS3hcGpXX5DRdyXTujaAecEv7H.YjmfrRKAcFH6iMxadjjI0eYnG');
insert into users(id, username, password)
values(3, 'gipszjakab', '$2a$10$AxS3hcGpXX5DRdyXTujaAecEv7H.YjmfrRKAcFH6iMxadjjI0eYnG');
