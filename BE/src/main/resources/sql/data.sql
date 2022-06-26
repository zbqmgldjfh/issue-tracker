insert into user (user_name, email, avatar_url)
values ('Shine', 'zbqmgldjfh@gmail.com', 'https://avatars.githubusercontent.com/something'),
       ('testUser1', 'testuser1@naver.com', 'test1!!'),
       ('testUser2', 'testuser2@naver.com', 'test2!!');

insert into label (title, description, background_color_code, font_color_code)
values ('title1', 'this is title 1', 'bg1', 'font1'),
       ('title2', 'this is title 2', 'bg2', 'font2'),
       ('title3', 'this is title 3', 'bg3', 'font3');

insert into milestone (title, description, due_date, is_open)
values ("mile1", "this is mile1", null, 1),
       ("mile2", "this is mile2", null, 1),
       ("mile3", "this is mile3", null, 1);

insert into issue (title, open, author_user_id, milestone_id)
values ('Issue-1', 1, 1, 1),
       ('Issue-2', 1, 1, 1),
       ('Issue-3', 1, 1, 2),
       ('Issue-4', 1, 1, 2),
       ('Issue-5', 1, 2, 3),
       ('Issue-6', 1, 3, 3),
       ('Issue-closed-1', 0, 1, 1),
       ('Issue-closed-2', 0, 1, 2);
