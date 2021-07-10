create table course(
    id serial primary key,
    name varchar(20),
    date_start varchar(20),
    date_end varchar (20),
    teacher_id integer,
    foreign key(teacher_id) references teacher(id)
);


create table lesson(
    id serial primary key,
    name varchar(20),
    day_of_week varchar(20),
    time varchar(20),
    course_id integer,
    foreign key(course_id) references course(id)
);

create table teacher(
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20),
    experience int
);

create table student(
    id serial primary key,
    first_name varchar(20),
    last_name varchar(20),
    number_of_group varchar(20)
);

create table student_course(
    student_id integer,
    foreign key (student_id) references student(id),
    course_id integer,
    foreign key (course_id) references course(id)
);

insert into teacher(last_name, first_name, experience) values('Низамиев', 'Рустам', 5);
insert into teacher(last_name, first_name, experience) values('Ференец', 'Александр', 8);
insert into teacher(last_name, first_name, experience) values('Сидиков', 'Марсель', 6);
insert into teacher(last_name, first_name, experience) values('Широкова', 'Елена',20);

insert into student(first_name, last_name, number_of_group) values ('Элина', 'Загидуллина', '11-001');
insert into student(first_name, last_name, number_of_group) values ('Милана', 'Махсотова', '11-001');
insert into student(first_name, last_name, number_of_group) values ('Регина', 'Тяпкина', '11-001');
insert into student(first_name, last_name, number_of_group) values ('Александра', 'Кузнецова', '11-002');

insert into course(name, date_start, date_end, teacher_id) values ('Java Lab', '29.06.2021', '12.07.2021', 3);
insert into course(name, date_start, date_end, teacher_id) values ('Английский язык', '03.09.2020', '28.12.2020', 1);
insert into course(name, date_start, date_end, teacher_id) values ('Мат анализ', '03.09.2020', '25.05.2021', 4);
insert into course(name, date_start, date_end, teacher_id) values ('PHP', '29.06.2021', '15.07.2021', 2);

insert into student_course(student_id, course_id) values (1, 1);
insert into student_course(student_id, course_id) values (1, 3);
insert into student_course(student_id, course_id) values (2, 1);
insert into student_course(student_id, course_id) values (2, 2);
insert into student_course(student_id, course_id) values (3, 4);
insert into student_course(student_id, course_id) values (4, 1);

insert into lesson(name, day_of_week, time, course_id) values ('SQL', 'пн', '14:00-15:30', 1);
insert into lesson(name, day_of_week, time, course_id) values ('JDBC 1', 'ср', '14:00-15:30', 1);
insert into lesson(name, day_of_week, time, course_id) values ('PHP', 'вт', '12:00-13:30', 4);
insert into lesson(name, day_of_week, time, course_id) values ('Ряды Фурье', 'пт', '14:00-15:30', 3);
insert into lesson(name, day_of_week, time, course_id) values ('Интеграл Римана', 'вт', '15:00-16:30', 3);
insert into lesson(name, day_of_week, time, course_id) values ('JDBC 2', 'пн', '14:00-15:30', 1);
insert into lesson(name, day_of_week, time, course_id) values ('Present Simple', 'чт', '14:00-15:30', 2);