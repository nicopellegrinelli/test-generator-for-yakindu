@echo off

set projectPath=ProgettiGit\test-generator-for-yakindu\itemis-ws\Test
set dir=models\from_other_repos

cd C:\Users\lenovo\Desktop

for /f %%f in ('dir /b .\%projectPath%\%dir%\*.ysc ^| sort') do java -jar .\ysc2sctunit-with-default-0.0.2.jar -scc .\itemis_CREATE\scc.bat -projectPath .\%projectPath% -sourceDir %dir% -sourceFile %%~nf -targetPackage statechart -evoTestDir test