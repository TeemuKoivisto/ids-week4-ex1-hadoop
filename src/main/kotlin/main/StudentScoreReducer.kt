package main

import extensions.toText
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.NullWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Reducer

/**
 * Joins key with Score and Student values into single row:
 * (20096468734, ["score: 90,80,40", "student:Testi9,1995"])
 * -> "20096468734,Testi9,1995,90,80,40"
 */
class StudentScoreReducer : Reducer<StudentIdKey, StudentScoreWritable, StudentIdKey, Text>() {

    override fun reduce(key: StudentIdKey, values: Iterable<StudentScoreWritable>, context: Context) {
//        error("Size " + values.toList().size)
//        error("Eka " + values.first().toString() + " toka " + values.last().toString() + " yht " + values.toList().toString())
        with(values.toList()) {
            context.write(StudentIdKey, this.toText())
        }
//        val asList = values.toList()
//        context.write(key, ("rs" + asList.size + ":" + asList.toString()).toText())
//        for (thing : Text in asList) {
//            context.write(key, thing)
//        }
//        if (asList.size == 2) {
//            val text1 = asList.toString()
//            val text2 = asList[1].toString()
//            val score = if (text1.contains("score")) substringScore(text1) else substringScore(text2)
//            val student = if (text1.contains("student")) substringStudent(text1) else substringStudent(text2)
//            val joined = key.toString() + "," + student + "," + score
//            context.write(key, (text1 + " " + text2).toText())
//            context.write(key, text1.toText())
//        }
    }

    fun substringScore(value: String) : String {
        return value.substring(4, value.length)
    }

    fun substringStudent(value: String) : String {
        return value.substring(6, value.length)
    }
}

class StudentScoreCombiner : Reducer<LongWritable, Text, LongWritable, Text>() {

    override fun reduce(key: LongWritable, values: Iterable<Text>, context: Context) {
        val asList = values.toList()
        context.write(key, ("cs" + asList.size + ":" + asList.toString()).toText())
    }

}