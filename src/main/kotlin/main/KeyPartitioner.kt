package main

import com.google.common.collect.Iterables.getFirst
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapred.JobConf
import org.apache.hadoop.mapreduce.Partitioner

class KeyPartitioner : Partitioner<LongWritable, StudentScoreWritable>() {
    override fun getPartition(key: LongWritable, value: StudentScoreWritable, numPartitions: Int): Int {
        return (key.hashCode() and Integer.MAX_VALUE) % numPartitions
    }
}