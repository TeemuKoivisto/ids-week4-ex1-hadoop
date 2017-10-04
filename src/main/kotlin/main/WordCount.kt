package main

import java.io.IOException
import java.util.StringTokenizer

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.Path
import org.apache.hadoop.io.IntWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Job
import org.apache.hadoop.mapreduce.Mapper
import org.apache.hadoop.mapreduce.Reducer
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat

object WordCount {

    class TokenizerMapper : Mapper<Any, Text, Text, IntWritable>() {
        private val word = Text()

        @Throws(IOException::class, InterruptedException::class)
        public override fun map(key: Any, value: Text, context: Context) {
            val itr = StringTokenizer(value.toString())
            while (itr.hasMoreTokens()) {
                word.set(itr.nextToken())
                context.write(word, one)
            }
        }

        companion object {
            private val one = IntWritable(1)
        }
    }

    class IntSumReducer : Reducer<Text, IntWritable, Text, IntWritable>() {
        private val result = IntWritable()

        @Throws(IOException::class, InterruptedException::class)
        public override fun reduce(key: Text, values: Iterable<IntWritable>,
                                   context: Context
        ) {
            var sum = 0
            for (`val` in values) {
                sum += `val`.get()
            }
            result.set(sum)
            context.write(key, result)
        }
    }

    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        val conf = Configuration()
        val job = Job.getInstance(conf, "word count")
        job.setJarByClass(WordCount::class.java)
        job.setMapperClass(TokenizerMapper::class.java)
        job.setCombinerClass(IntSumReducer::class.java)
        job.setReducerClass(IntSumReducer::class.java)
        job.outputKeyClass = Text::class.java
        job.outputValueClass = IntWritable::class.java
        FileInputFormat.addInputPath(job, Path(args[0]))
        FileOutputFormat.setOutputPath(job, Path(args[1]))
        System.exit(if (job.waitForCompletion(true)) 0 else 1)
    }
}