INSERT INTO USERS VALUES('admin','adminpwd',true);
INSERT INTO AUTHORITIES VALUES('admin','ROLE_ADMIN');

INSERT INTO GROUPS VALUES(1,'GROUP_ADMIN');
INSERT INTO GROUPS VALUES(2,'GROUP_USERS');

INSERT INTO GROUP_AUTHORITIES VALUES(1,'ROLE_ADMIN');

-- INSERT INTO GROUP_MEMBERS VALUES(1, 'admin');

COMMIT;