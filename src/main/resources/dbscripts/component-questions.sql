--insert into category(title, type) values('Component Testability', 'predefined');

insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Testability is a very important quality indicator of software and its components since its measurement leads to the prospect of _______ and _______ a software test process.', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'facilitating', true),
((select currval('question_id_seq')),'success', false),
((select currval('question_id_seq')), 'improving', true),
((select currval('question_id_seq')), 'progress',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'The degree to which a system or component facilitate the establishment of test criteria and the performance of tests to determine which of the following?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'criteria have been met', true),
((select currval('question_id_seq')),'the degree to which a requirement is stated in terms that permit the establishment of test criteria', true),
((select currval('question_id_seq')), 'performance of tests to determine whether test criteria have been  met', true),
((select currval('question_id_seq')), 'success of the project',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Poor testability of components and programs indicate which of the following?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'the poor quality of software and components', true),
((select currval('question_id_seq')),'the ineffective test  process', true),
((select currval('question_id_seq')), 'problem with test criteria', true),
((select currval('question_id_seq')), 'inefficiency of testers',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are not the tasks and activities related to testing?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Requirement Analysis', true),
((select currval('question_id_seq')),'Project Management', false),
((select currval('question_id_seq')), 'Software Design', true),
((select currval('question_id_seq')), 'Maintenance',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are the factors of component testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Understandability', true),
((select currval('question_id_seq')),'Observerability', true),
((select currval('question_id_seq')), 'traceability', true),
((select currval('question_id_seq')), 'Measurability',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Component observability indicates how easy it is to observe a program based on which of the following factors?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'operation behaviors', true),
((select currval('question_id_seq')), 'code coverage',false),
((select currval('question_id_seq')),'input parameter values', true),
((select currval('question_id_seq')), 'actual outputs', true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Component observability indicates how easy it is to observe a program based on which of the following factors?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'operation behaviors', true),
((select currval('question_id_seq')), 'code coverage',false),
((select currval('question_id_seq')),'input parameter values', true),
((select currval('question_id_seq')), 'actual outputs', true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Component traceability depends on which of the following factors?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'event & state behavior', true),
((select currval('question_id_seq')), 'function/operation',true),
((select currval('question_id_seq')),'performance', true),
((select currval('question_id_seq')), 'error', true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Component Controllability depends on which of the following factors?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'execution & environment', true),
((select currval('question_id_seq')), 'state-based behavior',true),
((select currval('question_id_seq')),'test control through interfaces', true),
((select currval('question_id_seq')), 'performance', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following is not a challenge in studying component testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Creating component testability models', false),
((select currval('question_id_seq')), 'Finding systematic methods to create testable components',false),
((select currval('question_id_seq')),'Reaching 100% code coverage', true),
((select currval('question_id_seq')), 'Developing systematic methods to verify component testability', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Design for component testability refers to requirement gathering and software development activities to enhance component testability for software components in a component development process', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are common approaches to increase component testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Framework-based testing facility', true),
((select currval('question_id_seq')), 'Build-in tests',true),
((select currval('question_id_seq')),'Systematic component wrapping for testing', true),
((select currval('question_id_seq')), 'Complete component testing', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'According to Y. Wang et al, a built-in test component is a special type of software component in which special member functions are included as its _______ for enhancing software ________ and _________', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'source code', true),
((select currval('question_id_seq')), 'major components',false),
((select currval('question_id_seq')),'testability', true),
((select currval('question_id_seq')), 'maintainability', true);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Built-in test components are able to operate in which of the following modes?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Normal mode', true),
((select currval('question_id_seq')), 'Maintenance mode',true),
((select currval('question_id_seq')),'Development mode', false),
((select currval('question_id_seq')), 'Assessment mode', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'What are the major limits of built-in test components?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Only limited tests can be built-in tests due to component complexity', true),
((select currval('question_id_seq')), 'It is costly to change and maintain built-in tests during a component development process',true),
((select currval('question_id_seq')),'It is difficult to test any amendments to features of components', false),
((select currval('question_id_seq')), 'It is difficult to adapt to changing the test plans', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are Different Architecture Models for Testable Components?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Component Software', true),
((select currval('question_id_seq')), 'Component Framework',false),
((select currval('question_id_seq')),'Built-in Test Component', false),
((select currval('question_id_seq')), 'Testable Bean', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following is the order of maturity levels of testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Initial, Standardized, Systematic, Measurable', true),
((select currval('question_id_seq')), 'Initial, Secondary, Systematic, Measurable',false),
((select currval('question_id_seq')),'Initial, Standardized,Managed, Measurable', false),
((select currval('question_id_seq')), 'Primary, Secondary, Systematic, Measurable', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'__________ & ___________ are the two verification approaches of component testability', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Static', true),
((select currval('question_id_seq')), 'Systematic',false),
((select currval('question_id_seq')),'Statistic', true),
((select currval('question_id_seq')), 'Measurability', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following is not a phase of static approach for verification of component testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Component specification phase', false),
((select currval('question_id_seq')), 'Component design phase',false),
((select currval('question_id_seq')),'Component implementation phase', false),
((select currval('question_id_seq')), 'Component re-iteration phase', true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are the methods for measurement of software testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Program-based measurement', true),
((select currval('question_id_seq')), 'Model-based measurement',true),
((select currval('question_id_seq')),'Dependability assessment methods', true),
((select currval('question_id_seq')), 'Component-based measurement', false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Which of the following are the methods for measurement of software testability?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Program-based measurement', true),
((select currval('question_id_seq')), 'Model-based measurement',true),
((select currval('question_id_seq')),'Dependability assessment methods', true),
((select currval('question_id_seq')), 'Component-based measurement', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'__________ use the black-box approach for testability measurement', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'Program-based measurement', false),
((select currval('question_id_seq')), 'Model-based measurement',false),
((select currval('question_id_seq')),'Dependability assessment methods', true),
((select currval('question_id_seq')), 'Component-based measurement', false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Model-based measurement methods for software testability is a black-box based approach for testability measurement', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'False', true),
((select currval('question_id_seq')), 'True',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'The major objective of Jerrfrey Voas method is to predict the probability of a software failure occurring if the particular software contains a fault for a given set of test set for black-box testing.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Static verification approach suggests the testing intensity or testing difficulty in discovering a fault at a specific location.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'False', true),
((select currval('question_id_seq')), 'True',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'The major goal of introducing testable components is to find a new way to develop software components which are easily to be observed, traced, tested, deployed, and executed.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'A testable bean is a testable software component that not only deployable and executable', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'True', true),
((select currval('question_id_seq')), 'False',true);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Component Testability'),'Systematic  Component Wrapping for Testing has high programming overhead', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) 
values((select currval('question_id_seq')), 'True', false),
((select currval('question_id_seq')), 'False',true);