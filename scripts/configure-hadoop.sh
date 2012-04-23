#!/bin/bash

HADOOP_VERSION="hadoop-1.0.1"
CONF_FILES="scripts/conf/"

echo "Copying configuration files..."
cp $CONF_FILES/* $HADOOP/conf/

hadoop namenode -format

