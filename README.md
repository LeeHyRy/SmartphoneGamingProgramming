# Too Many Enemy: 핵앤슬래시 로그라이크 게임
2023 스마트폰게임 프로그래밍의 원활한 수업 참여를 위한 Git Remote Repository입니다.
# 게임 설명
## 게임 컨셉
![1](https://user-images.githubusercontent.com/76027901/229520345-ec551c78-01a9-4ae0-b91f-e4fc17b4b0d1.png)
## 주요 개발 요소
![2](https://user-images.githubusercontent.com/76027901/229520491-fc96c18e-2fa6-40c4-8147-65da440c2d4c.png)
## 게임의 흐름
- 플레이어가 적들을 잡아, 경험치를 얻고 레벨업하여 능력치를 골라 성장하고 오래 살아 남는 게임
![3](https://user-images.githubusercontent.com/76027901/229520658-655da7e6-08c2-46c7-9ca9-0458250f47d0.png)
## 개발 일정
![4](https://user-images.githubusercontent.com/76027901/236857310-882a4649-902e-45cd-8783-c59098f09c99.png)
![5](https://user-images.githubusercontent.com/76027901/236860791-b564a2aa-70d5-4d36-86ba-e4d7e22c62e8.png)
# 코드 설명
## 뷰 및 클래스
### Title Activity
- highScoreText : 하이스코어를 출력합니다.
    - saveHighScore(int score) : SharedPreferences를 통해 하이스코어 저장
    - getHighScore() : SharedPreferences를 통해 하이스코어 불러오기
    - "MyPrefs"의 "high_score" 변수로 저장됩니다.
- startGameButton : 게임 화면인 Main Activity를 시작합니다.
- hsPlusOneBuuton : 디버그(테스트)용 버튼으로, High Score를 1 증가시켜 저장합니다.
### Main Activity(Game Activity)
- gameView : 게임 뷰입니다.
- MainScene : 게임 씬을 담당하여 다른 게임 오브젝트들을 쌓습니다.
- Player : 가운데에 위치할 플레이어에 대한 클래스입니다.
- ScrollBackground : 플레이어가 움직일 때마다 움직일 배경 클래스입니다.
# 어려워서 추가적으로 찾아본 부분
- 자동생성되는 BuildConfig에 대한 조정
- SharedPreferences 사용 (해결)
- 조이스틱 구현
# 자료 출처
## Hooded Protagonist (캐릭터 스프라이트)
https://penzilla.itch.io/hooded-protagonist
## Top Down Forest Tileset (배경 스프라이트)
https://pixivan.itch.io/top-down-forest-tileset
