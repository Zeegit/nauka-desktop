DROP TABLE IF EXISTS public.work_calendar;
DROP TABLE IF EXISTS public.employee;

-------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.department;

CREATE TABLE public.department
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 10),
    name character varying(255) NOT NULL,
    CONSTRAINT department_pkey PRIMARY KEY (id)
);


-------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.position;

CREATE TABLE public.position
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 10),
    name character varying(255) NOT NULL,
    CONSTRAINT position_pkey PRIMARY KEY (id)
);


-------------------------------------------------------------------------------

CREATE TABLE public.employee
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 10),
    service_number character varying(255) NOT NULL,
    first_name character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    birth_date date NOT NULL,
    sex boolean NOT NULL,
    remote boolean NOT NULL,
    address character varying(255) NOT NULL,
    photo oid,
    position_id bigint NOT NULL,
    department_id bigint NOT NULL,

    CONSTRAINT employee_pkey PRIMARY KEY (id),

    CONSTRAINT fk_employee_position FOREIGN KEY (position_id)
        REFERENCES public.position (id),

    CONSTRAINT fk_employee_department FOREIGN KEY (department_id)
        REFERENCES public.department (id)
);

-------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.work_code;

CREATE TABLE public.work_code
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 20),
    name character varying(255) NOT NULL,
    description character varying(255) NOT NULL,
    CONSTRAINT work_code_pkey PRIMARY KEY (id)
);


-------------------------------------------------------------------------------

DROP TABLE IF EXISTS public.holiday;
DROP TABLE IF EXISTS public.holiday_type;

CREATE TABLE public.holiday_type
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 10),
    name character varying(255),
    CONSTRAINT holiday_type_pkey PRIMARY KEY (id)
);

-------------------------------------------------------------------------------


CREATE TABLE public.holiday
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1),
    hdate date NOT NULL,
    name character varying(255),
	holiday_type_id bigint NOT NULL,
    CONSTRAINT holiday_pkey PRIMARY KEY (id),
	
	CONSTRAINT fk_holiday_holiday_type FOREIGN KEY (holiday_type_id)
        REFERENCES public.holiday_type (id)
);



-------------------------------------------------------------------------------



CREATE TABLE public.work_calendar
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1),
    work_date date NOT NULL,
    employee_id bigint NOT NULL,
    work_code_id bigint NOT NULL,

    CONSTRAINT work_calendar_pkey PRIMARY KEY (id),

    CONSTRAINT fk_work_calendar_employee FOREIGN KEY (employee_id)
        REFERENCES public.employee (id),

    CONSTRAINT fk_work_calendar_work_code FOREIGN KEY (work_code_id)
        REFERENCES public.work_code (id)

);

-------------------------------------------------------------------------------

insert into work_code (id, name, description) values (1, 'Я',  'полный рабочий день');
insert into work_code (id, name, description) values (2, 'Н',  'отсутствие на рабочее место по невыясненным причинам');
insert into work_code (id, name, description) values (3, 'В',  'выходные и праздничные дни');
insert into work_code (id, name, description) values (4, 'Рв', 'работа в праздничные и выходные дни; а также работа в праздничные и выходные дни, при нахождении в командировке');
insert into work_code (id, name, description) values (5, 'Б',  'дни временной нетрудоспособности');
insert into work_code (id, name, description) values (6, 'К',  'командировочные дни; а также, выходные (нерабочие) дни при нахождении в командировке,когда сотрудник отдыхает, в соответствии с графиком работы ООО «Наука» в командировке');
insert into work_code (id, name, description) values (7, 'ОТ', 'ежегодный основной оплаченный отпуск');
insert into work_code (id, name, description) values (8, 'До', 'неоплачиваемый отпуск (отпуск за свой счет');
insert into work_code (id, name, description) values (9, 'Хд', 'хозяйственный день');
insert into work_code (id, name, description) values (10, 'У',  'отпуск на период обучения');
insert into work_code (id, name, description) values (11, 'Ож', 'Отпуск по уходу за ребенком');


insert into holiday_type (id, name) values (1, 'выходной');
insert into holiday_type (id, name) values (2, 'предпразничный');
insert into holiday_type (id, name) values (3, 'празничный');

insert into department (id, name) values (1, 'Автоматизация антипригарных стульев');
insert into department (id, name) values (2, 'Разработка цветочных фремворков');
insert into department (id, name) values (3, 'PR станков и горшков');

insert into position (id, name) values (1, 'программист');
insert into position (id, name) values (2, 'бухгалтер');

insert into holiday (hdate, name, holiday_type_id) values ('01.05.2020', 'Праздник Весны и Труда', 3);
insert into holiday (hdate, name, holiday_type_id) values ('02.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('03.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('04.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('05.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('08.05.2020', '', 2);
insert into holiday (hdate, name, holiday_type_id) values ('09.05.2020', 'День Победы', 3);
insert into holiday (hdate, name, holiday_type_id) values ('10.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('11.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('16.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('17.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('23.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('24.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('30.05.2020', '', 1);
insert into holiday (hdate, name, holiday_type_id) values ('31.05.2020', '', 1);


insert into employee (id, first_name, last_name, service_number, birth_date, sex, remote, address, position_id, department_id)
values (1, 'Василий', 'Алибабаевич', '1', '10.05.1990', false, false, 'СПб', 2, 1);
insert into employee (id, first_name, last_name, service_number, birth_date, sex, remote, address, position_id, department_id)
values (2, 'Петр', 'Семенов', '2', '12.04.1994', false, true, 'Москва', 1, 1);
insert into employee (id, first_name, last_name, service_number, birth_date, sex, remote, address, position_id, department_id)
values (3, 'Иван', 'Иванов', '3', '12.04.1994', false, true, 'Москва', 1, 2);
insert into employee (id, first_name, last_name, service_number, birth_date, sex, remote, address, position_id, department_id)
values (4, 'Вася', 'Васильев', '4', '12.04.1994', false, true, 'Москва', 1, 2);

insert into work_calendar (work_date, employee_id, work_code_id) values ('01.05.2020', 2, 4);
insert into work_calendar (work_date, employee_id, work_code_id) values ('31.05.2020', 2, 4);
insert into work_calendar (work_date, employee_id, work_code_id) values ('11.05.2020', 1, 4);
insert into work_calendar (work_date, employee_id, work_code_id) values ('12.05.2020', 1, 1);
insert into work_calendar (work_date, employee_id, work_code_id) values ('13.05.2020', 1, 1);
insert into work_calendar (work_date, employee_id, work_code_id) values ('12.05.2020', 3, 1);
insert into work_calendar (work_date, employee_id, work_code_id) values ('13.05.2020', 3, 1);
insert into work_calendar (work_date, employee_id, work_code_id) values ('12.05.2020', 4, 1);
insert into work_calendar (work_date, employee_id, work_code_id) values ('13.05.2020', 4, 1);

		