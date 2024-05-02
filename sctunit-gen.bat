@echo off

set projectPath=ProgettiGit\test-generator-for-yakindu\itemis-ws\TargetStatecharts
set dir=models\sct

cd C:\Users\lenovo\Desktop

for /f %%f in ('dir /b .\%projectPath%\%dir%\*.ysc') do java -jar .\ysc2sctunit-0.0.2.jar -scc .\itemis_CREATE\scc.bat -projectPath .\%projectPath% -sourceDir %dir% -sourceFile %%~nf -targetPackage statechart -evoTestDir test -evoSearchBudget 1