@echo off
chcp 65001 > nul
setlocal

cd /d "%~dp0"

where javac > nul 2>&1
if errorlevel 1 (
    echo Java JDK не установлен или не добавлен в PATH.
    pause
    exit /b 1
)

if not exist "settings.ini" (
    echo Отсутствует файл setting.ini...
    exit /b 1
)

if not exist "out" mkdir "out"
if not exist "src\*.java" (
    echo Java-файлы не найдены в папке src.
    pause
    exit /b 1
)

dir /s /b "src\*.java" > sources.txt
javac -encoding UTF-8 -d out @sources.txt
if errorlevel 1 (
    echo Ошибка компиляции
    del sources.txt
    pause
    exit /b 1
)
del sources.txt

echo Запуск MODV
java -cp out Main

echo Конец программы
pause
