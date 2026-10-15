@echo off
setlocal
cd /d "%~dp0"
echo Starting Bank Account Management System and OOP Labs...
javac --release 21 Main.java
if %errorlevel% neq 0 (
    echo [ERROR] Failed to compile Main.java. Please ensure JDK is on your PATH.
    pause
    exit /b %errorlevel%
)
java Main %*
endlocal
