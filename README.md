# 🚌 서울 버스 조회 어플리케이션
<p style="text-align: left;">
<img src="https://img.shields.io/badge/AndroidStudio-3DDC84?style=flat-square&logo=AndroidStudio&logoColor=white"/>
<img src="https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white"/>
<img src="https://img.shields.io/badge/Jetpack-4285F4?style=flat-square&logo=android&logoColor=white"/>
<img src="https://img.shields.io/badge/Hilt-EE0000"/>
<img src="https://img.shields.io/badge/Coroutine-8A2BE2"/>
<img src="https://img.shields.io/badge/Flow-017CEE"/>
<img src="https://img.shields.io/badge/Retrofit2-CC0000"/>
<img src="https://img.shields.io/badge/Room-0F9D58"/>
<img src="https://img.shields.io/badge/MVVM-50162D"/>
<img src="https://img.shields.io/badge/Glide-8A2BE2"/>
<img src="https://img.shields.io/badge/DataBinding-3DDC84"/>
</p>

서울 버스 정보를 확인할수 있는 샘플 앱이며 아래와 같은 주요 기능이 제공됩니다.

- 실시간 버스 위치 조회
- 버스 및 정류장 정보 조회
   - 특정 버스의 번호, 경로, 첫차/막차 시간 등의 상세 정보를 확인할 수 있습니다.
   - 특정 정류장의 위치, 해당 정류장을 경유하는 버스 목록, 도착 예정 시간 등의 정보를 제공합니다.
- 근처 버스 정류장 조회
  -  사용자의 현재 위치를 기반으로 가까운 버스 정류장을 조회할 수 있습니다.

> OPEN API를 이용하여 구현했으며 **Mapper**를 이용해 응답 데이터를 매핑하였습니다.

> **MVVM 패턴** 및 **Flow**를 사용하여 네트워크 통신 및 로컬 데이터 처리를 하였습니다.

> **DataBinding**을 사용하여 UI 요소와 데이터 소스 간의 결합을 최소화하고, 코드의 가독성과 유지보수성을 향상시켰습니다.

## 🎈 Android Studio Setup

- Arctic Fox 버전 이상
- Java 17
- Android Sdk 34

## 📱 Screenshots

<img src="https://github.com/oxen6297/Bus/blob/master/screenshots/home.jpg"  width="150" height="280"/> <img src="https://github.com/oxen6297/Bus/blob/master/screenshots/map.jpg"  width="150" height="280"/> <img src="https://github.com/oxen6297/Bus/blob/master/screenshots/detail.jpg"  width="150" height="280"/> <img src="https://github.com/oxen6297/Bus/blob/master/screenshots/station.jpg"  width="150" height="280"/> <img src="https://github.com/oxen6297/Bus/blob/master/screenshots/search.jpg"  width="150" height="280"/>
  
## 🏢 Architecture

<img src="https://github.com/oxen6297/Bus/blob/master/screenshots/mvvm.png"  width="600" height="600"/> <img src="https://github.com/oxen6297/Bus/blob/master/screenshots/graph.png"  width="200" height="600"/>
