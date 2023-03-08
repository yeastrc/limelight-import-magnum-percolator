FROM amazoncorretto:11.0.17

ADD build/libs/magnumToLimelightXML.jar  /usr/local/bin/magnumToLimelightXML.jar
ADD entrypoint.sh /usr/local/bin/entrypoint.sh
ADD magnumPercolator2LimelightXML /usr/local/bin/magnumPercolator2LimelightXML

RUN chmod 755 /usr/local/bin/entrypoint.sh && \
    chmod 755 /usr/local/bin/magnumPercolator2LimelightXML && \
    yum install -y procps && \
    yum clean all && \
    rm -rf /var/cache/yum

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD []
