insert into category(title, type) values('Testing Basics', 'predefined'); 

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'),'Where does software testing come in the production line of software development phases?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Starting phase', false),
((select currval('question_id_seq')),'Middle phase', false),
((select currval('question_id_seq')), 'Last phase', true),
((select currval('question_id_seq')), 'All phases',false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following are the objectives of testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Uncover errors in a given timeline', true),
((select currval('question_id_seq')),' Demonstrate product matches the requirements', true),
((select currval('question_id_seq')), 'Validate the quality of a software testing', true),
((select currval('question_id_seq')), 'Develop the software product', false);
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), ' Scrum Master', false),
((select currval('question_id_seq')), 'Software Test Engineers', true),
((select currval('question_id_seq')), 'Development Engineers', true),
((select currval('question_id_seq')), 'Product managers', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Configuration management is in the scope of software testing?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), ' True', true),
((select currval('question_id_seq')), 'False', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Software testing is an element of which of the following topic?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), ' Validation', true),
((select currval('question_id_seq')), 'Management', false),
((select currval('question_id_seq')), 'Verification', true),
((select currval('question_id_seq')), 'Development', false);
 
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following define Software Quality factors?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Functionality', true),
((select currval('question_id_seq')), 'Maintainability', false),
((select currval('question_id_seq')), 'Engineering', true),
((select currval('question_id_seq')), 'Adaptability', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'How many basic software testing principles are usually followed in industry?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), '7', false),
((select currval('question_id_seq')), '5', false),
((select currval('question_id_seq')), '4', false),
((select currval('question_id_seq')), '6', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'We can test a program by trying all possible inputs and states of a program', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'true', false),
((select currval('question_id_seq')), 'false', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Software test automation cannot replace test engineers to perform good software testing ', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'true', true),
((select currval('question_id_seq')), 'false', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'If 100% test coverage is achieved, we can assure the test engineer completely understand the software product', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'true', false),
((select currval('question_id_seq')), 'false', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following is the correct order of the dependency of software testing process?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Unit, System, Integration, Validation', false),
((select currval('question_id_seq')), 'Unit, Integration, Validation, System', true),
((select currval('question_id_seq')), 'Unit, System, Integration, Validation', false),
((select currval('question_id_seq')), 'Unit, System, Integration, Validation', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following is not a V & V target?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Software Design', false),
((select currval('question_id_seq')), 'System Engineering', false),
((select currval('question_id_seq')), 'Requirement analysis, code & implementation', false),
((select currval('question_id_seq')), 'Development & planning', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following are the goals of  Unit test to uncover errors?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Data structure in a component', true),
((select currval('question_id_seq')), 'Component Interface', true),
((select currval('question_id_seq')), 'Design & construction of software architecture', false),
((select currval('question_id_seq')), 'Functions & operations of a component', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Integration testing is the testing of a group of dependent of components tested together to ensure the quality of their integration unit', 'user-defined',  true,false,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following are the focus of function validation testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'System input/output', true),
((select currval('question_id_seq')), 'User Interfaces', true),
((select currval('question_id_seq')), 'Program Logic', false),
((select currval('question_id_seq')), 'System behavior & performance', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'What are the issues of testing?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Stopping point of testing', true),
((select currval('question_id_seq')), 'Test criteria, test coverage', true),
((select currval('question_id_seq')), 'Software testing industry needs more talent', false),
((select currval('question_id_seq')), 'Industry needs more recognition', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following is not a major task of test analysis & design?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'To identify test conditions', true),
((select currval('question_id_seq')), 'To design test environment set-up & identify required infrastructure & tools', true),
((select currval('question_id_seq')), 'To define scope of the tests', false),
((select currval('question_id_seq')), 'To decide stopping condition', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'During test implementation and execution, we take the test conditions into test cases and procedures and other test-ware such as scripts for automation, the test environment and any other test infrastructure.', 'user-defined', true, false,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False', false);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Which of the following is not a task of evaluating exit criteria?', 'user-defined', false, true,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'To check the test logs against the exit criteria specified in test planning.', true),
((select currval('question_id_seq')), 'To assess if more test are needed or if the exit criteria specified should be changed.', true),
((select currval('question_id_seq')), 'To keep the scrum team updated about the scope of the product & testing', false),
((select currval('question_id_seq')), 'To write a test summary report for stakeholders', true);

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Testing Basics'), 'Software closure cannot occur before the software is delivered.', 'user-defined', true, false,'Concept', 'Beginner', (select id from users where login = 'admin'));
 
insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'True', false),
((select currval('question_id_seq')), 'False', true);
