@echo off
openfiles > NUL 2>&1
if not %ERRORLEVEL% == 0 (
echo �Ǘ��Ҍ����Ŏ��s���Ă�������
pause
goto :eof
)
cd /d %~dp0
if not "%PROCESSOR_ARCHITECTURE%" == "x86" (
if exist JTalkCOMx64.dll C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe JTalkCOMx64.dll /unregister
if exist JTalkCOMx64.dll C:\Windows\Microsoft.NET\Framework64\v4.0.30319\regasm.exe JTalkCOMx64.dll /codebase
)
if exist JTalkCOMx86.dll C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe JTalkCOMx86.dll /unregister
if exist JTalkCOMx86.dll C:\Windows\Microsoft.NET\Framework\v4.0.30319\regasm.exe JTalkCOMx86.dll /codebase
set /p=�L�[�������ƏI�����܂�<NUL
pause >NUL
echo.

