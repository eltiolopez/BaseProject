-- Inserts:
insert into baseprojectdb.role (idrole, description) values ('ROLE_ADMIN', 'Role for administrators');
insert into baseprojectdb.role (idrole, description) values ('ROLE_BASIC', 'Role for regular users');
insert into baseprojectdb.usergroup (idusergroup, groupname, description) values ('1', 'Grupo Admin', 'Group for administrators');
insert into baseprojectdb.usergroup (idusergroup, groupname, description) values ('2', 'Grupo User', 'Group for regular users');
insert into baseprojectdb.usergroup_has_role(usergroup_idusergroup, role_idrole) values ('1', 'ROLE_ADMIN');
insert into baseprojectdb.usergroup_has_role(usergroup_idusergroup, role_idrole) values ('2', 'ROLE_BASIC');
insert into baseprojectdb.user (iduser, username, password, group_idgroup, name, surname1, surname2, email) values ('1', 'root', '$2a$10$kDe7xj7L8TNtytPr5AU1k.mfpSQZl2S2iG/iuugmoCjAkYtdPCnha', '1', 'Root', 'Root_1', 'Root_2', 'root@system.com');
insert into baseprojectdb.user (iduser, username, password, group_idgroup, name, surname1, surname2, email) values ('2', 'admin', '$2a$10$kDe7xj7L8TNtytPr5AU1k.mfpSQZl2S2iG/iuugmoCjAkYtdPCnha', '1', 'Admin', 'Admin_1', 'Admin_2', 'admin@system.com');
--insert into baseprojectdb.picture(idpicture, user_iduser, filekey, filepath, filename, fileextension, creationdate) values ('1', '1', 'anonymous-user', '/bp/images', 'anonymous-user', 'png', now()); 'Admin', 'Admin_1', 'Admin_2', 'admin@system.com');
insert into baseprojectdb.userpreferences (iduserpreferences, user_iduser, repositorypath, picture_idpicture, numresultsinpage) values ('1', '1', '/root', null, 10);
insert into baseprojectdb.userpreferences (iduserpreferences, user_iduser, repositorypath, picture_idpicture, numresultsinpage) values ('2', '2', '/admin', null, 10);
insert into baseprojectdb.guestbook(idguestbook, user_iduser, message, date) values ('1', '1', 'This is a test message', now());



-- Queries:
select * from baseprojectdb.guestbook;
select * from baseprojectdb.usergroup_has_role;
select * from baseprojectdb.role;
select * from baseprojectdb.userpreferences;
select * from baseprojectdb.picture;
select * from baseprojectdb.user;
select * from baseprojectdb.usergroup;