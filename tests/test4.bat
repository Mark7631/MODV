@echo off
chcp 65001 > nul
setlocal

set "ROOT=%~dp0.."
cd /d "%ROOT%"

(
    echo [Settings]
    echo package_name = Elasticsearch.Net
    echo repo_url = https://api.nuget.org/v3-flatcontainer/
    echo test_mode = false
    echo package_version = 7.17.5
    echo output_file = dependencies.svg
    echo max_depth = 3
) > settings.ini

echo settings.ini создан
call run.bat

pause