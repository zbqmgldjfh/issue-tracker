# Team-26

## 👨‍👩‍👧‍👦 멤버소개

---

#### 👨‍ 호이 [@호이](https://github.com/youryu0212)
#### 👨‍ 아울 [@아울](https://github.com/bukim0329)
#### 👨‍ Shine [@zbqmgldjfh](https://github.com/zbqmgldjfh)

## 실행 영상
![이슈트래커](https://user-images.githubusercontent.com/60593969/179674790-8fb09d82-77f2-4470-af8d-65a9d2ae0c91.gif)

## BE 2.0ver TODO List
- [ ] DB Cleanup Class 구현하기, 테스트간에 격리용도
- [ ] 로그인 업데이트
  - [x] 로그인 인수 조건 도출 [wiki 링크](https://github.com/zbqmgldjfh/issue-tracker/wiki/%EC%9D%B8%EC%88%98%EC%A1%B0%EA%B1%B4-%EB%8F%84%EC%B6%9C)
  - [ ] 로그인 인수 테스트 작성
    - [ ] Form 기반 로그인 기능 구현
    - [ ] Bearer 기반 로그인 기능 구현
    - [ ] Basic 기반 로그인 기능 구현
- [ ] 인수 테스트를 수행하기 전 공통으로 필요한 상용자는 초기에 설정하기
  - [ ] DataLoader 구현하기

## BE 1.0ver 기능
기본적인 API 기능을 제공    
[API 명세서 바로가기](https://github.com/zbqmgldjfh/issue-tracker/wiki/API-%EB%AA%85%EC%84%B8)
- Issue 관련 기능
- Label 관련 기능
- MileStone 관련 기능
- Comment 관련 기능
- Image 등록 기능

## Ground Rule

- 10:00 ~ 10:30
    - 스크럼 겸 진행상황 공유
    - 필요 회의 있다면 진행하기
- 10:30 ~
    - 클래스 별 구현 & 학습 진행
    
### 폴더 구조
<img width="352" alt="스크린샷 2022-06-13 오전 11 02 16" src="https://user-images.githubusercontent.com/60593969/173266533-28f3f010-7f88-4247-8321-9ab7a1f2b3e9.png">

### 이슈 전략

```
제목: [FEAT] (FE / BE) 기능제목
---

## 💡 issue
이슈 작성

## 📝 todo
- [ ] 작업1
- [ ] 작업2
```



### 커밋 메시지 전략

```
[키워드]: 커밋 메시지 작성 [#이슈번호]

contents
- someting
- someting2

Todo
- 앞으로 할 일(필요 시 작성) 

예) [feat]: 기능 추가 [#13]
```



### 커밋 타입

| 타입     | 설명                                                         |
| -------- | ------------------------------------------------------------ |
| Feat     | 새 기능 추가                                                 |
| Fix      | 버그 수정                                                    |
| Design   | CSS 등 디자인 변경                                           |
| Style    | 코드 포맷 변경, 세미 콜론 누락, 코드 수정이 없는 경우        |
| Refactor | 리팩토링                                                     |
| Comment  | 주석 추가 및 변경                                            |
| Docs     | 문서 수정                                                    |
| Test     | 테스트 추가, 테스트 리팩토링                                 |
| Chore    | 빌드 테스트 업데이트, 패키지 매니저를 설정(프로덕션 코드 변경 X) |
| Rename   | 파일 수정하거나 옮기는 작업                                  |
| Remove   | 파일 삭제                                                    |
