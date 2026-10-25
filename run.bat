@echo off
if not exist "bin\com\busreservation\Main.class" (
    echo [!] Project not built yet. Running build first...
    call build.bat
)

cls
java -cp bin com.busreservation.Main
pause
