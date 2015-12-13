-- /////Software Development Engineer////

INSERT INTO jobcode (positionname, type, description) VALUES ('Software Development Engineer', 'predefined', 'Software Development Engineer Job Responsibilities include developing information systems by designing, developing, and installing software solutions. Determines operational feasibility by evaluating analysis, problem definition, requirements, solution development, and proposed solutions. Documents and demonstrates solutions by developing documentation, flowcharts, layouts, diagrams, charts, code comments and clear code. Obtains and licenses software by obtaining required information from vendors; recommending purchases; testing and approving products. Develops software solutions by studying information needs; conferring with users; studying systems flow, data usage, and work processes; investigating problem areas; following the software development lifecycle. Software Development Engineer Skills include Analyzing Information , General Programming Skills, Software Design, Software Debugging, Software Documentation, Software Testing, Problem Solving, Teamwork, Software Development Fundamentals, Software Development Process, Software Requirements.');


-- /////Start Categories////

INSERT INTO category (title, type) VALUES ('Data Structures', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Programming Language', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('OOP', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Database Concepts', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Operating System', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

-- /////End Categories////


-- /////Start Data Structures Sub Categories////

INSERT INTO category (title, type) VALUES ('Trees', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Data Structures'));

INSERT INTO category (title, type) VALUES ('Graphs', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Data Structures'));

INSERT INTO category (title, type) VALUES ('Stacks, Queues and Lists', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Data Structures'));

-- /////End Data Structures Sub Categories////

-- /////Start Programming Language Sub Categories////

INSERT INTO category (title, type) VALUES ('Java', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Programming Language'));

INSERT INTO category (title, type) VALUES ('Python', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Programming Language'));

INSERT INTO category (title, type) VALUES ('C++', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Programming Language'));

INSERT INTO category (title, type) VALUES ('Javascript', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Programming Language'));

-- /////End Programming Language Sub Categories////

-- /////Strat OOP Sub Categories////

INSERT INTO category (title, type) VALUES ('Oops Concepts', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'OOP'));

-- /////End OOP Sub Categories////

-- /////Start Database Concepts Sub Categories////

INSERT INTO category (title, type) VALUES ('Database Fundamentals', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Database Concepts'));

-- /////End Database Concepts Sub Categories////


-- /////Software Development Engineer////