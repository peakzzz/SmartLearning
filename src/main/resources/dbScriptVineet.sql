--Insert scripts for jobcode (Data Scientist)
INSERT INTO jobcode (positionname, type, description) VALUES (‘Data Scientist’, 'predefined', 'Candidate should have a demonstrated foundation typically in statistics, modeling, operations research, computer science and applications, and math. What sets the data scientist apart is proven business acumen, coupled with the ability to communicate findings to both business and IT leaders in a way that can influence how an organization approaches a business challenge. The data scientist will extract, transform, and combine all incoming data with the goal of discovering a previously hidden insight, which in turn can provide a competitive advantage or address a pressing business problem. A data scientist does not simply collect and report on data, but also builds statistical models, determines what it means, then recommends ways to apply the data. Data scientists are inquisitive: exploring, asking questions, doing "what if" analysis, questioning existing assumptions and processes. Armed with data, modeling expertise, and analytical results, a top-tier data scientist will then communicate informed conclusions and recommendations across an organization's leadership structure');

INSERT INTO category (title, type) VALUES ('Data Pattern Recognition', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Data Extraction Algorithm', 'predefined');

INSERT into jobcategories (jobid, categoryid) VALUES ((select currval('jobcode_id_seq')), (select currval('category_id_seq')));


---Question for Data Pattern Recognition


-- Question 1
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'What are different types of methods for finding pattern in data?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Clustering', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Analyzing data', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Comparison', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Classification', true);

-- Question 2
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Multiple string matching is a method for finding pattern in data?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 3
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Object recognition in computer vision is a application for finding the patern in data.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 3
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'What are extration features of texture classiication', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), ' edge detection with a Laplacian-of-Gaussian or difference-of-Gaussian filter', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'spatial filters', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Gabor transform', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'pseudo-Wigner distribution ', true);



-- Question 4
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Symbol recgnition is a type of clasification', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);



-- Question 5
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Tools like Apache Mahout and Weka can be used to classify and find patterns in data?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);

-----------Question for Data Extraction Algorithm


--Insert queries for Questions
-- White-Box Testing Questions
-- Question 1
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'How many data integration vendors were featured in Gartners 2007 Magic Quadrant?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '4', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '10', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '16', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), '25', false);

-- Question 2
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'The difference between EDR (enterprise data replication) tools and ETL (extract, transform and load) tools is that EDR tools are used to retrieve data from disparate sources for loading into a single, target database, whereas ETL tools are database centric and are often used to copy or migrate database objects from system A to system B, according to Jill Dyche?', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);

-- Question 3 
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Which of the following best characterizes the process of continuous integration (CI)?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Integrating data or functions from one application with data of functions from another application', false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Using query federation to allow information gathered from various sources to be accessible on demand and in real-time, false);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), ' The practice of integrating daily, or several times a day, in order to reduce risks, data errors and repetitive manual processes', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'The practice of integrating all enterprise applications each time a new application or system is introduced or added to a network', false);


-- Question 4
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Data Mining refers to extracting knowledge from larger amount of data.', 'user-defined', true, false, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'True', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'False', false);


-- Question 5
insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Data Pattern Recognition'), 'Which of the following of Grid based clustering method exploratesstatistical information?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Sting', true);

insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Clique', false);
insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Array', false);
insert into option(questionid, text, iscorrectoption) values((select currval('question_id_seq')), 'Itemized Collection', false);




-----------Training Module

--Data Scientist

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Data Pattern Recognition',(select id from jobcode where positionname = 'Data Scientist'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Data Pattern Recognition'),'http://www.cse.buffalo.edu/research/areas/pattern.php');

-------
insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Data Extraction Algorithm',(select id from jobcode where positionname = 'Data Scientist'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Data Extraction Algorithm'),'https://www.coursera.org/course/mmds');

---QA Engineer

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Software Testing Fundamentals',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Software Testing Fundamentals'),'http://softwaretestingfundamentals.com');

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Black-Box Testing ',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Black-Box Testing'),'http://softwaretestingfundamentals.com/black-box-testing/');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for White-Box Testing',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='White-Box Testing'),'http://softwaretestingfundamentals.com/white-box-testing/');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Regression Testing',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Regression Testing'),'http://softwaretestingfundamentals.com/regression-testing/');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Integration Testing',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Integration Testing'),'http://softwaretestingfundamentals.com/integration-testing/');


insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Component Testing',(select id from jobcode where positionname = 'QA Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Component Testing'),'http://people.cs.clemson.edu/~johnmc/joop/col3/column3.html');

-------Software Developement Engineer

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Data Structures',(select id from jobcode where positionname = 'Software Development Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Data Structures'),'https://class.coursera.org/algo-004/lecture');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Programming Language',(select id from jobcode where positionname = 'Software Development Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Programming Language'),'https://www.coursera.org/course/proglang');


insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for OOP',(select id from jobcode where positionname = 'Software Development Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='OOP'),'http://ocw.mit.edu/courses/electrical-engineering-and-computer-science/6-01sc-introduction-to-electrical-engineering-and-computer-science-i-spring-2011/unit-1-software-engineering/object-oriented-programming/');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Database Concepts',(select id from jobcode where positionname = 'Software Development Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Database Concepts'),'https://www.mooc-list.com/course/introduction-databases-coursera');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Operating System',(select id from jobcode where positionname = 'Software Development Engineer'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Operating System'),'http://www.scs.stanford.edu/15wi-cs140/');




------ Project management


insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Project Management	',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Project Management'),'https://www.coursera.org/learn/project-management-basics/outline');



insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Project Scope Management',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Project Scope Management'),'https://www.coursera.org/learn/project-management-basics/outline');


insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Project Communication Management',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Project Communication Management'),'http://www.tutorialspoint.com/management_concepts/communications_management.htm');

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Project Cost Management',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Project Communication Management'),'https://www.coursera.org/course/budgetingprojects');

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Portfolio Management',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Portfolio Management'),'http://apm.stanford.edu');

insert into trainingmodule(id,title,jobcodeid,userid,focus,level,content) values ((select currval('trainingModule_id_seq')),'Module for Project Risk Management',(select id from jobcode where positionname = 'Project Management'), (select id from users where login = 'admin'),'Concepts', 'Intermediate', '');
insert into trainingmodulecategories(id,trainingmoduleid,categoryid,content) values ((select currval('trainingModulecategories_id_seq')), (select currval('trainingModule_id_seq')),(select id from category where title ='Project Risk Management'),'http://apm.stanford.edu');

