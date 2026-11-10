@echo off
chcp 65001 > nul
setlocal

set "ROOT=%~dp0.."
cd /d "%ROOT%"

(
    echo [Settings]
    echo package_name = pack
    echo repo_url = tests/test-repository.txt
    echo test_mode = true
    echo package_version = 1.0.0
    echo output_file = out
    echo max_depth = 5
) > settings.ini

echo settings.ini создан
call run.bat

pause