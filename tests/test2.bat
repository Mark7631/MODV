@echo off
chcp 65001 > nul
setlocal

set "ROOT=%~dp0.."
cd /d "%ROOT%"

(
    echo [Settings]
    echo package_name =
    echo repo_url = pppp
    echo test_mode =
    echo package_version = 1.0.0
    echo output_file =
    echo max_depth = -1
) > settings.ini

echo settings.ini создан
call run.bat

pause