# Too Many Enemy: 핵앤슬래시 로그라이크 게임

2023 스마트폰게임 프로그래밍의 원활한 수업 참여를 위한 Git Remote Repository입니다.

# 게임 설명

## 게임 컨셉

![1](https://user-images.githubusercontent.com/76027901/229520345-ec551c78-01a9-4ae0-b91f-e4fc17b4b0d1.png)

## 주요 개발 요소

![image](https://github.com/LeeHyRy/SmartphoneGamingProgramming/assets/76027901/b41c94bf-5f03-42da-8ffb-cd500a65dd8d)


## 게임의 흐름

- 플레이어가 적들을 잡아, 경험치를 얻고 레벨업하여 능력치를 골라 성장하고 오래 살아 남는 게임
  ![3](https://user-images.githubusercontent.com/76027901/229520658-655da7e6-08c2-46c7-9ca9-0458250f47d0.png)

## 개발 일정

![image](https://github.com/LeeHyRy/SmartphoneGamingProgramming/assets/76027901/e656f669-c925-4796-b373-763c52759e19)
![image](https://github.com/LeeHyRy/SmartphoneGamingProgramming/assets/76027901/82b202f6-bcf5-4b84-b230-95c4d6b8fcc7)

# 코드 설명

## 뷰 및 클래스

### Title Activity

- highScoreText : 하이스코어를 출력합니다.
  - saveHighScore(int score) : SharedPreferences를 통해 하이스코어 저장
  - getHighScore() : SharedPreferences를 통해 하이스코어 불러오기
  - "MyPrefs"의 "high_score" 변수로 저장됩니다.
- startGameButton : 게임 화면인 Main Activity를 시작합니다.
- ~~hsPlusOneBuuton~~ : 디버그(테스트)용 버튼으로, High Score를 1 증가시켜 저장합니다.

### Main Activity(Game Activity)

- gameView : 게임 뷰
- MainScene : 게임 씬을 담당하여 다른 게임 오브젝트 중첩
- Player : 가운데에 위치할 플레이어에 대한 클래스
- Shell : 총알에 대한 클래스
- Joystick : 플레이어를 조종하는 조이스틱에 대한 클래스
- Enemy(Fly), FlyGen : 적에 대한 클래스
- ExpOrb : 경험치 구슬에 대한 클래스
- Stat : 플레이어가 가질 정보에 대한 하위클래스
- ~~ScrollBackground~~ : 플레이어가 움직일 때마다 움직일 배경 클래스
- TiledBackground : 플레이어가 돌아다니는 배경공간
- PausedScene : 두 일시정지 씬에 대한 클래스
  - case 1: 레벨업에 대한 일시정지 씬
  - case 2: 게임오버에 대한 일시정지 씬

### Frameworks

- objects
  - Sprite, SheetSprite, Score, Button
- res
  - BitmapPool, Sound
- scene
  - BaseScene, RecycleBin
- tiled
  - MapLoader, TiledLayer, TiledMap, TiledTileset
- util
  - CollisionHelper, Gauge
- view
  - GameView, Metrics

# 어려워서 추가적으로 찾아본 부분

- 자동생성되는 BuildConfig에 대한 조정 (해결)
- SharedPreferences 사용 (해결)
- 조이스틱 구현 (해결)

# 자료 출처

## Hooded Protagonist (캐릭터 스프라이트)

https://penzilla.itch.io/hooded-protagonist

## Top Down Forest Tileset (배경 스프라이트)

https://pixivan.itch.io/top-down-forest-tileset
