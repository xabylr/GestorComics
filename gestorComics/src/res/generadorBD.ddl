CREATE TABLE ANOTACION
  (
    ID        NUMBER NOT NULL PRIMARY KEY ,
    TEXTO     VARCHAR2 (500) ,
    PRIVADA   BIT ,
    COMIC_ID  NUMBER ,
    BOCETO_ID NUMBER ,
    VINETA_ID NUMBER ,
    FOREIGN KEY ( COMIC_ID ) REFERENCES COMIC ( ID ) ,
    FOREIGN KEY ( BOCETO_ID ) REFERENCES BOCETO ( ID ),
    FOREIGN KEY ( VINETA_ID ) REFERENCES VINETA ( ID )
  ) ;

CREATE TABLE BOCETO
  (
    ID NUMBER NOT NULL PRIMARY KEY,
    IMAGEN BLOB NOT NULL ,
    COMIC_ID NUMBER NOT NULL ,
    FOREIGN KEY ( COMIC_ID ) REFERENCES COMIC ( ID )
  ) ;
  
CREATE TABLE COMIC
  (
    ID        NUMBER NOT NULL PRIMARY KEY ,
    NOMBRE    VARCHAR2 (50) NOT NULL ,
    PORTADA   NUMBER ,
    ID_MEDIO  NUMBER ,
    FOREIGN KEY ( PORTADA ) REFERENCES VINETA ( ID ) ,
    FOREIGN KEY ( ID_MEDIO ) REFERENCES MEDIO_DIFUSION ( ID ),
  ) ;

CREATE TABLE COMIC_VINETA
  (
    COMIC_ID  NUMBER NOT NULL ,
    VINETA_ID NUMBER NOT NULL ,
    PRIMARY KEY (COMIC_ID,VINETA_ID),
    FOREIGN KEY ( COMIC_ID ) REFERENCES COMIC ( ID ),
    FOREIGN KEY ( VINETA_ID ) REFERENCES VINETA ( ID )
  ) ;
  
 CREATE TABLE VINETA
  (
    ID     NUMBER NOT NULL PRIMARY KEY ,
    NOMBRE VARCHAR2 (50) NOT NULL ,
    IMAGEN BLOB NOT NULL
  ) ;

 CREATE TABLE MEDIO_DIFUSION
 (
   ID	   NUMBER NOT NULL PRIMARY KEY ,
   NOMBRE  VARCHAR2 (50) NOT NULL , 
 ) ;