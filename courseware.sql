--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: assignments; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE assignments (
    id integer NOT NULL,
    name character varying,
    grade numeric,
    lesson_id integer,
    date_submitted timestamp without time zone
);


ALTER TABLE assignments OWNER TO "Guest";

--
-- Name: assignments_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE assignments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE assignments_id_seq OWNER TO "Guest";

--
-- Name: assignments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE assignments_id_seq OWNED BY assignments.id;


--
-- Name: course_ratings; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE course_ratings (
    id integer NOT NULL,
    course_id integer,
    student_id integer,
    comment character varying
);


ALTER TABLE course_ratings OWNER TO "Guest";

--
-- Name: course_ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE course_ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE course_ratings_id_seq OWNER TO "Guest";

--
-- Name: course_ratings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE course_ratings_id_seq OWNED BY course_ratings.id;


--
-- Name: courses; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE courses (
    id integer NOT NULL,
    name character varying,
    description character varying,
    subject character varying,
    teacher_id integer
);


ALTER TABLE courses OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE courses_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE courses_id_seq OWNER TO "Guest";

--
-- Name: courses_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE courses_id_seq OWNED BY courses.id;


--
-- Name: enrollment; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE enrollment (
    id integer NOT NULL,
    course_id integer,
    student_id integer
);


ALTER TABLE enrollment OWNER TO "Guest";

--
-- Name: enrollment_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE enrollment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE enrollment_id_seq OWNER TO "Guest";

--
-- Name: enrollment_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE enrollment_id_seq OWNED BY enrollment.id;


--
-- Name: lessons; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE lessons (
    id integer NOT NULL,
    name character varying,
    course_id integer
);


ALTER TABLE lessons OWNER TO "Guest";

--
-- Name: lessons_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE lessons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE lessons_id_seq OWNER TO "Guest";

--
-- Name: lessons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE lessons_id_seq OWNED BY lessons.id;


--
-- Name: students; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE students (
    id integer NOT NULL,
    user_id integer
);


ALTER TABLE students OWNER TO "Guest";

--
-- Name: students_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE students_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE students_id_seq OWNER TO "Guest";

--
-- Name: students_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE students_id_seq OWNED BY students.id;


--
-- Name: teacher_ratings; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE teacher_ratings (
    id integer NOT NULL,
    teacher_id integer,
    student_id integer,
    comment character varying
);


ALTER TABLE teacher_ratings OWNER TO "Guest";

--
-- Name: teacher_ratings_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE teacher_ratings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE teacher_ratings_id_seq OWNER TO "Guest";

--
-- Name: teacher_ratings_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE teacher_ratings_id_seq OWNED BY teacher_ratings.id;


--
-- Name: teachers; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE teachers (
    id integer NOT NULL,
    user_id integer
);


ALTER TABLE teachers OWNER TO "Guest";

--
-- Name: teachers_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE teachers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE teachers_id_seq OWNER TO "Guest";

--
-- Name: teachers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE teachers_id_seq OWNED BY teachers.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE users OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO "Guest";

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY assignments ALTER COLUMN id SET DEFAULT nextval('assignments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY course_ratings ALTER COLUMN id SET DEFAULT nextval('course_ratings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY courses ALTER COLUMN id SET DEFAULT nextval('courses_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY enrollment ALTER COLUMN id SET DEFAULT nextval('enrollment_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY lessons ALTER COLUMN id SET DEFAULT nextval('lessons_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY students ALTER COLUMN id SET DEFAULT nextval('students_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY teacher_ratings ALTER COLUMN id SET DEFAULT nextval('teacher_ratings_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY teachers ALTER COLUMN id SET DEFAULT nextval('teachers_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: assignments; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY assignments (id, name, grade, lesson_id, date_submitted) FROM stdin;
\.


--
-- Name: assignments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('assignments_id_seq', 1, false);


--
-- Data for Name: course_ratings; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY course_ratings (id, course_id, student_id, comment) FROM stdin;
\.


--
-- Name: course_ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('course_ratings_id_seq', 1, false);


--
-- Data for Name: courses; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY courses (id, name, description, subject, teacher_id) FROM stdin;
\.


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_id_seq', 1, false);


--
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY enrollment (id, course_id, student_id) FROM stdin;
\.


--
-- Name: enrollment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('enrollment_id_seq', 1, false);


--
-- Data for Name: lessons; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY lessons (id, name, course_id) FROM stdin;
\.


--
-- Name: lessons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('lessons_id_seq', 1, false);


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY students (id, user_id) FROM stdin;
\.


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('students_id_seq', 1, false);


--
-- Data for Name: teacher_ratings; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY teacher_ratings (id, teacher_id, student_id, comment) FROM stdin;
\.


--
-- Name: teacher_ratings_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('teacher_ratings_id_seq', 1, false);


--
-- Data for Name: teachers; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY teachers (id, user_id) FROM stdin;
\.


--
-- Name: teachers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('teachers_id_seq', 1, false);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, name) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 1, false);


--
-- Name: assignments_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY assignments
    ADD CONSTRAINT assignments_pkey PRIMARY KEY (id);


--
-- Name: course_ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY course_ratings
    ADD CONSTRAINT course_ratings_pkey PRIMARY KEY (id);


--
-- Name: courses_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY courses
    ADD CONSTRAINT courses_pkey PRIMARY KEY (id);


--
-- Name: enrollment_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY enrollment
    ADD CONSTRAINT enrollment_pkey PRIMARY KEY (id);


--
-- Name: lessons_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY lessons
    ADD CONSTRAINT lessons_pkey PRIMARY KEY (id);


--
-- Name: students_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY students
    ADD CONSTRAINT students_pkey PRIMARY KEY (id);


--
-- Name: teacher_ratings_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY teacher_ratings
    ADD CONSTRAINT teacher_ratings_pkey PRIMARY KEY (id);


--
-- Name: teachers_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY teachers
    ADD CONSTRAINT teachers_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

