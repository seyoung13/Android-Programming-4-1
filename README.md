# Android-Programming-4-1

# 최종 발표

## 게임 컨셉

<img src="/PlanImages/pt3/gameply.png" width="300px" height="200px"></img>

타일맵 기반의 타워 디펜스


## 커밋 히스토리

<img src="/PlanImages/pt3/commit_history.png" width="300px" height="200px"></img>


## 진행률

<img src="/PlanImages/pt3/progress.png" width="300px" height="200px"></img>


## 구현 내용

<img src="/PlanImages/pt3/path_example.png" width="300px" height="200px"></img>

* JSON 파일을 로드해서 타일맵을 생성하는 기능

* 백트래킹 알고리즘을 사용해 시작지점별 경로를 생성하는 기능


<img src="/PlanImages/pt3/result_example.png" width="300px" height="200px"></img>

* 플레이어의 점수를 수집하고 출력하는 기능

* 레이어를 이용한 발사체별 충돌 및 데미지 처리


## 수업내용에서 차용한 내용

*버튼을 이용한 타워 업그레이드 및 철거 기능

* PathMeasure 를 이용한 몬스터 이동

* 숫자를 화면에 표시하는 기능


## 구현하기 어려웠던 부분

* json 파일의 웨이브 정보를 적절한 구조로 저장하기

* 저장한 웨이브 큐를 이용해 몬스터를 생성하기


## 해결하지 못한 버그

<img src="/PlanImages/pt3/bug_example.png" width="300px" height="200px"></img>

* 타워가 공격 대상을 찾지 못하는 버그

* 발사체가 충돌처리를 하지 못하고 몬스터에 붙어 있는 버그

## 구현하고 싶었으나 구현하지 못한 부분

* 화면을 드래그해서 맵을 스크롤하는 기능

* 화면을 확대/축소하는 기능

* 사운드 매니저 클래스에 큐를 만들어서 효과음이 많이 겹치는 상황을 방지하는 기능

## 수업에 관해

* 얻은 것
  - 자바의 클래스, 인터페이스, 상속 활용법
  - 안드로이드 콜백 기능 사용법
  - 커스텀 뷰 설계와 애니메이터 기능

* 얻지 못한 것
  - 세세한 자바 문법



# 2차 발표

## 게임 컨셉

<img src="/PlanImages/pt1/sampleImage.png" width="300px" height="200px"></img>

타일맵 기반의 타워 디펜스

## 클래스 소개

* class Collider
  - HashSet<Collider> overlappedColliders 충돌 중인 콜라이더를 모은 컨테이너
  - boolean intersects(Collider) 충돌 판정 함수


* interface collidable
  - onBeginOverlap(Collidable) 충돌 시작 시 실행
  - onEndOverlap(Collidable) 충돌 종료 시 실행
  
* class Tower
  - Collider range 사거리
  - float delay 공격 주기
  - LinkedHashSet<Monster> targetList 사거리 내의 몬스터들
  
  - void fire() 발사체 생성 함수
  - void exceptTarget() 죽은 타겟을 타겟리스트에서 제거
  - onBeginOverlap(Collidable) 사거리에 들어온 몬스터를 타겟리스트에 추가
  - onEndOverlap(Collidable) 사거리를 벗어난 몬스터를 타겟리스트에서 제거
  
* 발사체

<img src="/PlanImages/pt2/cannon_sample.png" width="300px" height="200px"></img>
타겟에게 충돌한 후 데미지를 주는 형태  
<img src="/PlanImages/pt2/laser_sample.png" width="300px" height="200px"></img>
충돌과는 상관 없이 바로 데미지를 주는 형태



* 몬스터
  - float hp 체력
  - boolean isDead 체력이 0보다 낮은지 여부
  - Collider collider 타워 사거리나 발사체와 이벤트를 발생시킬 콜라이더
  - void beDamaged(float) 데미지를 받아들이는 함수
  

##  상호 작용

<img src="/PlanImages/pt2/interaction_image.png" width="300px" height="200px"></img>

몬스터는 타워의 사거리와 만나면 onBeginOverlap 이벤트를 발생시킵니다.
타워는 onBeginOverlap을 통해 들어온 오브젝터를 타겟리스트에 추가하고 발사체에 넘겨줍니다.
발사체는 몬스터의 beDamaged 함수를 호출합니다.

## 진행률

<img src="/PlanImages/pt2/progress.png" width="300px" height="200px"></img>

## 커밋 히스토리

<img src="/PlanImages/pt2/commit_history.png" width="300px" height="200px"></img>



# 1차 발표

## 게임 컨셉

<img src="/PlanImages/pt1/sampleImage.png" width="300px" height="200px"></img>

타일맵 기반의 타워 디펜스

플레이어는  몬스터를 잡아 자원을 획득하며
획득한 자원을 사용해 다시 타워를 타일에 건설합니다.

모든 웨이브를 막아내면 승리하게 되며 그전에 몬스터가 본진을 공격하여 본진의 체력이 0이 되면 패배합니다.

## 주요 요소
* 타워
  - 사거리
  - 공격 주기
  - 발사체 생성기

몬스터에게 데미지를 입히거나 디버프를 주는 등의 상호작용은 발사체에게 위임

* 발사체
  - 공격력
  - 공격 형태

* 몬스터
  - 체력
  - 이동 속도
  - 자원 획득량

* 타일맵
  - 타워 배치 가능 구역
  - 타워 배치가 불가능한 몬스터 이동 경로 구역

* 인게임 UI 기획도

<img src="/PlanImages/pt1/ui.png" width="300px" height="200px"></img>
 
타워와 관련 있는 UI는 하단에 배치

나머지는 상단에 배치

타워 배치할 때 사정거리가 원 이미지로 나타나고

배치 가능한 구역에 들어오면 초록색, 아니면 빨간색의 형태가 나타납니다.

##  게임 흐름 

<img src="/PlanImages/pt1/gameFlow.png" width="300px" height="200px"></img>

게임은 크게 세 가지 액티비티로 분류할 수 있습니다.

메인과 스테이지 선택창 사이를 왔다갔다 할 수 있고 스테이지 선택을 통해 디펜스 액티비티로 들어갑니다.

디펜스 과정에서 게임 오버가 되거나 스테이지를 클리어 할 시 다시 메인 메뉴로 돌아오는 구조입니다.

## 개발 일정
<img src="/PlanImages/pt1/schedule.png" width="300px" height="200px"></img>
