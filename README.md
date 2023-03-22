# 블로그 검색 API 

## 실행
- [jar파일 다운로드](https://drive.google.com/file/d/1GynGzKgpqp_7d6ZB7vIvQGTDuvvE8UJ4/view?usp=sharing)
  ```
  java -jar blog-0.0.1-SNAPSHOT.jar
  
  h2 
  console : http://localhost:8080/h2-console 
  jdbc URL : jdbc:h2:mem:blog
  ```

## 요구 사항
- [x] 블로그 검색
   - [x] 키워드를 통해 블로그 검색
     - 검색 소스는 [카카오 API의 키워드로 블로그 검색](https://developers.kakao.com/docs/latest/ko/daum-search/dev-guide#search-blog)을 활용합니다.
   - [x] 정확도순, 최신순 정렬 기능
   - [x] 페이징 처리
   - [x] failover [네이버 블로그 검색](https://developers.naver.com/docs/serviceapi/search/blog/blog.md)
- [x] 인기 검색어 목록
  - [x] 많이 검색한 순서 top10
  - [x] 검색된 횟수 추가

## Spec
- Java 11
- SpringBoot 2.7.9
- Feign Client
  - 외부 API 통신
- Spock (test framework)
  - 테스트

## 우대사항
- [ ] 멀티모듈
- [x] 데이터가 많고 트리픽이 많은 환경 동시성 제어

### 동시성 제어 고민..
- redisson 분산락
    - redis 사용안하기로 결정
- 트래픽 많다는 가정이니, 여러대의 서버가 있다고 가정하여 synchronized 는 고려하지 않음.
- Lock 고민
    - 낙관적락 or 비관적락 중 고민
    - 낙관적락은 실패 시 처리 비용이 큼(무시? 재시도? 또실패하면?)
      - 많은 케이스로 고려 X 
    - 비관적락이 좀 더 나을것으로 보이나 트래픽과 데이터가 많음.
    - 카운트를 한개씩 늘리고 조회시점에 완벽한 횟수가 필요한것은 아님으로 update 요청으로 처리하고자함.
      - mysql update 에는 배타적잠금이 적용되어 동시성제어가 될것으로 판단됨
      - 위내용은 db 별로 다르고, h2는 아닌것으로 확인 
    - **비관적락을 사용하기로 결정**

## 필요 추가 작업사항
- [x] index 추가
- [x] 동시성 제어
- [x] 예외처리
- [ ] 다운로드 링크 제공
