@echo off

set basePath=C:\Users\lenovo\Desktop
set wsPath=ProgettiGit\test-generator-for-yakindu\itemis-ws
set projectName=TargetStatecharts
set dir=models\sct

for /f %%f in ('dir /b %basePath%\%wsPath%\%project%\%dir%\*.cov') do java -jar %basePath%\coverage.jar %basePath%\%wsPath%\ %projectName% %dir%\%%f