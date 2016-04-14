# CareerPath
Repo for Master's Project




1. Created QuestionnaireController & added logic for creating questionnaire.
2. Created a place holder QuestionnaireDAO interface & QuestionnaireDAOImpl. All questionnaire related operations should go here.

//To export the database into pgsql
pg_dump -U postgres careerpath > careerpath.pgsql

//To import the exported sql script and create database freshly
psql -U username -d careerpath -a -f careerpath.pgsql