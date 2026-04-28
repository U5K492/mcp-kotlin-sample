#!/bin/sh
# Maven Wrapper script
set -e

MAVEN_PROJECTBASEDIR="${MAVEN_BASEDIR:-$(cd "$(dirname "$0")" && pwd)}"
WRAPPERJAR="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.jar"
WRAPPERPROPERTIES="$MAVEN_PROJECTBASEDIR/.mvn/wrapper/maven-wrapper.properties"

if [ -n "$JAVA_HOME" ]; then
    JAVACMD="$JAVA_HOME/bin/java"
else
    JAVACMD="$(which java 2>/dev/null || true)"
fi

if [ -z "$JAVACMD" ] || [ ! -x "$JAVACMD" ]; then
    echo "Error: JAVA_HOME is not set and no 'java' command found in PATH." >&2
    exit 1
fi

# Read wrapperUrl from properties
WRAPPER_URL=""
if [ -r "$WRAPPERPROPERTIES" ]; then
    WRAPPER_URL=$(grep "^wrapperUrl" "$WRAPPERPROPERTIES" | sed 's/.*=//')
fi

# Download wrapper jar if missing
if [ ! -e "$WRAPPERJAR" ]; then
    mkdir -p "$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
    if command -v curl > /dev/null 2>&1; then
        curl -fsSL -o "$WRAPPERJAR" "$WRAPPER_URL" || true
    elif command -v wget > /dev/null 2>&1; then
        wget -q -O "$WRAPPERJAR" "$WRAPPER_URL" || true
    fi
fi

exec "$JAVACMD" \
    -classpath "$WRAPPERJAR" \
    "-Dmaven.multiModuleProjectDirectory=$MAVEN_PROJECTBASEDIR" \
    org.apache.maven.wrapper.MavenWrapperMain "$@"
