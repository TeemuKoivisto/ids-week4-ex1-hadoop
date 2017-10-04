package main

import extensions.*
import org.apache.hadoop.conf.Configured
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.*
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat
import org.apache.hadoop.util.Tool
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet

class WordJoinDriver : Configured(), Tool {
    override fun run(args: Array<out String>): Int {
        if (args.size != 4) {
            error("Expected <#reducers> <score.txt_path> <student.txt_path> <output_path>, but received ${args.size} elements.")
        }
        val numReducers = args[0].toInt()
        val scorePath = Path(args[1])
        val studentPath = Path(args[2])
        val outputPath = Path(args[3])

        with(Job.getInstance(this.conf)) {
            jobName = "Teemu Koivisto - Introduction to DataScience Week 4 Exercise 2"

            setJarByClass<WordJoinDriver>()

            addMultipleInputPath<TextInputFormat, ScoreMapper>(scorePath)
            mapOutput<LongWritable, StudentScoreWritable>()
            addMultipleInputPath<TextInputFormat, StudentMapper>(studentPath)
            mapOutput<StudentIdKey, StudentScoreWritable>()

//            partitionerClass = KeyPartitioner::class.java
//            setCombinerKeyGroupingComparatorClass<WritableComparator>()
            addOutputPath(outputPath)
//            setCombinerClass<StudentScoreCombiner>()
            setReducerClass<StudentScoreReducer>(numReducers)
            setOutputFormatClass<TextOutputFormat<StudentIdKey, Text>>()

//            reducerOutput<Text, Text>()

            return if (waitForCompletion(true)) 0 else 1
        }
    }
}