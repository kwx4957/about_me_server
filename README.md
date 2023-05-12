# about_me_server [![Build Status](https://travis-ci.org/ABOUT-ME-APP/about_me_server.svg?branch=dev_1)](https://travis-ci.org/ABOUT-ME-APP/about_me_server)
앱구동을 위한 서버환경 설정 , api 서버 구축 

### * 환경설정
> 1. 개발언어 -> spring > mysql
> 2. 클라우드 환경  -> 파이어베이스(푸시기능 매우 필요) 또는 AWS
> 3. IDE -> 인텔리J 또는 Visula Studio Code 

### * 코딩규칙
> 1. 기간동안 1브랜치 1커밋 필수 -> 벌칙?
> 2. 이름 붙이기
> > + 변수 aaBbbCcc : 카멜형식
> > + 함수 aa_bb_cc : 팟홀형식
> > + 클래스 AaBbCcc : 파스칼형식
> 3. 변수선언 예) 등호위치 동일
> > >``` 
> > > int a                       = b;
> > > string str                  = '알파벳';
> > > HashMap<String, Object> map = new HashMap<>();
> > > ```
 > 4. 날쿼리 보단 모델클래스 구성후 참조하기

### * 진행순서 (예상) 
> > 2~4는 한번에 제대로 구현했으면 함
> > 3월 3째주부터 시작을 가정하에. _16주_ 커리
> > 1. erd 구성 - 서비스 기능 탭 정해지면 _1주_ 간 진행 
> > 2. db 설계 및 저장 - 설계 _1주_ 및 데이터 처리 _1주_  
> > 3. 환경설정 -> 오프라인 모임 했으면 함 : 설정할때 한번에 같이 해버리는것이 좋을듯하여 _1주_
> > 4. 모델 구현 - +spring 코드 공부 - _4주_
> > 5. 쿼리 구현 및 api 코딩 - _4주_
> > 7. 앱 연동 확인 및 코드 수정 - _4주_
 

 

