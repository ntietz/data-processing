
#NODE=$1

NODES=" 76972 26964606 697535 8166296 23033 38493 11887 21205103 21139 382207 "

for NODE in $NODES
do
    echo ""
    echo "Running" $NODE
    # run the job
    hadoop jar dist/jobs.jar pre-bfs /parsedgraph /bfsgraph/$NODE 4 /topnodes/part-00000 1000000 $NODE
    hadoop jar dist/jobs.jar bfs /bfsgraph/$NODE /bfsoutput/$NODE 4 6 1000000
    # cleanup
    hadoop fs -rmr /bfsgraph/$NODE
    hadoop fs -rmr /bfsoutput/$NODE/0
    hadoop fs -rmr /bfsoutput/$NODE/1
    hadoop fs -rmr /bfsoutput/$NODE/2
    hadoop fs -rmr /bfsoutput/$NODE/3
    hadoop fs -rmr /bfsoutput/$NODE/4
    hadoop fs -mv /bfsoutput/$NODE/5 /bfsfinal/$NODE
    hadoop fs -rmr /bfsoutput/$NODE
    echo ""
done

