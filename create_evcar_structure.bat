REM =====================================================
REM [용어 설명]
REM =====================================================
REM  REM         = 주석. Java의 // 와 같음. 실행 안 되고 메모용
REM  @echo off   = 명령어 실행 과정을 화면에 안 보여줌 (깔끔하게)
REM  echo        = 화면에 메시지 출력. Java의 System.out.println() 과 같음
REM  chcp 65001  = 한글이 깨지지 않게 인코딩을 UTF-8로 설정
REM  >nul        = 출력 메시지를 숨김 (결과를 화면에 안 보여줌)
REM  2>nul       = 에러 메시지를 숨김 (이미 폴더가 있어도 에러 안 뜸)
REM  mkdir       = make directory. 폴더를 만드는 명령어
REM  pause       = "아무 키나 누르세요" 멈춤. 결과 확인용
REM ================================================================
REM [사용법]
REM  1. 이 파일을 EVCar 프로젝트 루트 폴더에 넣기
REM     (src 폴더, build.gradle 이 보이는 그 위치)
REM  2. 더블클릭
REM  3. STS에서 프로젝트 우클릭 → Refresh (F5)
REM ================================================================

@echo off
chcp 65001>nul

echo ===== EVCar 프로젝트 폴더 구조 생성 시작 =====
echo.

REM - JAVA 패키지 (Domain)

mkdir "src\main\java\com\evcar\domain\user"
mkdir "src\main\java\com\evcar\domain\vehicle"
mkdir "src\main\java\com\evcar\domain\wishlist"
mkdir "src\main\java\com\evcar\domain\consultation"
mkdir "src\main\java\com\evcar\domain\inquiry"
mkdir "src\main\java\com\evcar\domain\faq"
mkdir "src\main\java\com\evcar\domain\chatbot"
mkdir "src\main\java\com\evcar\domain\charging"
mkdir "src\main\java\com\evcar\domain\mypage"

REM - JAVA 패키지 (Repository)

mkdir "src\main\java\com\evcar\repository\user"
mkdir "src\main\java\com\evcar\repository\vehicle"
mkdir "src\main\java\com\evcar\repository\wishlist"
mkdir "src\main\java\com\evcar\repository\consultation"
mkdir "src\main\java\com\evcar\repository\inquiry"
mkdir "src\main\java\com\evcar\repository\faq"
mkdir "src\main\java\com\evcar\repository\chatbot"
mkdir "src\main\java\com\evcar\repository\charging"

REM - JAVA 패키지 (Service)

mkdir "src\main\java\com\evcar\service\user"
mkdir "src\main\java\com\evcar\service\login"
mkdir "src\main\java\com\evcar\service\mypage"
mkdir "src\main\java\com\evcar\service\vehicle"
mkdir "src\main\java\com\evcar\service\wishlist"
mkdir "src\main\java\com\evcar\service\consultation"
mkdir "src\main\java\com\evcar\service\inquiry"
mkdir "src\main\java\com\evcar\service\faq"
mkdir "src\main\java\com\evcar\service\chatbot"
mkdir "src\main\java\com\evcar\service\charging"
mkdir "src\main\java\com\evcar\service\admin"

REM - JAVA 패키지 (Controller)

mkdir "src\main\java\com\evcar\controller"
mkdir "src\main\java\com\evcar\controller\user"
mkdir "src\main\java\com\evcar\controller\login"
mkdir "src\main\java\com\evcar\controller\mypage"
mkdir "src\main\java\com\evcar\controller\vehicle"
mkdir "src\main\java\com\evcar\controller\wishlist"
mkdir "src\main\java\com\evcar\controller\consultation"
mkdir "src\main\java\com\evcar\controller\inquiry"
mkdir "src\main\java\com\evcar\controller\faq"
mkdir "src\main\java\com\evcar\controller\chatbot"
mkdir "src\main\java\com\evcar\controller\charging"
mkdir "src\main\java\com\evcar\controller\admin"

REM - JAVA 패키지 (dto)

mkdir "src\main\java\com\evcar\dto\user"
mkdir "src\main\java\com\evcar\dto\login"
mkdir "src\main\java\com\evcar\dto\mypage"
mkdir "src\main\java\com\evcar\dto\vehicle"
mkdir "src\main\java\com\evcar\dto\consultation"
mkdir "src\main\java\com\evcar\dto\inquiry"
mkdir "src\main\java\com\evcar\dto\chatbot"
mkdir "src\main\java\com\evcar\dto\charging"
mkdir "src\main\java\com\evcar\dto\admin"

REM - JAVA 패키지 (공통)

mkdir "src\main\java\com\evcar\config"
mkdir "src\main\java\com\evcar\config\user"
mkdir "src\main\java\com\evcar\common\vo"
mkdir "src\main\java\com\evcar\common\util"
mkdir "src\main\java\com\evcar\interceptor"
mkdir "src\main\java\com\evcar\exception"
mkdir "src\main\java\com\evcar\validator\mypage"
mkdir "src\main\java\com\evcar\mapper\charging"
mkdir "src\main\java\com\evcar\api\charging"

REM - templates (resources)

mkdir "src\main\resources\templates\layout"
mkdir "src\main\resources\templates\fragments"
mkdir "src\main\resources\templates\error"
mkdir "src\main\resources\templates\user"
mkdir "src\main\resources\templates\login"
mkdir "src\main\resources\templates\mypage"
mkdir "src\main\resources\templates\vehicle"
mkdir "src\main\resources\templates\wishlist"
mkdir "src\main\resources\templates\consultation"
mkdir "src\main\resources\templates\inquiry"
mkdir "src\main\resources\templates\faq"
mkdir "src\main\resources\templates\charging"
mkdir "src\main\resources\templates\admin\user"
mkdir "src\main\resources\templates\admin\vehicle"
mkdir "src\main\resources\templates\admin\consultation"
mkdir "src\main\resources\templates\admin\inquiry"
mkdir "src\main\resources\templates\admin\faq"

REM - static resources (CSS, JS, Image)

mkdir "src\main\resources\static\css"
mkdir "src\main\resources\static\js"
mkdir "src\main\resources\static\images"

echo.
echo ===== 생성 완료 =====
echo.
echo 폴더에서 .bat 파일 실행 후, STS에서 프로젝트 우클릭 → Refresh 하면 폴더가 보입니다.
echo.
pause