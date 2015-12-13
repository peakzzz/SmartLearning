INSERT INTO jobcode (positionname, type, description, userid) VALUES ('Project Manager', 'predefined', 'The Software Project Manager will play a key role in driving multiple technical projects to successful completion.  We are seeking a well-rounded Software Project Manager with experience managing multiple technical teams through sprint cycles, including creating project and contingency plans, defining cycle content, managing cross team dependencies, scheduling resources and mitigating road blocks.  The project manager should be comfortable working in an ambiguous as well as in a structured environment while communicating with internal and external stakeholders, and technical resources.', 1);

INSERT INTO category (title, type) VALUES ('Project Management', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')));

INSERT INTO category (title, type) VALUES ('Project Scope Management', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')),(select id from category where title='Project Management'));

INSERT INTO category (title, type) VALUES ('Project Risk Management', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')),(select id from category where title='Project Management'));

INSERT INTO category (title, type) VALUES ('Project Communication Management', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')),(select id from category where title='Project Management'));

INSERT INTO category (title, type) VALUES ('Project Cost Management', 'predefined');
INSERT into jobcategories (jobid, categoryid, parentcategoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')),(select id from category where title='Project Management'));


INSERT INTO category (title, type) VALUES ('Portfolio Management', 'predefined');
INSERT into jobcategories (jobid, categoryid) VALUES ((select id from jobcode where positionname='Project Manager' and type='predefined'), (select currval('category_id_seq')));


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'Which of the following can cause a conflict?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Insufficient action on the part of the project manager', false),
((select currval('question_id_seq')),'Competition for facilities, equipment, material, manpower and other resources', false),
((select currval('question_id_seq')), 'Personality conflicts between managers and/ or other personnel', false),
((select currval('question_id_seq')), 'All of the above',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'Most of the project managers external communication comprises:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Written documents', true),
((select currval('question_id_seq')),'Oral communication', false),
((select currval('question_id_seq')), 'Tactile contact', false),
((select currval('question_id_seq')), 'Informal contact',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'The critical element in a projects communication system is the:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Progress report',false),
((select currval('question_id_seq')),'Project directive', false),
((select currval('question_id_seq')), 'Project manager', true),
((select currval('question_id_seq')), 'Customer',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'Truly effective communication:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Depends on using multiple channels',false),
((select currval('question_id_seq')),'Depends on selecting the right method for each message and avoiding duplication, since everyone is overloaded with information', false),
((select currval('question_id_seq')), 'Can only be achieved by communication professional', false),
((select currval('question_id_seq')), 'a and b',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'The process of communication contains four major parts. Which part is the vehicle or method used to convey the message?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Communicator',false),
((select currval('question_id_seq')),'Message', false),
((select currval('question_id_seq')), 'Medium', true),
((select currval('question_id_seq')), 'Recipient',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'The tools and techniques used in the process of Manage Communications excludes', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Information Management System',false),
((select currval('question_id_seq')),'Communication Technology', false),
((select currval('question_id_seq')), 'Communication Models', false),
((select currval('question_id_seq')), 'Communication Requirements Analysis',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Communication Management'),'An arrangement that provides a set of standard tools for the project manager to capture, store and distribute information to stakeholders about the projects costs, schedule progress, and performance is known as', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Performance Reporting',false),
((select currval('question_id_seq')),'Information Management System', false),
((select currval('question_id_seq')), 'Expert Judgment', false),
((select currval('question_id_seq')), 'None of the above',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Scope Management'),'The process of determining, documenting and managing stakeholder needs and requirements to meet project objectives is known as', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Plan Scope Management',false),
((select currval('question_id_seq')),'Collect Requirements', true),
((select currval('question_id_seq')), 'Control Scope', false),
((select currval('question_id_seq')), 'Validate Scope',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Scope Management'),'The Scope Management Plan is included in which of the following documents?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Project Management Plan',true),
((select currval('question_id_seq')),'The Work Breakdown Structure', false),
((select currval('question_id_seq')), 'The Scope Statement', false),
((select currval('question_id_seq')), 'Project Specifications',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Scope Management'),'Project Scope:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'is of concern only at the start of the project',false),
((select currval('question_id_seq')),'is mainly a problem to be handled by the change control procedures during the project execution phase.', false),
((select currval('question_id_seq')), 'Should be managed and controlled from the project concept through closing', true),
((select currval('question_id_seq')), 'is usually not a problem after the contract or other document authorizing the project has been approved.',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Scope Management'),'The Project charter is created by:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'The project manager',false),
((select currval('question_id_seq')),'The sponsor', true),
((select currval('question_id_seq')), 'The Vice President over a functional management group', false),
((select currval('question_id_seq')), 'The customer',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Scope Management'),'The Requirements that describe features, functions and characteristics of the product, service, or result that will meet the business and stakeholders requirements is known as:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Solution Requirements',false),
((select currval('question_id_seq')),'Project Requirements', true),
((select currval('question_id_seq')), 'Transition Requirements', false),
((select currval('question_id_seq')), 'Quality Requirements',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Risk Management'),'The process under Process Risk Management that prioritizes risks for further analysis or action by assessing and combining their probability of occurrence and impact is called', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Perform Qualitative Risks Analysis',true),
((select currval('question_id_seq')),'Perform Quantitative Risk Analysis', true),
((select currval('question_id_seq')), 'Plan Risk Management', false),
((select currval('question_id_seq')), 'Plan Risk Responses',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Risk Management'),'The inputs used in the process of Perform Qualitative Risk Analysis includes all except', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Scope Baseline',false),
((select currval('question_id_seq')),'Risk Register', false),
((select currval('question_id_seq')), 'Quality Management Plan', true),
((select currval('question_id_seq')), 'Risk Management Plan',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Risk Management'),'A risk is defined as ____________ event or condition that, if it occurs, has a positive or negative effect on one or more project______________.', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'indefinite, probabilities',false),
((select currval('question_id_seq')),'uncertain, objectives', true),
((select currval('question_id_seq')), 'sure, goals', false),
((select currval('question_id_seq')), 'definite, uncertainties',false);



insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Risk Management'),'The process of Control Risks does not have one of the following as an output. Which one is it?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Work Performance Information',false),
((select currval('question_id_seq')),'Change Requests', false),
((select currval('question_id_seq')), 'OPA updates', false),
((select currval('question_id_seq')), 'Risk Register',true);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Risk Management'),'The strategy used under Strategies for Positive Risks or Opportunities that is used to increase the probability and/or the positive impacts of an opportunity is called.', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Share',false),
((select currval('question_id_seq')),'Accept', false),
((select currval('question_id_seq')), 'Enhance', true),
((select currval('question_id_seq')), 'Exploit',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Cost Management'),'Which of the following type of contracts is most preferable to the contractor doing the project work?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Cost plus fixed fee',true),
((select currval('question_id_seq')),'Fixed price', false),
((select currval('question_id_seq')), 'Fixed price plus incentive free', false),
((select currval('question_id_seq')), 'B and C',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Cost Management'),'Cost Variance (CV) is which of the following equations?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'A. CV = EV - PV',false),
((select currval('question_id_seq')),'B. CV = EV - AC', true),
((select currval('question_id_seq')), 'C. CV = EV / AC', false),
((select currval('question_id_seq')), 'D. A and c',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Cost Management'),'Which of the following is a direct project cost?', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'A. Lighting and heating for the corporate office',false),
((select currval('question_id_seq')),'B. Piping for an irrigation project', true),
((select currval('question_id_seq')), 'C. Workers Compensation insurance', false),
((select currval('question_id_seq')), 'D. A and B',false);


insert into question(categoryid, questiontext, type, istrueorfalse, ismultiplechoice, focus, level, userid) values((select id from category where title = 'Project Cost Management'),'The sum of all budgets established for the work to be performed is known as:', 'user-defined', false, true, 'Concept', 'Beginner', (select id from users where login = 'admin'));

insert into option(questionid, text, iscorrectoption)
values((select currval('question_id_seq')), 'Planned Value',false),
((select currval('question_id_seq')),'Aggregated Planned Value', false),
((select currval('question_id_seq')), 'Budget at Completion', true),
((select currval('question_id_seq')), 'Estimate at Completion',false);


