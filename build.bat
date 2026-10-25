@echo off
echo ==============================================================
echo   Building Bus Reservation Management System...
echo ==============================================================

if not exist "bin" mkdir bin

javac -d bin src\com\busreservation\model\*.java src\com\busreservation\util\*.java src\com\busreservation\data\*.java src\com\busreservation\service\*.java src\com\busreservation\Main.java

if %ERRORLEVEL% EQU 0 (
    echo [OK] Build successful! Binaries created in \bin folder.
    echo Run 'run.bat' to start the application.
) else (
    echo [ERROR] Compilation failed. Please ensure JDK 8+ is installed and on your PATH.
)
pause
