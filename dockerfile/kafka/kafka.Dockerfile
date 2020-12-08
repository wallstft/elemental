from ubuntu


RUN apt update
RUN apt install -y curl
RUN apt install -y default-jre
RUN apt install -y gzip
RUN mkdir /usr/local/kafka
RUN curl -o /usr/local/kafka/kafka_2.13-2.4.1.tgz http://apache.mirrors.hoobly.com/kafka/2.4.1/kafka_2.13-2.4.1.tgz
RUN gunzip /usr/local/kafka/kafka_2.13-2.4.1.tgz
RUN cd /usr/local/kafka/ ; tar -xvf /usr/local/kafka/kafka_2.13-2.4.1.tar

RUN echo "#!/bin/bash" > /usr/local/kafka/startup.sh
RUN echo "cd /usr/local/kafka/kafka_2.13-2.4.1" >> /usr/local/kafka/startup.sh 
RUN echo "bin/zookeeper-server-start.sh config/zookeeper.properties &" >> /usr/local/kafka/startup.sh 
RUN echo "sleep 10" >> /usr/local/kafka/startup.sh 
RUN echo "bin/kafka-server-start.sh config/server.properties" >> /usr/local/kafka/startup.sh 

RUN chmod a+x /usr/local/kafka/startup.sh

ENTRYPOINT /usr/local/kafka/startup.sh

