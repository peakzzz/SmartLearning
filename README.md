# CareerPath
Repo for Master's Project

Educational platform for collaborative and quality learning. 
Includes job portal integration, learning materials, assessments such as quizzes and assignments, auto correction tool and project collaboration. 
Data pattern discovery and big data analytics for assessing students learning, improved course suggestion and performance.

//To export the database into pgsql
pg_dump -U postgres careerpath > careerpath.pgsql

//To import the exported sql script and create database freshly
psql -U username -d careerpath -a -f careerpath.pgsql
