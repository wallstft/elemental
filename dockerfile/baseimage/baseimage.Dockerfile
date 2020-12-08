from ubuntu


RUN apt update
RUN apt install -y curl
RUN apt install -y default-jre
RUN apt install -y gzip
RUN apt install -y iputils-ping

