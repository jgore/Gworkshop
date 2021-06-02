#!/usr/bin/env bash
#required for proper logging of source in graylog
export SERVER_HOSTNAME=$HOSTNAME
if [ ! -z "$CREDENTIALS_FILE_NAME" ] && [ -r "/run/secrets/$CREDENTIALS_FILE_NAME" ]; then
	. /run/secrets/$CREDENTIALS_FILE_NAME
fi
java -agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS  -jar GoreIT-0.0.1-SNAPSHOT.jar