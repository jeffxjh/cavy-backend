@echo off & cd /d "%~dp0"
echo.添加换行符
for %%i in (*.sql) do (
	echo %%i
	echo.>>"%%i"
)
echo -----------------------
echo.合并文件
copy /b *.sql data
echo -----------------------
move /y "data" "init.sql"
pause&exit