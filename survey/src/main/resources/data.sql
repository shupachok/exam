insert into survey (id,description,title) values (1000,'test','test Survey');

insert into question (ID  ,DESCRIPTION ,SURVEY_ID,CORRECT_ANSWER )  values (1100,'question AAA',1000,'AAA');

insert into question (ID  ,DESCRIPTION ,SURVEY_ID,CORRECT_ANSWER )  values (1101,'question BBB',1000,'BBB');

insert into question (ID  ,DESCRIPTION ,SURVEY_ID,CORRECT_ANSWER )  values (1102,'question CCC',1000,'CCC');

insert into option (id,answer,QUESTION_ID ) values (1000,'Dog',1100);