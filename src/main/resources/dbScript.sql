
CREATE SEQUENCE user_id_seq;
CREATE TABLE users (
	id integer NOT NULL DEFAULT nextval('user_id_seq') PRIMARY KEY,
	fname varchar(50) NOT NULL,
	lname varchar(50),
	password varchar(50) NOT NULL,
	login varchar(50) NOT NULL UNIQUE,
	role varchar(25)
);
ALTER SEQUENCE user_id_seq OWNED BY users.id;

-- jobcode related tables
CREATE SEQUENCE jobcode_id_seq;
CREATE TABLE jobcode (
	id integer NOT NULL DEFAULT nextval('jobcode_id_seq') PRIMARY KEY, 
	positionName varchar(50) NOT NULL, 
	type varchar(25) NOT NULL, 
	description varchar(200) NOT NULL, 
	predefinedJobID integer references jobcode(id) NULL, 
	userid integer references users(id) NULL
);
ALTER SEQUENCE jobcode_id_seq OWNED BY jobcode.id;
ALTER TABLE jobcode ALTER COLUMN description TYPE text;
ALTER TABLE jobcode ALTER COLUMN description SET NOT NULL;


CREATE SEQUENCE category_id_seq;
CREATE TABLE category (
	id integer NOT NULL DEFAULT nextval('category_id_seq') PRIMARY KEY, 
	title varchar(50) NOT NULL, 
	type varchar(25) NOT NULL
);
ALTER SEQUENCE category_id_seq OWNED BY category.id;

CREATE SEQUENCE jobCategories_id_seq;
CREATE TABLE jobCategories (
	id integer NOT NULL DEFAULT nextval('jobCategories_id_seq') PRIMARY KEY, 
	jobID integer references jobcode(id) NOT NULL,
	categoryID integer references category(id) NOT NULL,
	parentCategoryID integer references category(id) NULL,
	UNIQUE(jobID, categoryID)
);
ALTER SEQUENCE category_id_seq OWNED BY category.id;



CREATE SEQUENCE trainingModule_id_seq;
CREATE TABLE trainingModule (
	id integer NOT NULL DEFAULT nextval('trainingModule_id_seq') PRIMARY KEY,
	title varchar(50) NOT NULL,
	jobcodeID integer references jobcode(id) NOT NULL,
	userID integer references users(id) NOT NULL,
	focus varchar(50) NULL,
	level varchar(50) NULL,
	content varchar(5000) NULL
);
ALTER SEQUENCE trainingModule_id_seq OWNED BY trainingModule.id;
ALTER TABLE trainingModule ALTER COLUMN content TYPE text;
ALTER TABLE trainingModule ALTER COLUMN content SET NOT NULL;


CREATE SEQUENCE trainingModuleCategories_id_seq;
CREATE TABLE trainingModuleCategories (
	id integer NOT NULL DEFAULT nextval('trainingModuleCategories_id_seq') PRIMARY KEY,
	trainingModuleID integer references trainingModule(id) NOT NULL,
	categoryID integer references category(id) NOT NULL,
	content varchar(5000) NOT NULL
);
ALTER SEQUENCE trainingModuleCategories_id_seq OWNED BY trainingModuleCategories.id;
ALTER TABLE trainingModuleCategories ALTER COLUMN content TYPE text;
ALTER TABLE trainingModuleCategories ALTER COLUMN content SET NOT NULL;


CREATE SEQUENCE question_id_seq;
CREATE TABLE question (
	id integer NOT NULL DEFAULT nextval('question_id_seq') PRIMARY KEY,
	jobID integer references jobCode(id) NOT NULL,
	categoryID integer references category(id) NULL,
	questionText varchar(1000) NOT NULL,
	type varchar(25) NOT NULL,
	isTrueOrFalse boolean NOT NULL,
	isMultipleChoice boolean NOT NULL,
	correctAnswer varchar(500) NULL,
	focus varchar(50) NULL,
	level varchar(50) NULL,
	userID integer references users(id) NULL
);
ALTER SEQUENCE question_id_seq OWNED BY question.id;
ALTER TABLE question DROP COLUMN jobID;

CREATE SEQUENCE option_id_seq;
CREATE TABLE option (
	id integer NOT NULL DEFAULT nextval('option_id_seq') PRIMARY KEY,
	questionID integer references question(id) NOT NULL,
	text varchar(500) NOT NULL
);
ALTER SEQUENCE option_id_seq OWNED BY option.id;
ALTER TABLE option ALTER COLUMN text TYPE text;
ALTER TABLE option ALTER COLUMN text SET NOT NULL;
ALTER TABLE option ADD COLUMN iscorrectoption boolean DEFAULT false;


CREATE SEQUENCE testSet_id_seq;
CREATE TABLE testSet (
	id integer NOT NULL DEFAULT nextval('testSet_id_seq') PRIMARY KEY,
	jobCodeID integer references jobCode(id) NOT NULL,
	userID integer references users(id) NULL,
	cutoff decimal NOT NULL,
	level varchar(50) NULL
);
ALTER SEQUENCE testSet_id_seq OWNED BY testSet.id;
ALTER TABLE testSet ADD COLUMN title varchar(200);
ALTER table testset ADD COLUMN status varchar(50) DEFAULT 'active';


CREATE SEQUENCE testSetCategories_id_seq;
CREATE TABLE testSetCategories (
	id integer NOT NULL DEFAULT nextval('testSetCategories_id_seq') PRIMARY KEY,
	testSetID integer references testSet(id) ON DELETE CASCADE NOT NULL,
	categoryID integer references category(id) NOT NULL,
	cutoff decimal NULL,
	weightage decimal NULL
);
ALTER SEQUENCE testSetCategories_id_seq OWNED BY testSetCategories.id;

ALTER TABLE testSetCategories ADD COLUMN title varchar(200);
ALTER table testsetcategories ADD COLUMN status varchar(50) DEFAULT 'active';

CREATE SEQUENCE testSetQuestions_id_seq;
CREATE TABLE testSetQuestions (
	id integer NOT NULL DEFAULT nextval('testSetQuestions_id_seq') PRIMARY KEY,
	testSetCategoryID integer references testSetCategories(id) ON DELETE CASCADE NOT NULL,
	questionID integer references question(id) NOT NULL
		
);
ALTER SEQUENCE testSetQuestions_id_seq OWNED BY testSetQuestions.id;


CREATE SEQUENCE testSetAttempt_id_seq;
CREATE TABLE testSetAttempt (
	id integer NOT NULL DEFAULT nextval('testSetAttempt_id_seq') PRIMARY KEY,
	userID integer references users(id) NOT NULL,
	testSetID integer references testSet(id) NOT NULL,
	date bigint NOT NULL,
	score float NOT NULL
);
ALTER SEQUENCE testSetAttempt_id_seq OWNED BY testSetQuestions.id;


CREATE SEQUENCE categoryWiseScore_id_seq;
CREATE TABLE categoryWiseScore (
	id integer NOT NULL DEFAULT nextval('categoryWiseScore_id_seq') PRIMARY KEY,
	testSetAttemptID integer references testSetAttempt(id) NOT NULL,
	categoryID integer references category(id) NOT NULL,
	score float NOT NULL,
	isCutoffReached boolean NOT NULL DEFAULT FALSE
);
ALTER SEQUENCE categoryWiseScore_id_seq OWNED BY testSetQuestions.id;


CREATE SEQUENCE questionWiseRecord_id_seq;
CREATE TABLE questionWiseRecord (
	id integer NOT NULL DEFAULT nextval('questionWiseRecord_id_seq') PRIMARY KEY,
	testSetAttemptID integer references testSetAttempt(id) NOT NULL,
	categoryID integer references category(id) NOT NULL,
	questionID integer references question(id) NOT NULL,
	userAnswerOption integer references option(id) NULL,
	isCorrectAnswer boolean
);
ALTER SEQUENCE questionWiseRecord_id_seq OWNED BY testSetQuestions.id;


CREATE SEQUENCE userJobPreference_id_seq;
CREATE TABLE userJobPreference (
	id integer NOT NULL DEFAULT nextval('userJobPreference_id_seq') PRIMARY KEY,
	userID integer references users(id) NOT NULL,
	jobCodeID integer references jobCode(id) NOT NULL
);
ALTER SEQUENCE userJobPreference_id_seq OWNED BY userJobPreference.id;


CREATE SEQUENCE trainingModuleRecommendation_id_seq;
CREATE TABLE trainingModuleRecommendation (
	id integer NOT NULL DEFAULT nextval('trainingModuleRecommendation_id_seq') PRIMARY KEY,
	userID integer references users(id) NOT NULL,
	trainingModuleID integer references trainingModule(id) NOT NULL
);
ALTER SEQUENCE trainingModuleRecommendation_id_seq OWNED BY trainingModuleRecommendation.id;


CREATE SEQUENCE trainingModuleCompletionStatus_id_seq;
CREATE TABLE trainingModuleCompletionStatus (
	id integer NOT NULL DEFAULT nextval('trainingModuleCompletionStatus_id_seq') PRIMARY KEY,
	userID integer references users(id) NOT NULL,
	trainingModuleID integer references trainingModule(id) NOT NULL,
	status varchar(50) DEFAULT 'Not Completed',
	dateCompleted bigint DEFAULT NULL
);
ALTER SEQUENCE trainingModuleCompletionStatus_id_seq OWNED BY trainingModuleCompletionStatus.id;


ALTER TABLE question DROP COLUMN correctanswer;


ALTER TABLE trainingmodule ADD CONSTRAINT unique_title UNIQUE (title);

-- Alter queries for adding column to users table
ALTER TABLE users ADD COLUMN birthdate bigint;
ALTER TABLE users ADD COLUMN profession varchar(100);
ALTER TABLE users ADD COLUMN education varchar(100);
ALTER TABLE users ADD COLUMN careerLevel varchar(100);


-- Alter queries for questionWiseRecord
ALTER TABLE questionWiseRecord ADD COLUMN userAnswers varchar(50);
ALTER TABLE questionWiseRecord DROP COLUMN userAnswerOption;

--Insert scripts for Users
INSERT INTO users (fname, lname,login,password,role) VALUES ('Admin', 'Admin','admin', 'admin','admin');

--Insert scripts for jobcode
INSERT INTO jobcode (positionname, type, description) VALUES ('QA Engineer', 'predefined', 'Software QA (quality assurance) engineers are individuals who monitor every phase of the software development process so as to ensure design quality, making sure that the software adheres to the standards set by the development company.Sometimes software quality assurance engineers are confused with software testers, which is a mistake. Software testers test parts of the software at different stages of development, whereas a software quality assurance engineer oversees the entire development process, which includes software testing, from start to finish. The monetary success of the software product is largely due in part to the quality of the product as well as the product’s ability to hit the market on time. Both are the responsibility of the software quality assurance engineer.');

INSERT INTO category (title, type) VALUES ('Black-Box Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('White-Box Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Test Automation', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Regression Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Integration Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')));


INSERT INTO category (title, type) VALUES ('Component Testability', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')));



INSERT INTO category (title, type) VALUES ('Graph-Based Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Black-Box Testing'));

INSERT INTO category (title, type) VALUES ('Error Guessing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Black-Box Testing'));

INSERT INTO category (title, type) VALUES ('Boundary Value Analysis', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')), (select id from category where title = 'Black-Box Testing'));


INSERT INTO category (title, type) VALUES ('Basis-Path Program Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'White-Box Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Branch-Based Program Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'White-Box Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Data Flow Program Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'White-Box Testing' and type='predefined'));



INSERT INTO category (title, type) VALUES ('Syntax-Based Program Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'White-Box Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('State-Based Program Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'White-Box Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Object-Oriented Strategy', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Regression Testing' and type='predefined'));



INSERT INTO category (title, type) VALUES ('Class Firewall Concept', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Regression Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Non-Incremental Software Integration', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Integration Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Incremental Software Integration', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Integration Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Incremental Software Integration', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Integration Testing' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Component Understandability', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Component Testability' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Component Traceability', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Component Testability' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Component Controllability', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Component Testability' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Code-driven Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Test Automation' and type='predefined'));


INSERT INTO category (title, type) VALUES ('Graphical User Interface Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Test Automation' and type='predefined'));


INSERT INTO category (title, type) VALUES ('API Driven Testing', 'predefined');

INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname = 'QA Engineer' and type='predefined'), (select currval('category_id_seq')), (select id from category where title = 'Test Automation' and type='predefined'));



--Insert queries for Questions
-- White-Box Testing Questions
-- Question 1
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is not a type of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Branch Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Loop Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'State-Based Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Graph-Based Testing', true);


-- Question 2
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Finding compilation errors is one of the goals of white-box testing?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 3
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is the goal of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Guarantee that all independent paths have been exercised at least once', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Exercise all logical decisions on their true and false sides', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Exercise all loops at their boundaries and within their operational', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Exercise internal data structures to assure their validity', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);



-- Question 4
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is not the focus of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Specification-based Function Errors', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Program Structure', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Program internal logic & data structure', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Program internal behavior & states', false);



-- Question 5
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Exercising predicate nodes of a program flow graph to make sure that each predicate node has been exercised at least once defines which method of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Loop Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'State-based Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Branch Testing', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);



-- Question 6
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Exercising loops of a program to make sure that the inside and outside of loop body are executed defines which method of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Loop Testing', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'State-based Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Branch Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 7
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Using a finite state machine as a test model to check the state behaviors of a program process defines which method of white-box testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Loop Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'State-based Testing', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Branch Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);



-- Question 8
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following provides a quantitative measure of the global complexity of a program', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Cyclic Complexity', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Cyclomatic Complexity', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Basis Path Complexity', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);

-- Question 9
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is not a formula for cyclomatic complexity?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'V(G) = E - N + 2, where E is the number of flow graph edges and N is the number of flow graph nodes', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'V(G) = P + 1 where P is the number of predicate nodes contained in the flow graph G', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'V(G) = E - P + 2 where E is the number of flow graph edges and P is the number of predicate nodes contained in the flow graph G', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);



-- Question 9
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Select true or false. The basis set in a flow graph should contain minimum number of linearly independent paths.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'true', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'false', false);



-- Question 10
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is the main purpose of data flow software testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Testing data flow', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Testing correctness of data', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Finding breaks in data flow', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Improving the data test coverage of a program', true);



-- Question 11
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Select true or false. The data flow test criteria are stronger than branch testing but weaker than path testing.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'true', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'false', false);


-- Question 12
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is also known as decision testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Branch Testing', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Data Flow Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Loop Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'State-based Testing', false);



-- Question 13
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is not a limitation of branch testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Treats a compound conditional as a single statement', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'For each decision in a program structure, each branch much be exercised at least once', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Ignores implicit paths that result from compound conditionals', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);



-- Question 14
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'In condition testing, the focus is not on:', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Boolean expressions of branch nodes', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Relational operator errors', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Arithmetic expression errors', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Each branch in a program structure', true);


-- Question 15
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'A program contains a conditio: (i < value) AND (result <= maxint). How many test cases should theoretically be designed to preserve decision coverage?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '4', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '3', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '2', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '1', false);


-- Question 16
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Which of the following is not a type of loop testing?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Simple Loops', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Complex loops', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Nested Loops', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Concatenated Loops', false);


-- Question 17
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'White-Box Testing'), 'Syntax testing is a powerful technique for testing ', 'user-defined', false, true, 'Concept', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Command-driven Software', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Rule-driven Software', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Policy-driven Software', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Concatenated Condition-basedLoops', false);

-- Integration Testing Questions
-- Question 1
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Testing activities that integrate software components together to form a complete system are together called Integration Testing', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 2
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is the focus of integration Testing', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Interfaces between modules (or components)', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Integrated functional features', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Interfacing protocols and messages', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'System architecture', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);


-- Question 3
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Who performs integration Testing?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Developers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Test Engineers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Build & Release Team', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Developers & Test Engineers', true);


-- Question 4
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'What do you need to perform integration testing?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Integration Strategy', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Integration Test Environment & Suite', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Build Module (or component) Specifications', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Interface & Design Documents', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);


-- Question 5
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Test model is not needed to support the definition of software integration test strategies.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', true);


-- Question 6
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is not an example of test models?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Control Flow Graph', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Object-oriented Class Diagram', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Scenario-based Model', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Data Flow Model', true);


-- Question 7
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Sandwich integration is a non-incremental approach of integration testing.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', true);


-- Question 8
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is not an incremental software integration strategy?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Top-down Software Integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Bottom-up Software Integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Big Bang Integration Approach', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Sandwich Integration', false);


-- Question 9
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Software test stubs are programs which simulate the behaviors of software components (or modules) that are the dependent modules of a under-test modules.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 10
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Software test drivers are programs which simulate the behaviors of software components (or modules) that are the control modules of an under-test module.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 11
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is not a disadvantage of Big Band Integration Strategy?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Hard for debugging & for isolating errors', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Not easy to validate test results', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Complex', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Impossible to form an integrated system', false);


-- Question 11
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'In top-down integration approach, modules are incorporated into the system  in:', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Depth-first manner only', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Breadth-first manner only', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Either depth-first or breadth-first manner', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 12
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is a con for top-down integration approach?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Stub construction cost', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Difficult to validate test results', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Need test drivers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No controllable system until the last step', false);


-- Question 13
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is a benefit of top-down integration approach?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No stubs cost', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Major control functions can be tested early', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No stub construction cost', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Simple', false);


-- Question 14
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is a disadvantage of bottom-up integration approach?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Stub construction cost', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Difficult to validate test results', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No controllable system until the last step', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Complex', false);


-- Question 15
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Which of the following is a benefit of bottom-up integration approach?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No stubs cost', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Major control functions can be tested early', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'No stub construction cost', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Simple', false);


-- Question 16
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Class test order is an integration strategy for:', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Top-down integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Bottom-up integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Big band integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Object-oriented software', true);


-- Question 17
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Class test order is a class test sequence order for a class library or an OO program.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 18
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Class test order does not provide a unit test sequence for classes in a class library.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', true);


-- Question 20
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Class relation diagram is used as the integration test model in class test order strategy.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);



-- Regression Testing
-- Question 1
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Software regression testing activities occur...', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Before software changes', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'During software changes', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'After software changes', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 2
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Regression testing usually refers to testing activities during...', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Software Design Phase', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Software Development Phase', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Software Testing Phase', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Software Maintenance Phase', true);


-- Question 3
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. The major testing objectives of regression testing are to retest the changed components and to check the affected areas.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);



-- Question 4
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'The level at which regression testing is performed is...', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Unit Level', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Function Level', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'System Level', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Re-integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);



-- Question 5
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Who performs software regression at the unit level or integration?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Developers', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Test Engineers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'QA & Test Engineers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);



-- Question 6
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Who performs software regression at the functional level?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Developers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Test Engineers', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'QA & Test Engineers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 7
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Who performs software regression at the system level?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Developers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Test Engineers', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'QA & Test Engineers', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 8
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Major problem in software regression testing is...', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'A. How to identify software changes in a systematic way?', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'B. How to identify software change impacts in a systematic way?', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Both A & B', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'None of the above', false);


-- Question 9
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. The major challenge in software regression testing is how to minimize re-testing efforts and achieve adequate testing coverage.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);

-- Question 10
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. Reporting and analyzing regression test results is one of the steps in software regression process.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 11
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Which of the following is not a requirement change?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Adding new functional features', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change current function features', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Delete existing function features', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change component interaction', true);


-- Question 11
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Which of the following is not a system design change?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Call signatures', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change State-based behavior', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change component interaction', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change GUI design', false);



-- Question 12
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Which of the following is not a system implementation change?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Internal data types and names', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Message interaction', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Change in algorithmic logic', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Protocol messages and formats', false);


-- Question 13
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Which of the following is a typical regression test model?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Control flow graph', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Object-oriented class diagram', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Scenario-based model', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);


-- Question 14
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. With firewall concept, we can reduce the software regression testing to a smaller scope.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 15
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'Which of the following is not a firewall-based test model?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Data firewall', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Function firewall', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Class firewall', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Logical firewall', true);


-- Question 16
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Integration Testing'), 'Select true or false. The class firewall does not provide safe scope of regression testing.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', true);



-- Question 17
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Regression Testing'), 'With class firewall concept we can narrow down the scope of ...', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Regression Testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Unit Re-testing', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Re-integration', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'All of the above', true);


-- Start Blackbox Testing
--insert into category(title, type) values('BlackBox Testing', 'predefined');

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Black box testing is', 'user-defined', false, true, 'Skills', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'is testing based on knowledge of the internal logic (algorithms) of an application’s code', false),
  ((select currval('question_id_seq')),'the testing of requirements and functionality without knowledge in internal content', true),
  ((select currval('question_id_seq')), 'performed after changes have been made to a preciously released version of the software', false),
  ((select currval('question_id_seq')), ' testing is typically by the customer performed usually on site on their designated test system',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Black box testing is also called Opaque testing', 'user-defined', true, false, 'Depth', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')), 'False',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'What testing types fall under the Black Box Testing strategy?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'functional testing, stress testing', false),
  ((select currval('question_id_seq')),'recovery testing, volume testing, sanity testing', false),
  ((select currval('question_id_seq')), 'A&B', true),
  ((select currval('question_id_seq')), 'None of the above',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Smoke testing is also called', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Sanity testing', true),
  ((select currval('question_id_seq')),'Beta testing', false),
  ((select currval('question_id_seq')), 'Regression testing', false),
  ((select currval('question_id_seq')), 'Alpha testing',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'The software tested for the functional requirements is called Functional Testing', 'user-defined', true, false, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')), 'False',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In alpha testing', 'user-defined', false, true, 'Skills', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'the software is handed over to the user in order to find out if the software meets the user expectations and works as it is expected to.', true),
  ((select currval('question_id_seq')),'the users are invited at the development center where they use the application and the developers note every particular input or action carried out by the user.', false),
  ((select currval('question_id_seq')), 'A&B', false),
  ((select currval('question_id_seq')), 'None of the above',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In beta testing, the software is distributed as a beta version to the users and users test the application at their sites', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')), 'False',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In alpha testing, the software is distributed as a beta version to the users and users test the application at their sites', 'user-defined', true, false, 'Depth', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', false),
  ((select currval('question_id_seq')), 'False',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In Stress testing, the application is tested against heavy load such as', 'user-defined', false, true, 'Depth', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Complex numerical values', false),
  ((select currval('question_id_seq')),'Large number of inputs', false),
  ((select currval('question_id_seq')), 'Large number of queries', false),
  ((select currval('question_id_seq')), 'All of the above',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In black box testing, tester and programmer are independent of each other', 'user-defined', true, false, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')), 'False',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Advantages of black box testing are', 'user-defined', false, true, 'Skills', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'tests are done from a user point of view', false),
  ((select currval('question_id_seq')),'tester needs no knowledge of implementation', false),
  ((select currval('question_id_seq')), 'tester and programmer are independent of each other', false),
  ((select currval('question_id_seq')), 'All of the above',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Black-box testing methods for components inlude', 'user-defined', false, true, 'Skills', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Random testing, Decision table-based testing', false),
  ((select currval('question_id_seq')),'Partition testing, Mutation testing', false),
  ((select currval('question_id_seq')), 'Boundary value testing', false),
  ((select currval('question_id_seq')), 'All of the above',true);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Software validation methods for components can be classified into two classes', 'user-defined', false, true, 'Concept', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'White-box validation methods', false),
  ((select currval('question_id_seq')),'Black-box validation methods ', false),
  ((select currval('question_id_seq')), 'A&B', true),
  ((select currval('question_id_seq')), 'None of the above',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Fault-based black-box testing techniques(focusing on targeted fault points) include', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'User-operation scenario testing', false),
  ((select currval('question_id_seq')),'Random testing', false),
  ((select currval('question_id_seq')), 'Statistical testing', false),
  ((select currval('question_id_seq')), 'None of the above',true);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Which on of these is Usage-based black-box testing techniques?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Mutation testing', false),
  ((select currval('question_id_seq')),'User-operation scenario testing', true),
  ((select currval('question_id_seq')), 'Fault injection method', false),
  ((select currval('question_id_seq')), 'None of the above',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'Error-based black-box testing techniques(focusing on error-prone points) are', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Equivalence partitioning testing, Category-partition testing', false),
  ((select currval('question_id_seq')),'Boundary-value analysis, Decision table-based testing', false),
  ((select currval('question_id_seq')), 'All of the above', true),
  ((select currval('question_id_seq')), 'None of the above',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Black-Box Testing'),'In random testing, can we identify all input domains of a component?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')), 'False',false);


-- End Blackbox Testing



-- Start Software Testing Fundamentals

insert into category(title, type) values('Software Testing Fundamentals', 'predefined');


INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobCode where positionname='QA Engineer'), (select id from category where title='Software Testing Fundamentals'));



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), '', false),
  ((select currval('question_id_seq')),'', false),
  ((select currval('question_id_seq')), '', false),
  ((select currval('question_id_seq')), '',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'What is software error?', 'user-defined', false, true, 'Skills', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Mismatch between program and its specification', true),
  ((select currval('question_id_seq')),'Mismatch between program and requirements', false),
  ((select currval('question_id_seq')), 'A & B', false),
  ((select currval('question_id_seq')), 'None of the above',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'Test execution can be performed using manual, systematic and semi-automatic approach?', 'user-defined', true, false, 'Depth', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
  ((select currval('question_id_seq')),'False', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'Which one of the following describes the major benefit of verification early in the life cycle?', 'user-defined', false, true, 'Concept', 'Intermediate', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'It allows the identification of changes in user requirmeents', true),
  ((select currval('question_id_seq')),'It facilitates imely setup of the test environment', false),
  ((select currval('question_id_seq')), 'It reduces defect manipulation', false),
  ((select currval('question_id_seq')), 'It allows testers to become involved early in the project',false);




insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'Major objective of sotware testing plan is', 'user-defined', false, true, 'Skills', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Generate a well defined software plan', true),
  ((select currval('question_id_seq')),'Generate well defined strategy', false),
  ((select currval('question_id_seq')), 'Generate a well defined rules', false),
  ((select currval('question_id_seq')), 'Generate a well defines features',false);




insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'Which of these are software error categories?', 'user-defined', false, true, 'Concept', 'Expert', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Function errors, error handling, documentation errors, requirements error', true),
  ((select currval('question_id_seq')),'software error', false),
  ((select currval('question_id_seq')), 'code error', false),
  ((select currval('question_id_seq')), 'test error',false);




insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid)
values((select id from category where title = 'Software Testing Fundamentals'),'For performance testing,', 'user-defined', false, true, 'Depth', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'We need clear requirements on system performance', true),
  ((select currval('question_id_seq')),'We need clear requirements on system features', false),
  ((select currval('question_id_seq')), 'We need clear requirements on system functional requirements', false),
  ((select currval('question_id_seq')), 'None of the above',false);
-- End Software Testing Fundamentals

