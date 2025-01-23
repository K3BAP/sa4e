#!/bin/bash
sudo docker run --rm -it --network xmaswishes_xmaswishes_network cassandra:5.0 cqlsh cassandra-node1 9042 --cqlversion='3.4.7'