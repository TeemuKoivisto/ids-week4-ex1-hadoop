package main

import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer
import java.io.IOException


class IntSumReducer : Reducer<Text, IntWritable, Text, IntWritable>() {
    private val result = IntWritable()

    @Throws(IOException::class, InterruptedException::class)
    override fun reduce(key: Text, values: Iterable<IntWritable>, context: Context) {
        var sum = 0
        for (`val` in values) {
            sum += `val`.get()
        }
        result.set(sum)
        context.write(key, result)
    }
}