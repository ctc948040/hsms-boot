--DROP TABLE IF EXISTS COM_USER;

create TABLE IF NOT EXISTS COM_USER (
  id       int unsigned primary key not null auto_increment,
  name     varchar(32)              not null,
  password varchar(32)               null,
  state    tinyint                   null,
  address  varchar(128)              null,
  email    varchar(32)               null
);

CREATE TABLE IF NOT EXISTS COM_FILE (
	FILE_ID     VARCHAR(35)  NOT NULL DEFAULT (CONCAT('FIL',HEX(UUID_TO_BIN(UUID(),1)))) COMMENT '파일아이디', -- 파일아이디
	FILE_NAME   VARCHAR(100) NOT NULL COMMENT '파일명', -- 파일명
	FILE_PATH   VARCHAR(100)   NOT NULL COMMENT '파일경로', -- 파일경로
	REG_USER_ID VARCHAR(30)    NULL     COMMENT '등록자', -- 등록자
	IN_DATE     VARCHAR(14)    NULL     COMMENT '등록일', -- 등록일
	UP_DATE     VARCHAR(14)    NULL     COMMENT '수정일' -- 수정일
)
COMMENT '공통파일';

-- 공통파일
ALTER TABLE COM_FILE
	ADD CONSTRAINT PK_COM_FILE -- 공통파일 기본키
	PRIMARY KEY (
	FILE_ID -- 파일아이디
	);
	
	
-- 카테고리
CREATE TABLE IF NOT EXISTS COM_CATEGORY (
	CTG_ID        VARCHAR(35)  NOT NULL DEFAULT (CONCAT('CTG',HEX(UUID_TO_BIN(UUID(),1)))) COMMENT '카테고리코드', -- 카테고리아이디
	CTG_NAME      VARCHAR(50)  NOT NULL COMMENT '카테고리명', -- 카테고리명
	PARENT_CTG_ID VARCHAR(35)  NULL     COMMENT '부모카테고리아이디', -- 부모카테고리아이디
	CTG_LEVEL     INT          NULL     COMMENT '카테고리레벨', -- 카테고리레벨
	CTG_SORT      INT          NULL     COMMENT '순번', -- 순번
	GRADE_CODE    VARCHAR(8)   NULL     COMMENT '학년코드(COMGRD)', -- 학년코드
	SUBJECT_CODE  VARCHAR(8)   NULL     COMMENT '과목코드(COMSBJ)', -- 과목코드
	USE_YN        VARCHAR(1) NULL     COMMENT '사용여부', -- 사용여부
	REG_USER_ID   VARCHAR(35)  NULL     COMMENT '등록자', -- 등록자
	IN_DATE       VARCHAR(14)  NULL     COMMENT '등록일', -- 등록일
	UP_DATE       VARCHAR(14)  NULL     COMMENT '수정일' -- 수정일
)
COMMENT '카테고리';

-- 카테고리
ALTER TABLE COM_CATEGORY
	ADD CONSTRAINT PK_COM_CATEGORY -- 카테고리 기본키
	PRIMARY KEY (
	CTG_ID -- 카테고리아이디
	);

-- 카테고리
ALTER TABLE COM_CATEGORY
	ADD CONSTRAINT FK_COM_CATEGORY_TO_COM_CATEGORY -- 카테고리 -> 카테고리
	FOREIGN KEY (
	PARENT_CTG_ID -- 부모카테고리아이디
	)
	REFERENCES COM_CATEGORY ( -- 카테고리
	CTG_ID -- 카테고리아이디
	);	
	
-- 공통그룹코드
CREATE TABLE  IF NOT EXISTS COM_GROUP_CODE (
	GROUP_CODE_ID   VARCHAR(6)   NOT NULL COMMENT '그룹코드', -- 그룹코드아이디
	GROUP_CODE_NAME VARCHAR(50)  NOT NULL COMMENT '그룹코드명', -- 그룹코드명
	USE_YN          VARCHAR(1) NULL     COMMENT '사용여부', -- 사용여부
	REG_USER_ID     VARCHAR(35)  NULL     COMMENT '등록자', -- 등록자
	IN_DATE         VARCHAR(14)  NULL     COMMENT '등록일', -- 등록일
	UP_DATE         VARCHAR(14)  NULL     COMMENT '수정일' -- 수정일
)
COMMENT '공통그룹코드';

-- 공통그룹코드
ALTER TABLE COM_GROUP_CODE
	ADD CONSTRAINT PK_COM_GROUP_CODE -- 공통그룹코드 기본키
	PRIMARY KEY (
	GROUP_CODE_ID -- 그룹코드아이디
	);	
	
	
-- 공통코드
CREATE TABLE IF NOT EXISTS  COM_CODE (
	GROUP_CODE_ID VARCHAR(6)   NOT NULL COMMENT '그룹코드아이디', -- 그룹코드아이디
	CODE_ID       VARCHAR(8)   NOT NULL COMMENT '코드아이디', -- 코드아이디
	CODE_NAME     VARCHAR(50)  NOT NULL COMMENT '코드명', -- 코드명
	SORT          INT          NULL     COMMENT '순번', -- 순번
	DEFAULT_YN    VARCHAR(1) NULL     COMMENT '기본여부', -- 기본여부
	USE_YN        VARCHAR(1) NULL     COMMENT '사용여부', -- 사용여부
	REG_USER_ID   VARCHAR(35)  NULL     COMMENT '등록자', -- 등록자
	IN_DATE       VARCHAR(14)  NULL     COMMENT '등록일', -- 등록일
	UP_DATE       VARCHAR(14)  NULL     COMMENT '수정일' -- 수정일
)
COMMENT '공통코드';

-- 공통코드
ALTER TABLE COM_CODE
	ADD CONSTRAINT PK_COM_CODE -- 공통코드 기본키
	PRIMARY KEY (
	GROUP_CODE_ID, -- 그룹코드아이디
	CODE_ID        -- 코드아이디
	);

-- 공통코드
ALTER TABLE COM_CODE
	ADD CONSTRAINT FK_COM_GROUP_CODE_TO_COM_CODE -- 공통그룹코드 -> 공통코드
	FOREIGN KEY (
	GROUP_CODE_ID -- 그룹코드아이디
	)
	REFERENCES COM_GROUP_CODE ( -- 공통그룹코드
	GROUP_CODE_ID -- 그룹코드아이디
	);	