@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  codeAnalyze startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and CODE_ANALYZE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\codeAnalyze-1.0-SNAPSHOT.jar;%APP_HOME%\lib\c2cpg_3-1.1.786.jar;%APP_HOME%\lib\cpg-core-4.4.2.jar;%APP_HOME%\lib\jgrapht-io-1.4.0.jar;%APP_HOME%\lib\jgrapht-core-1.4.0.jar;%APP_HOME%\lib\javaparser-symbol-solver-core-3.24.0.jar;%APP_HOME%\lib\javaparser-core-3.24.0.jar;%APP_HOME%\lib\commons-cli-1.4.jar;%APP_HOME%\lib\dom4j-2.1.3.jar;%APP_HOME%\lib\log4j-1.2.17.jar;%APP_HOME%\lib\antlr4-4.6.jar;%APP_HOME%\lib\antlr4-runtime-4.7.2.jar;%APP_HOME%\lib\jackson-module-kotlin-2.13.0.jar;%APP_HOME%\lib\kotlin-reflect-1.6.20.jar;%APP_HOME%\lib\kotlin-stdlib-1.6.20.jar;%APP_HOME%\lib\org.eclipse.cdt.core-5.11.0.jar;%APP_HOME%\lib\x2cpg_3-1.1.786.jar;%APP_HOME%\lib\semanticcpg_3-1.1.786.jar;%APP_HOME%\lib\scala-parallel-collections_3-1.0.4.jar;%APP_HOME%\lib\codepropertygraph_3-1.3.534.jar;%APP_HOME%\lib\json4s-native_3-4.0.5.jar;%APP_HOME%\lib\codepropertygraph-protos_3-1.3.534.jar;%APP_HOME%\lib\codepropertygraph-domain-classes_3-1.3.534.jar;%APP_HOME%\lib\overflowdb-traversal_3-1.119.jar;%APP_HOME%\lib\scopt_3-4.0.1.jar;%APP_HOME%\lib\json4s-core_3-4.0.5.jar;%APP_HOME%\lib\json4s-native-core_3-4.0.5.jar;%APP_HOME%\lib\formats_3-1.119.jar;%APP_HOME%\lib\json4s-ast_3-4.0.5.jar;%APP_HOME%\lib\json4s-scalap_3-4.0.5.jar;%APP_HOME%\lib\scala-xml_3-2.0.1.jar;%APP_HOME%\lib\scala3-library_3-3.1.2.jar;%APP_HOME%\lib\spotless-eclipse-cdt-10.5.0.jar;%APP_HOME%\lib\jline-3.21.0.jar;%APP_HOME%\lib\log4j-slf4j-impl-2.17.2.jar;%APP_HOME%\lib\jul-to-slf4j-1.7.32.jar;%APP_HOME%\lib\log4j-slf4j18-impl-2.17.0.jar;%APP_HOME%\lib\spotless-eclipse-base-3.5.2.jar;%APP_HOME%\lib\neo4j-ogm-core-3.2.27.jar;%APP_HOME%\lib\neo4j-ogm-api-3.2.27.jar;%APP_HOME%\lib\overflowdb-core-1.119.jar;%APP_HOME%\lib\slf4j-api-1.8.0-beta4.jar;%APP_HOME%\lib\commons-text-1.6.jar;%APP_HOME%\lib\parser-9.0-9.0.20210312.jar;%APP_HOME%\lib\ast-9.0-9.0.20210312.jar;%APP_HOME%\lib\expressions-9.0-9.0.20210312.jar;%APP_HOME%\lib\util-9.0-9.0.20210312.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\org.eclipse.jface.text-3.20.0.jar;%APP_HOME%\lib\org.eclipse.core.filebuffers-3.7.200.jar;%APP_HOME%\lib\org.eclipse.core.resources-3.16.100.jar;%APP_HOME%\lib\org.eclipse.text-3.12.0.jar;%APP_HOME%\lib\org.eclipse.core.runtime-3.24.0.jar;%APP_HOME%\lib\icu4j-71.1.jar;%APP_HOME%\lib\core-7.2.100.202105180159.jar;%APP_HOME%\lib\parboiled-scala_2.12-1.2.0.jar;%APP_HOME%\lib\better-files_2.13-3.9.1.jar;%APP_HOME%\lib\scala-library-2.13.8.jar;%APP_HOME%\lib\commons-io-2.11.0.jar;%APP_HOME%\lib\jheaps-0.11.jar;%APP_HOME%\lib\ST4-4.0.8.jar;%APP_HOME%\lib\antlr-runtime-3.5.2.jar;%APP_HOME%\lib\org.abego.treelayout.core-1.0.3.jar;%APP_HOME%\lib\javax.json-1.0.4.jar;%APP_HOME%\lib\log4j-core-2.17.2.jar;%APP_HOME%\lib\log4j-api-2.17.2.jar;%APP_HOME%\lib\reflections8-0.11.7.jar;%APP_HOME%\lib\javassist-3.28.0-GA.jar;%APP_HOME%\lib\j-text-utils-0.3.4.jar;%APP_HOME%\lib\guava-31.0.1-jre.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.6.20.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\classgraph-4.8.116.jar;%APP_HOME%\lib\jackson-datatype-jdk8-2.9.9.jar;%APP_HOME%\lib\jackson-datatype-jsr310-2.9.9.jar;%APP_HOME%\lib\jackson-databind-2.13.0.jar;%APP_HOME%\lib\jackson-annotations-2.13.0.jar;%APP_HOME%\lib\org.eclipse.core.jobs-3.12.100.jar;%APP_HOME%\lib\org.eclipse.core.contenttype-3.8.100.jar;%APP_HOME%\lib\org.eclipse.equinox.app-1.6.100.jar;%APP_HOME%\lib\org.eclipse.core.filesystem-1.9.300.jar;%APP_HOME%\lib\org.eclipse.equinox.registry-3.11.100.jar;%APP_HOME%\lib\org.eclipse.equinox.preferences-3.9.100.jar;%APP_HOME%\lib\org.eclipse.jface-3.25.0.jar;%APP_HOME%\lib\org.eclipse.core.commands-3.10.100.jar;%APP_HOME%\lib\org.eclipse.equinox.common-3.16.0.jar;%APP_HOME%\lib\org.eclipse.osgi-3.17.200.jar;%APP_HOME%\lib\failureaccess-1.0.1.jar;%APP_HOME%\lib\listenablefuture-9999.0-empty-to-avoid-conflict-with-guava.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\checker-qual-3.12.0.jar;%APP_HOME%\lib\error_prone_annotations-2.7.1.jar;%APP_HOME%\lib\j2objc-annotations-1.3.jar;%APP_HOME%\lib\jackson-core-2.13.0.jar;%APP_HOME%\lib\parboiled-core-1.2.0.jar;%APP_HOME%\lib\protobuf-java-3.10.0.jar;%APP_HOME%\lib\paranamer-2.8.jar;%APP_HOME%\lib\core-3.1.0.jar;%APP_HOME%\lib\msgpack-core-0.9.1.jar;%APP_HOME%\lib\h2-mvstore-1.4.200.jar;%APP_HOME%\lib\commons-lang-2.6.jar;%APP_HOME%\lib\opencsv-2.4.jar


@rem Execute codeAnalyze
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %CODE_ANALYZE_OPTS%  -classpath "%CLASSPATH%"  %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable CODE_ANALYZE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%CODE_ANALYZE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
