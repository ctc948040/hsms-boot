package com.hsms;

public class ResponseMessage {
	public static final String OK = "회원 정보 조회 성공";
	public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";
    
    public static final String READ_CATEGORY = "카테고리 정보 조회 성공";
    public static final String NOT_FOUND_CATEGORY = "카테고리를 찾을 수 없습니다.";
    
    public static final String READ_QUESTION = "학습문제 정보 조회 성공";
    public static final String NOT_FOUND_QUESTION = "학습문제를 찾을 수 없습니다.";
    
    public static final String READ_FILE = "파일 정보 조회 성공";
    public static final String NOT_FOUND_FILE = "파일을 찾을 수 없습니다.";
	public static final String NOT_FOUND_BASKET = "학습문제를 찾을 수 없습니다.";
	public static final String READ_BASKET = "학습문제 정보 조회 성공";
	public static final String INSERT_BASKET = "문제바구니에 등록 성공";
	public static final String UPDATE_BASKET = "문제바구니 업데이트 성공";
}
