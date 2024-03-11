FROM openjdk:17

WORKDIR /usrapp/bin

ENV PORT 57000

COPY /target/classes /usrapp/bin/classes
COPY /target/dependency /usrapp/bin/dependency


CMD ["java","-cp","./classes:./dependency/*","edu.eci.arep.taller6.AppFacade"]