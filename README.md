# Github Bookmark

GitHub 사용자를 검색하고 즐겨찾기를 관리하는 Android 앱입니다.

## 스크린샷

| GitHub 검색 | 북마크 |
|:-----------:|:------:|
| 사용자 이름으로 검색 | 저장된 북마크 목록 |

## 주요 기능

- GitHub 사용자 검색
- 사용자 북마크 추가/제거
- 알파벳 헤더로 그룹화된 사용자 목록
- 오프라인 북마크 저장 (Room)

## 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Kotlin 2.0.21 |
| SDK | minSdk 24, targetSdk 35 |
| Architecture | Clean Architecture, MVVM |
| UI | Jetpack Compose, Material3 |
| Async | Coroutines 1.9.0, StateFlow |
| DI | Hilt 2.54 |
| Network | Ktor 3.0.3 |
| Database | Room 2.6.1 |
| Image | Coil 2.7.0 |
| Build | Gradle 8.10.2, AGP 8.7.3 |

## 아키텍처

Clean Architecture 기반의 멀티 모듈 구조:

```
app                    # Entry point, Hilt DI modules
├── presentation       # Compose Screens, ViewModels
├── component          # Reusable Compose components, Theme
├── domain             # Use cases, Repository interfaces, Models
├── data               # Repository implementations, Data source interfaces
├── remote             # Ktor HTTP client, GitHub API
└── local              # Room database, DAOs
```

### 데이터 흐름

```
Composable Screen → ViewModel → UseCase → Repository → DataSource
```

### 모듈 의존성

```
app → presentation/remote/local → data → domain
```

## 빌드 및 실행

### 요구사항

- Android Studio Ladybug 이상
- JDK 17
- Android SDK 35

### 명령어

```bash
# APK 빌드
./gradlew assembleDebug
./gradlew assembleRelease

# 테스트
./gradlew test

# 코드 스타일
./gradlew ktlintCheck
./gradlew ktlintFormat

# 기기에 설치
./gradlew installDebug
```

## 프로젝트 구조

```
├── app/                    # Application 모듈
│   └── di/                 # Hilt DI modules
├── presentation/           # UI 레이어
│   └── ui/
│       ├── main/           # MainActivity, MainScreen
│       ├── github/         # GitHub 검색 화면
│       ├── bookmark/       # 북마크 화면
│       └── common/         # 공통 UI 컴포넌트
├── component/              # 재사용 Compose 컴포넌트
│   └── compose/
│       ├── theme/          # Color, Type, Theme
│       └── *.kt            # UserItemCard, SearchTextField 등
├── domain/                 # 비즈니스 로직
│   ├── model/              # Domain 모델
│   ├── repository/         # Repository 인터페이스
│   └── usecase/            # Use cases
├── data/                   # 데이터 레이어
│   ├── model/              # Data 모델 (Mapper)
│   ├── repository/         # Repository 구현체
│   └── source/             # DataSource 인터페이스
├── remote/                 # 원격 데이터
│   ├── api/                # API 인터페이스 및 구현
│   └── response/           # API Response 모델
└── local/                  # 로컬 데이터
    ├── database/           # Room Database
    ├── dao/                # Room DAO
    └── entity/             # Room Entity
```

## License

```
MIT License
```
