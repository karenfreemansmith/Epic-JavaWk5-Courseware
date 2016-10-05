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
    date_submitted timestamp without time zone,
    content text,
    student_id integer
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
    course_id integer,
    reading character varying,
    lecture text
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

COPY assignments (id, name, grade, lesson_id, date_submitted, content, student_id) FROM stdin;
1	Build Your Portfolio	\N	1	\N	<p>--- or a ToDo List</p>	\N
2	Build Your Portfolio	9	1	2016-10-05 15:07:49.361647	<p>This is my portfoilo!!!! :)</p>	1
\.


--
-- Name: assignments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('assignments_id_seq', 2, true);


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
19	Folkloristics	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Folkloristics</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is the formal, academic discipline devoted to the study of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Folklore" href="https://en.wikipedia.org/wiki/Folklore">folklore</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. The term itself derives from the nineteenth-century German designation&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;">folkloristik</em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(i.e., folklore). Ultimately, the term&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;">folkloristics</em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is used to distinguish between the materials studied, folklore, and the study of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Folklore" href="https://en.wikipedia.org/wiki/Folklore">folklore</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, folkloristics. In scholarly usage,&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;">folkloristics</em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;represents an emphasis on the contemporary, social aspects of expressive culture, in contrast to the more literary or historical study of cultural texts.</span></p>	Literature	5
17	Historical geography	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Historical geography</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is the branch of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Geography" href="https://en.wikipedia.org/wiki/Geography">geography</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;that studies the ways in which geographic phenomena have changed over time. It is a synthesizing discipline which shares both topical and methodological similarities with&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="History" href="https://en.wikipedia.org/wiki/History">history</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Anthropology" href="https://en.wikipedia.org/wiki/Anthropology">anthropology</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Ecology" href="https://en.wikipedia.org/wiki/Ecology">ecology</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Geology" href="https://en.wikipedia.org/wiki/Geology">geology</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Environmental studies" href="https://en.wikipedia.org/wiki/Environmental_studies">environmental studies</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Literary studies" href="https://en.wikipedia.org/wiki/Literary_studies">literary studies</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and other fields. Although the majority of work in historical geography is considered&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Human geography" href="https://en.wikipedia.org/wiki/Human_geography">human geography</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, the field also encompasses studies of geographic change which are not primarily&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Human impact on the environment" href="https://en.wikipedia.org/wiki/Human_impact_on_the_environment">anthropogenic</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. Historical geography is often a major component of school and university curricula in geography and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Social studies" href="https://en.wikipedia.org/wiki/Social_studies">social studies</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. Current research in historical geography is practiced by scholars in more than forty countries.</span></p>	History	2
9	Weight Loss for Beauty Queens	Keep those beauty contest judges happy. Learn how to manage your weight through my personally patented system of binging and purging. Don't turn into a Miss Piggy! Personal one-on-one tutoring available.  For a small fee you can learn the art of not eating at all - plus receive a personally signed hat!!	Society	3
11	Intro to Programming	Learn the basics of programming, including HTML and CSS.	Technology	2
23	Children's literature	<p style="margin: 0.5em 0px; line-height: inherit; color: #252525; font-family: sans-serif; font-size: 14px;"><strong>Children's literature</strong>&nbsp;or&nbsp;<strong>juvenile literature</strong>&nbsp;includes stories, books, magazines, and poems that are enjoyed by&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Child" href="https://en.wikipedia.org/wiki/Child">children</a>. Modern children's literature is classified in two different ways: genre or the intended age of the reader.</p>\r\n<p style="margin: 0.5em 0px; line-height: inherit; color: #252525; font-family: sans-serif; font-size: 14px;">Children's literature can be traced to stories and songs, part of a wider&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Oral tradition" href="https://en.wikipedia.org/wiki/Oral_tradition">oral tradition</a>, that adults shared with children before publishing existed. The development of early children's literature, before printing was invented, is difficult to trace. Even after printing became widespread, many classic "children's" tales were originally created for adults and later adapted for a younger audience. Since the 15th century, a large quantity of literature, often with a moral or religious message, has been aimed specifically at children. The late nineteenth and early twentieth centuries became known as the "Golden Age of Children's Literature" as this period included the publication of many books acknowledged today as classics.</p>	Literature	5
3	Golfing 101	<p>Play some golf at one of the area's finest golf club and learn how to get the caddie to pay for your game. (Club fees not included.)</p>	Sports	3
13	test	<p>Learn how to make textiles with the use of a loom and a variety of materials -- including yarn, beads, reeds, and palm fronds.</p>	Crafts	1
2	Makeup Application With Cheetos	<p>Learn how to dust your face with Cheetos powder. (You must bring your own Cheetos)</p>	Crafts	3
7	How to Avoid Taxes for 18 years, LEGALLY	Totally legal, very smart!	Business	3
10	JavaScript for Beginners	Learn an integral programming language that is used by almost every website.	Technology	2
14	Statistics	Statistics is the study of the collection, analysis, interpretation, presentation, and organization of data. In applying statistics to, e.g., a scientific, industrial, or social problem, it is conventional to begin with a statistical population or a statistical model process to be studied.	Math	1
6	Construction or How To Build A Wall	<p>How to build a wall and get someone else to pay for it! Who'da thunk?</p>	Sports	3
8	Politics 101	Learn the secrets of American government.	History	3
4	Gold Digging 101	How to fine a good husband that will commit tax fraud for youd..	Sports	3
5	Gold Digging 101	How to fine a good husband that will commit tax fraud for youd..	Sports	3
1	another course	<p>How to fine a good husband that will commit tax fraud for you.</p>	Sports	3
22	Sikhism	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Sikhism</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(</span><span class="nowrap" style="white-space: nowrap; color: #252525; font-family: sans-serif; font-size: 14px;"><span class="IPA nopopups"><a style="text-decoration: none !important; color: #0b0080; background: none;" title="Help:IPA for English" href="https://en.wikipedia.org/wiki/Help:IPA_for_English">/<span style="border-bottom: 1px dotted;"><span title="/ˈ/ primary stress follows">ˈ</span><span title="'s' in 'sigh'">s</span><span title="/ɪ/ short 'i' in 'bid'">ɪ</span><span title="'k' in 'kind'">k</span><span title="/ɪ/ or /ə/ 'e' in 'roses'">ᵻ</span><span title="'z' in 'Zion'">z</span><span title="/əm/ 'm' in 'rhythm'">əm</span></span>/</a></span></span><span style="color: #252525; font-family: sans-serif; font-size: 14px;">), or&nbsp;</span><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Sikhi</strong><strong style="color: #252525; font-family: sans-serif; font-size: 11.2px; white-space: nowrap;">&nbsp;</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">(</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Punjabi language" href="https://en.wikipedia.org/wiki/Punjabi_language">Punjabi</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">:&nbsp;</span><span lang="pa" style="color: #252525; font-family: sans-serif; font-size: 14px;" xml:lang="pa">ਸਿੱਖੀ</span><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;"><span class="Unicode" title="International Alphabet of Sanskrit Transliteration">Sikkhī</span></em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><small style="font-size: 11.9px; color: #252525; font-family: sans-serif;">pronounced&nbsp;</small><span class="IPA" style="color: #252525; font-family: sans-serif; font-size: 14px;" title="Representation in the International Phonetic Alphabet (IPA)"><a style="text-decoration: none !important; color: #0b0080; background: none;" title="Help:IPA for Punjabi" href="https://en.wikipedia.org/wiki/Help:IPA_for_Punjabi">[ˈsɪkːʰiː]</a></span><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, from&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;">Sikh</em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, meaning a "disciple", or a "learner"), is a monotheistic religion that originated in the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Punjab (region)" href="https://en.wikipedia.org/wiki/Punjab_(region)">Punjab region</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="South Asia" href="https://en.wikipedia.org/wiki/South_Asia">South Asia</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;during the 15th century.</span><span style="color: #252525; font-family: sans-serif; font-size: 11.2px; white-space: nowrap;">&nbsp;</span><span style="color: #252525; font-family: sans-serif; font-size: 14px;">The fundamental beliefs of Sikhism, articulated in the sacred scripture&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Guru Granth Sahib" href="https://en.wikipedia.org/wiki/Guru_Granth_Sahib">Guru Granth Sahib</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, include faith and meditation on the name of the one creator, unity and equality of all humankind, engaging in&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Selfless service" href="https://en.wikipedia.org/wiki/Selfless_service">selfless service</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, striving for social justice for the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Sarbat da bhala" href="https://en.wikipedia.org/wiki/Sarbat_da_bhala">benefit and prosperity of all</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and honest conduct and livelihood while living a householder's life.</span><span style="color: #252525; font-family: sans-serif; font-size: 11.2px; white-space: nowrap;">&nbsp;</span><span style="color: #252525; font-family: sans-serif; font-size: 14px;">Being one of the youngest amongst the major world religions, with 25-28 million adherents worldwide, Sikhism is&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Major religious groups" href="https://en.wikipedia.org/wiki/Major_religious_groups#Religious_demographics">the ninth-largest religion in the world</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">.</span></p>	Society	1
21	Egyptian Arabic	<p style="margin: 0.5em 0px; line-height: inherit; color: #252525; font-family: sans-serif; font-size: 14px;"><span style="font-size: 14px;">Egyptian is a&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Variety (linguistics)" href="https://en.wikipedia.org/wiki/Variety_(linguistics)">variety</a>&nbsp;of the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Arabic languages" href="https://en.wikipedia.org/wiki/Arabic_languages">Arabic languages</a>&nbsp;of the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Semitic languages" href="https://en.wikipedia.org/wiki/Semitic_languages">Semitic</a>&nbsp;branch of the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Afroasiatic languages" href="https://en.wikipedia.org/wiki/Afroasiatic_languages">Afroasiatic language family</a>. It originated in the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Nile Delta" href="https://en.wikipedia.org/wiki/Nile_Delta">Nile Delta</a>&nbsp;in Lower Egypt around the capital&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Cairo" href="https://en.wikipedia.org/wiki/Cairo">Cairo</a>. Descended from the spoken Arabic which was brought to&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Egypt" href="https://en.wikipedia.org/wiki/Egypt">Egypt</a>&nbsp;during the seventh-century AD&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Muslim conquest of Egypt" href="https://en.wikipedia.org/wiki/Muslim_conquest_of_Egypt">Muslim conquest</a>, its development was influenced by the indigenous&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Coptic language" href="https://en.wikipedia.org/wiki/Coptic_language">Coptic</a>&nbsp;of&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Ancient Egypt" href="https://en.wikipedia.org/wiki/Ancient_Egypt">pre-Islamic Egypt</a>,</span><span style="font-size: 11.2px; white-space: nowrap;">&nbsp;</span><span style="font-size: 14px;">and later by other languages such as&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Turkish language" href="https://en.wikipedia.org/wiki/Turkish_language">Turkish</a><span style="font-size: 14px;">/</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Ottoman Turkish language" href="https://en.wikipedia.org/wiki/Ottoman_Turkish_language">Ottoman Turkish</a><span style="font-size: 14px;">,</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Italian language" href="https://en.wikipedia.org/wiki/Italian_language">Italian</a><span style="font-size: 14px;">,&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="French language" href="https://en.wikipedia.org/wiki/French_language">French</a><span style="font-size: 14px;">&nbsp;and&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="English language" href="https://en.wikipedia.org/wiki/English_language">English</a><span style="font-size: 14px;">. The 80 million Egyptians speak a&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Dialect continuum" href="https://en.wikipedia.org/wiki/Dialect_continuum">continuum of dialects</a><span style="font-size: 14px;">, among which Cairene is the most prominent. It is also understood across most of the&nbsp;</span><a class="mw-redirect" style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Arab World" href="https://en.wikipedia.org/wiki/Arab_World">Arabic speaking countries</a><span style="font-size: 14px;">&nbsp;due to the predominance of the Egyptian influence on the region as well as the Egyptian media, making it the most widely spoken and one of the most widely studied&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Varieties of Arabic" href="https://en.wikipedia.org/wiki/Varieties_of_Arabic">varieties of Arabic</a><span style="font-size: 14px;">.</span></p>	Languages	2
24	Sandpainting	<p style="margin: 0.5em 0px; line-height: inherit; color: #252525; font-family: sans-serif; font-size: 14px;"><strong>Sandpainting</strong>&nbsp;is the art of pouring colored sands, and powdered pigments from minerals or crystals, or pigments from other natural or synthetic sources onto a surface to make a fixed, or unfixed sand painting. Unfixed sand paintings have a long established cultural history in numerous social groupings around the globe, and are often temporary, ritual paintings prepared for religious or healing ceremonies. It is also referred to as drypainting.</p>\r\n<p style="margin: 0.5em 0px; line-height: inherit; color: #252525; font-family: sans-serif; font-size: 14px;">Drypainting is practiced by&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Native Americans in the United States" href="https://en.wikipedia.org/wiki/Native_Americans_in_the_United_States">Native Americans</a>&nbsp;in the Southwestern United States, by&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Tibet" href="https://en.wikipedia.org/wiki/Tibet">Tibetan</a>&nbsp;and Buddhist monks, as well as&nbsp;<a class="mw-redirect" style="text-decoration: none; color: #0b0080; background: none;" title="Australian Aborigines" href="https://en.wikipedia.org/wiki/Australian_Aborigines">Australian Aborigines</a>, and also by Latin Americans on certain Christian holy days.</p>	Art	5
20	Ancient Egypt	<p style="margin: 0.5em 0px; line-height: inherit; font-family: sans-serif; font-size: 14px;"><strong><a style="text-decoration: none; color: #0b0080; background: none;" title="Ancient Egypt" href="https://en.wikipedia.org/wiki/Ancient_Egypt">Ancient Egypt</a></strong>&nbsp;was an&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Ancient history" href="https://en.wikipedia.org/wiki/Ancient_history">ancient</a>&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Civilization" href="https://en.wikipedia.org/wiki/Civilization">civilization</a>&nbsp;of eastern&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="North Africa" href="https://en.wikipedia.org/wiki/North_Africa">North Africa</a>, concentrated along the lower reaches of the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Nile" href="https://en.wikipedia.org/wiki/Nile">Nile River</a>&nbsp;in what is now the modern country of<a style="text-decoration: none; color: #0b0080; background: none;" title="Egypt" href="https://en.wikipedia.org/wiki/Egypt">Egypt</a>. The civilization coalesced around 3150&nbsp;BC with the political unification of&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Upper and Lower Egypt" href="https://en.wikipedia.org/wiki/Upper_and_Lower_Egypt">Upper and Lower Egypt</a>&nbsp;under the first&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Pharaoh" href="https://en.wikipedia.org/wiki/Pharaoh">pharaoh</a>, and it developed over the next two millennia. Ancient Egypt reached its pinnacle during the&nbsp;<a class="mw-redirect" style="text-decoration: none; color: #0b0080; background: none;" title="New Kingdom" href="https://en.wikipedia.org/wiki/New_Kingdom">New Kingdom</a>, after which it entered a period of slow decline. Egypt was conquered by a succession of foreign powers in this late period, and the rule of the pharaohs officially ended in 31&nbsp;BC when the early&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Roman Empire" href="https://en.wikipedia.org/wiki/Roman_Empire">Roman Empire</a>&nbsp;conquered Egypt and made it&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Egypt (Roman province)" href="https://en.wikipedia.org/wiki/Egypt_(Roman_province)">a province</a>.</p>\r\n<p style="margin: 0.5em 0px; line-height: inherit; font-family: sans-serif; font-size: 14px;">Egypt has left a lasting legacy for all to see. Its&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Art of ancient Egypt" href="https://en.wikipedia.org/wiki/Art_of_ancient_Egypt">art</a>&nbsp;and&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Ancient Egyptian architecture" href="https://en.wikipedia.org/wiki/Ancient_Egyptian_architecture">architecture</a>&nbsp;has been widely copied, and its antiquities have been carried off to the far corners of the world. Egypt's monumental ruins have inspired the imaginations of travelers and writers for centuries. A newfound respect for antiquities and excavations in the early modern period led to the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Egyptology" href="https://en.wikipedia.org/wiki/Egyptology">scientific investigation</a>&nbsp;of Egyptian civilization and a greater appreciation of its cultural legacy for the earth.</p>	History	2
26	Egyptian Civil Code	<p><span style="color: #252525; font-family: sans-serif; font-size: 14px;">The Egyptian Civil Code has been the source of law and inspiration for numerous other Middle Eastern jurisdictions, including pre-</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Dictatorship" href="https://en.wikipedia.org/wiki/Dictatorship">dictatorship</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;kingdoms of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Kingdom of Libya" href="https://en.wikipedia.org/wiki/Kingdom_of_Libya">Libya</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Kingdom of Iraq" href="https://en.wikipedia.org/wiki/Kingdom_of_Iraq">Iraq</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(both drafted by El-Sanhuri himself and a team of native jurists under his guidance), in addition to&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Jordan" href="https://en.wikipedia.org/wiki/Jordan">Jordan</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(completed in 1976, after his death)&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Bahrain" href="https://en.wikipedia.org/wiki/Bahrain">Bahrain</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(2001), as well as&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Qatar" href="https://en.wikipedia.org/wiki/Qatar">Qatar</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(1971) (these last two merely inspired by his notions), and the commercial code of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Kuwait" href="https://en.wikipedia.org/wiki/Kuwait">Kuwait</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(drafted by El-Sanhuri). When Sudan drafted its own civil code in 1970, it was in large part copied from the Egyptian Civil Code with slight modifications. Today all Arab nations possessing modern civil codes, with the exception of Saudi Arabia and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Oman" href="https://en.wikipedia.org/wiki/Oman">Oman</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, are based fully or partly on the Egyptian Civil Code.</span></p>	Law & Government	2
18	Astronomy	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Astronomy</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, a&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Natural science" href="https://en.wikipedia.org/wiki/Natural_science">natural science</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, is the study of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Astronomical object" href="https://en.wikipedia.org/wiki/Astronomical_object">celestial objects</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;(such as&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Star" href="https://en.wikipedia.org/wiki/Star">stars</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Galaxy" href="https://en.wikipedia.org/wiki/Galaxy">galaxies</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Planet" href="https://en.wikipedia.org/wiki/Planet">planets</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Natural satellite" href="https://en.wikipedia.org/wiki/Natural_satellite">moons</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Asteroid" href="https://en.wikipedia.org/wiki/Asteroid">asteroids</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Comet" href="https://en.wikipedia.org/wiki/Comet">comets</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Nebula" href="https://en.wikipedia.org/wiki/Nebula">nebulae</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">) and processes (such as&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Supernova" href="https://en.wikipedia.org/wiki/Supernova">supernovae explosions</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Gamma ray burst" href="https://en.wikipedia.org/wiki/Gamma_ray_burst">gamma ray bursts</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Cosmic microwave background radiation" href="https://en.wikipedia.org/wiki/Cosmic_microwave_background_radiation">cosmic microwave background radiation</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">), the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Physics" href="https://en.wikipedia.org/wiki/Physics">physics</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Chemistry" href="https://en.wikipedia.org/wiki/Chemistry">chemistry</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Chronology of the Universe" href="https://en.wikipedia.org/wiki/Chronology_of_the_Universe">evolution</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;of such objects and processes, and more generally all phenomena that originate outside the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Atmosphere of Earth" href="https://en.wikipedia.org/wiki/Atmosphere_of_Earth">atmosphere of Earth</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. A related but distinct subject,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Physical cosmology" href="https://en.wikipedia.org/wiki/Physical_cosmology">physical cosmology</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, is concerned with studying the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Universe" href="https://en.wikipedia.org/wiki/Universe">Universe</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;as a whole.</span></p>	Science	5
30	Exotic Basket Weaving	<p>Learn to weave baskets in the exotic and tropical location of Fiji. &nbsp;(Plane tickets not included in tuition.)</p>	\N	3
27	Mineralogy	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Mineralogy</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is a subject of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Geology" href="https://en.wikipedia.org/wiki/Geology">geology</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;specializing in the scientific study of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Chemistry" href="https://en.wikipedia.org/wiki/Chemistry">chemistry</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Crystal structure" href="https://en.wikipedia.org/wiki/Crystal_structure">crystal structure</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and physical (including&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Optical mineralogy" href="https://en.wikipedia.org/wiki/Optical_mineralogy">optical</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">) properties of</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Mineral" href="https://en.wikipedia.org/wiki/Mineral">minerals</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;and mineralized&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Artifact (archaeology)" href="https://en.wikipedia.org/wiki/Artifact_(archaeology)">artifacts</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. Specific studies within mineralogy include the processes of mineral origin and formation, classification of minerals, their geographical distribution, as well as their utilization.</span></p>	Science	5
28	Shinto	<p style="margin: 0.5em 0px; line-height: inherit; font-family: sans-serif;"><span style="font-size: 14px;"><strong>Shinto</strong>&nbsp;(<span class="t_nihongo_kanji" lang="ja" xml:lang="ja">神道</span>&nbsp;<em>Shintō</em><sup class="t_nihongo_help noprint" style="line-height: 1; font-size: 11.2px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Help:Installing Japanese character sets" href="https://en.wikipedia.org/wiki/Help:Installing_Japanese_character_sets"><span class="t_nihongo_icon" style="color: #0000ee; font-weight: bold; font-stretch: normal; font-size: 9px; line-height: normal; padding: 0px 0.1em;">?</span></a></sup>), also&nbsp;<em><strong>kami-no-michi</strong></em>, is the&nbsp;<a class="mw-redirect" style="text-decoration: none; color: #0b0080; background: none;" title="Indigenous religion" href="https://en.wikipedia.org/wiki/Indigenous_religion">indigenous religion</a>&nbsp;of&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Japan" href="https://en.wikipedia.org/wiki/Japan">Japan</a>&nbsp;and the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Japanese people" href="https://en.wikipedia.org/wiki/Japanese_people">people of Japan</a>.</span><span style="font-size: 11.2px; white-space: nowrap;">&nbsp;</span><span style="font-size: 14px;">It is defined as an action-centered</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Religion" href="https://en.wikipedia.org/wiki/Religion">religion</a><span style="font-size: 14px;">,</span><span style="font-size: 14px;">&nbsp;focused on&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Ritual" href="https://en.wikipedia.org/wiki/Ritual">ritual</a><span style="font-size: 14px;">&nbsp;practices to be carried out diligently, to establish a connection between present-day Japan and its ancient past.</span><span style="font-size: 11.2px; white-space: nowrap;">&nbsp;</span><span style="font-size: 14px;">Shinto practices were first recorded and codified in the written historical records of the&nbsp;</span><em style="font-size: 14px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Kojiki" href="https://en.wikipedia.org/wiki/Kojiki">Kojiki</a></em><span style="font-size: 14px;">&nbsp;and&nbsp;</span><em style="font-size: 14px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Nihon Shoki" href="https://en.wikipedia.org/wiki/Nihon_Shoki">Nihon Shoki</a></em><span style="font-size: 14px;">&nbsp;in the 8th century. Still, these earliest Japanese writings do not refer to a unified "Shinto religion", but rather to a collection of native beliefs and&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Japanese mythology" href="https://en.wikipedia.org/wiki/Japanese_mythology">mythology</a><span style="font-size: 14px;">.</span><span style="font-size: 14px;">&nbsp;Shinto today is a term that applies to the religion of public&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Shinto shrine" href="https://en.wikipedia.org/wiki/Shinto_shrine">shrines</a><span style="font-size: 14px;">&nbsp;devoted to the worship of a multitude of&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Deity" href="https://en.wikipedia.org/wiki/Deity">gods</a><span style="font-size: 14px;">&nbsp;(</span><em style="font-size: 14px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Kami" href="https://en.wikipedia.org/wiki/Kami">kami</a></em><span style="font-size: 14px;">),</span><span style="font-size: 14px;">&nbsp;suited to various purposes such as war memorials and&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Harvest festival" href="https://en.wikipedia.org/wiki/Harvest_festival">harvest festivals</a><span style="font-size: 14px;">, and applies as well to various sectarian organizations. Practitioners express their diverse beliefs through a standard language and practice, adopting a similar style in dress and ritual, dating from around the time of the&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Nara period" href="https://en.wikipedia.org/wiki/Nara_period">Nara</a><span style="font-size: 14px;">&nbsp;and&nbsp;</span><a style="font-size: 14px; text-decoration: none; color: #0b0080; background: none;" title="Heian period" href="https://en.wikipedia.org/wiki/Heian_period">Heian</a><span style="font-size: 14px;">&nbsp;periods.</span></p>\r\n<p style="margin: 0.5em 0px; line-height: inherit; font-family: sans-serif; font-size: 14px;">The word&nbsp;<em>Shinto</em>&nbsp;("way of the gods") was adopted, originally as&nbsp;<em>Shindo</em>,&nbsp;from the written&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Chinese language" href="https://en.wikipedia.org/wiki/Chinese_language">Chinese</a>&nbsp;<em><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background: none;" title="Shendao" href="https://en.wikipedia.org/wiki/Shendao">Shendao</a></em>&nbsp;(神道,&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Pinyin" href="https://en.wikipedia.org/wiki/Pinyin">pinyin</a>:&nbsp;<em><span lang="zh-Latn-pinyin" xml:lang="zh-Latn-pinyin">sh&eacute;n d&agrave;o</span></em>),<sup id="cite_ref-Sokyo1962_7-0" class="reference" style="line-height: 1; unicode-bidi: isolate; white-space: nowrap; font-size: 11.2px;"><a style="text-decoration: none; color: #0b0080; background: none;" href="https://en.wikipedia.org/wiki/Portal:Shinto#cite_note-Sokyo1962-7">[7]</a></sup>combining two&nbsp;<em><a style="text-decoration: none; color: #0b0080; background: none;" title="Kanji" href="https://en.wikipedia.org/wiki/Kanji">kanji</a></em>: "<em>shin</em>"&nbsp;(<span class="t_nihongo_kanji" lang="ja" xml:lang="ja">神</span><sup class="t_nihongo_help noprint" style="line-height: 1; font-size: 11.2px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Help:Installing Japanese character sets" href="https://en.wikipedia.org/wiki/Help:Installing_Japanese_character_sets"><span class="t_nihongo_icon" style="color: #0000ee; font-weight: bold; font-stretch: normal; font-size: 9px; line-height: normal; padding: 0px 0.1em;">?</span></a></sup>), meaning "spirit" or&nbsp;<em>kami</em>; and "<em>tō</em>"&nbsp;(<span class="t_nihongo_kanji" lang="ja" xml:lang="ja">道</span><sup class="t_nihongo_help noprint" style="line-height: 1; font-size: 11.2px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Help:Installing Japanese character sets" href="https://en.wikipedia.org/wiki/Help:Installing_Japanese_character_sets"><span class="t_nihongo_icon" style="color: #0000ee; font-weight: bold; font-stretch: normal; font-size: 9px; line-height: normal; padding: 0px 0.1em;">?</span></a></sup>), meaning a philosophical path or study (from the Chinese word<em><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background: none;" title="D&agrave;o" href="https://en.wikipedia.org/wiki/D%C3%A0o">d&agrave;o</a></em>).&nbsp;The oldest recorded usage of the word&nbsp;<em>Shindo</em>&nbsp;is from the second half of the 6th century.&nbsp;<em><a style="text-decoration: none; color: #0b0080; background: none;" title="Kami" href="https://en.wikipedia.org/wiki/Kami">Kami</a></em>&nbsp;are defined in English as "spirits", "essences" or "gods", referring to the energy generating the phenomena.&nbsp;Since&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Japanese language" href="https://en.wikipedia.org/wiki/Japanese_language">Japanese language</a>&nbsp;doesn't distinguish between singular and plural,&nbsp;<em>kami</em>&nbsp;refers to the&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Divinity" href="https://en.wikipedia.org/wiki/Divinity">divinity</a>, or<a style="text-decoration: none; color: #0b0080; background: none;" title="Sacred" href="https://en.wikipedia.org/wiki/Sacred">sacred</a>&nbsp;essence, that manifests in multiple forms: rocks, trees, rivers, animals, places, and even people can be said to possess the nature of&nbsp;<em>kami</em>.&nbsp;Kami and people are not separate; they exist within the same world and share its interrelated complexity.</p>	Society	1
29	Comics	<p><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">Comics</strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is a&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Media (communication)" href="https://en.wikipedia.org/wiki/Media_(communication)">medium</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;used to express ideas by images, often combined with text or other visual information. Comics frequently takes the form of juxtaposed sequences of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Panel (comics)" href="https://en.wikipedia.org/wiki/Panel_(comics)">panels</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;of images. Often textual devices such as&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Speech balloon" href="https://en.wikipedia.org/wiki/Speech_balloon">speech balloons</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, captions, and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Onomatopoeia" href="https://en.wikipedia.org/wiki/Onomatopoeia">onomatopoeia</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;indicate dialogue, narration, sound effects, or other information. Size and arrangement of panels contribute to narrative pacing.&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Cartooning" href="https://en.wikipedia.org/wiki/Cartooning">Cartooning</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;and similar forms of illustration are the most common image-making means in comics;&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;"><a style="text-decoration: none; color: #0b0080; background: none;" title="Photonovel" href="https://en.wikipedia.org/wiki/Photonovel">fumetti</a></em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is a form which uses photographic images. Common forms of comics include&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Comic strip" href="https://en.wikipedia.org/wiki/Comic_strip">comic strips</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Editorial cartoon" href="https://en.wikipedia.org/wiki/Editorial_cartoon">editorial</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Gag cartoon" href="https://en.wikipedia.org/wiki/Gag_cartoon">gag cartoons</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Comic book" href="https://en.wikipedia.org/wiki/Comic_book">comic books</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. Since the late 20th century, bound volumes such as&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Graphic novel" href="https://en.wikipedia.org/wiki/Graphic_novel">graphic novels</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">,&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Comic album" href="https://en.wikipedia.org/wiki/Comic_album">comic albums</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and&nbsp;</span><em style="color: #252525; font-family: sans-serif; font-size: 14px;"><span class="Unicode" title="Japanese transliteration"><a style="text-decoration: none; color: #0b0080; background: none;" title="Tankōbon" href="https://en.wikipedia.org/wiki/Tank%C5%8Dbon">tankōbon</a></span></em><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;have become increasingly common, and online&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Webcomic" href="https://en.wikipedia.org/wiki/Webcomic">webcomics</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;have proliferated in the 21st century.</span></p>	Art	5
31	How to Snag Yourself a Billionaire	<p>How to find a good husband that will commit tax fraud for you. Personal instruction over dinner at the fine dinning establishment of your choice. (You pay for dinner.)</p>	\N	3
25	Politics of Egypt	<p><span style="color: #252525; font-family: sans-serif; font-size: 14px;">The&nbsp;</span><strong style="color: #252525; font-family: sans-serif; font-size: 14px;">politics of&nbsp;<a style="text-decoration: none; color: #0b0080; background: none;" title="Egypt" href="https://en.wikipedia.org/wiki/Egypt">Egypt</a></strong><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;is based on&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Republicanism" href="https://en.wikipedia.org/wiki/Republicanism">republicanism</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, with a&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Semi-presidential system" href="https://en.wikipedia.org/wiki/Semi-presidential_system">semi-presidential system</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;of government. Following the&nbsp;</span><a class="mw-redirect" style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Egyptian Revolution of 2011" href="https://en.wikipedia.org/wiki/Egyptian_Revolution_of_2011">Egyptian Revolution of 2011</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, and the resignation of&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="President of Egypt" href="https://en.wikipedia.org/wiki/President_of_Egypt">President</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Hosni Mubarak" href="https://en.wikipedia.org/wiki/Hosni_Mubarak">Hosni Mubarak</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, executive power was assumed by the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Supreme Council of the Armed Forces" href="https://en.wikipedia.org/wiki/Supreme_Council_of_the_Armed_Forces">Supreme Council of the Armed Forces</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">, which dissolved the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Parliament of Egypt" href="https://en.wikipedia.org/wiki/Parliament_of_Egypt">parliament</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">and suspended the&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Constitution" href="https://en.wikipedia.org/wiki/Constitution">constitution</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">. In 2012,&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Mohamed Morsi" href="https://en.wikipedia.org/wiki/Mohamed_Morsi">Mohamed Morsi</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;was&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Egyptian presidential election, 2012" href="https://en.wikipedia.org/wiki/Egyptian_presidential_election,_2012">elected</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;as Egypt's fifth&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="President of Egypt" href="https://en.wikipedia.org/wiki/President_of_Egypt">president</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;but was&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="2013 Egyptian coup d'&eacute;tat" href="https://en.wikipedia.org/wiki/2013_Egyptian_coup_d%27%C3%A9tat">deposed</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;by army chief General&nbsp;</span><a style="text-decoration: none; color: #0b0080; background-image: none; background-position: initial; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; font-family: sans-serif; font-size: 14px;" title="Abdel Fattah el-Sisi" href="https://en.wikipedia.org/wiki/Abdel_Fattah_el-Sisi">Abdel Fattah el-Sisi</a><span style="color: #252525; font-family: sans-serif; font-size: 14px;">&nbsp;who was subsequently elected as Egypt's sixth president in 2014.</span></p>	Law & Government	2
15	Pāli Tipitaka	The Pāli Tipitaka (Sanskrit: Tripiṭaka, three pitakas), which means "three baskets", refers to the Vinaya Pitaka, the Sutta Pitaka, and the Abhidhamma Pitaka. These constitute the oldest known canonical works of Buddhism. The Vinaya Pitaka contains disciplinary rules for the Buddhist monasteries. The Sutta Pitaka contains words attributed to the Buddha. The Abhidhamma Pitaka contain expositions and commentaries on the Sutta, and these vary significantly between Buddhist schools.  The Pāli Tipitaka is the only surviving early Tipitaka. According to some sources, some early schools of Buddhism had five or seven pitakas. Much of the material in the Canon is not specifically "Theravadin", but is instead the collection of teachings that this school preserved from the early, non-sectarian body of teachings. According to Peter Harvey, it contains material at odds with later Theravadin orthodoxy. He states: "The Theravadins, then, may have added texts to the Canon for some time, but they do not appear to have tampered with what they already had from an earlier period."	History	1
16	Mahayana sutras	The Mahayana sutras are a very broad genre of Buddhist scriptures that the Mahayana Buddhist tradition holds are original teachings of the Buddha. Some adherents of Mahayana accept both the early teachings (including in this the Sarvastivada Abhidharma, which was criticized by Nagarjuna and is in fact opposed to early Buddhist thought) and the Mahayana sutras as authentic teachings of Gautama Buddha, and claim they were designed for different types of persons and different levels of spiritual understanding.  The Mahayana sutras often claim to articulate the Buddha's deeper, more advanced doctrines, reserved for those who follow the bodhisattva path. That path is explained as being built upon the motivation to liberate all living beings from unhappiness. Hence the name Mahāyāna (lit., the Great Vehicle). The Theravada school does not treat the Mahayana Sutras as authoritative or authentic teachings of the Buddha.  Generally, scholars conclude that the Mahayana scriptures were composed from the 1st century CE onwards: "Large numbers of Mahayana sutras were being composed in the period between the beginning of the common era and the fifth century".	Society	1
\.


--
-- Name: courses_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('courses_id_seq', 31, true);


--
-- Data for Name: enrollment; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY enrollment (id, course_id, student_id) FROM stdin;
1	6	1
2	10	1
3	8	2
4	19	2
5	15	2
6	11	2
\.


--
-- Name: enrollment_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('enrollment_id_seq', 6, true);


--
-- Data for Name: lessons; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY lessons (id, name, course_id, reading, lecture) FROM stdin;
1	Lesson 1: HTML Review	10	http://jquery.com	<p>We will review some HTML and how the DOM works with JavaScript</p>
\.


--
-- Name: lessons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('lessons_id_seq', 1, true);


--
-- Data for Name: students; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY students (id, user_id) FROM stdin;
1	3
2	7
\.


--
-- Name: students_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('students_id_seq', 2, true);


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
1	1
2	2
3	4
4	5
5	6
\.


--
-- Name: teachers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('teachers_id_seq', 5, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY users (id, name) FROM stdin;
1	Ewa
2	Karen
3	Karen
4	Donald Trump
5	Brian
6	Elysia
7	George
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('users_id_seq', 7, true);


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

