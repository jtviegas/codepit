CREATE TABLE TERMINATION_LEVEL
   (	"ID" NUMBER(5,0) GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20, 
	"DESCRIPTION" VARCHAR2(60 BYTE) NOT NULL ENABLE
   ) ;