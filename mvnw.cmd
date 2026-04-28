@REM Maven Wrapper script for Windows
@echo off
setlocal

set MAVEN_PROJECTBASEDIR=%~dp0
set WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar
set WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties

if defined JAVA_HOME (
    set JAVACMD=%JAVA_HOME%\bin\java.exe
) else (
    for /f %%i in ('where java 2^>nul') do set JAVACMD=%%i
)

if not defined JAVACMD (
    echo Error: JAVA_HOME is not set and no 'java' found in PATH. >&2
    exit /b 1
)

if not exist "%WRAPPER_JAR%" (
    for /f "tokens=2 delims==" %%i in ('findstr "^wrapperUrl" "%WRAPPER_PROPERTIES%"') do set WRAPPER_URL=%%i
    powershell -Command "Invoke-WebRequest -Uri '%WRAPPER_URL%' -OutFile '%WRAPPER_JAR%'" 2>nul
)

"%JAVACMD%" -classpath "%WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" org.apache.maven.wrapper.MavenWrapperMain %*
