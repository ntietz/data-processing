#!/bin/bash

HADOOP_VERSION="hadoop-1.0.1"
MIRROR_BASE="noodle.portalias.net/apache/hadoop/common/"

cd ~

if [ ! -d hadoop ]; then
    mkdir hadoop
fi

cd hadoop

echo "Retrieving the archive..."
wget $MIRROR_BASE$HADOOP_VERSION/$HADOOP_VERSION.tar.gz

echo "Unpacking the archive..."
tar xfz $HADOOP_VERSION.tar.gz

