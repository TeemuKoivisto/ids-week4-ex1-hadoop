package main

import extensions.split
import extensions.toText
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.io.Writable
import org.apache.hadoop.io.WritableUtils
import org.apache.hadoop.mapreduce.Mapper
import java.io.DataInput
import java.io.DataOutput
import java.io.IOException
import java.util.regex.Pattern

/**
 * Maps values from score_3000.txt as LongWritable-Text key-value pairs
 * that look like: (20096468734, "score: 90,80,40")
 * Mapper<KEYIN,VALUEIN,KEYOUT,VALUEOUT>
 */
class ScoreMapper : Mapper<Any, Text, StudentIdKey, StudentScoreWritable>() {

    override fun map(key: Any, value: Text, context: Context) {
        // Splits the words in the row by comma
        val words = value.toString().split(",")
        val studentId = words.get(0).toLong()
        val score1 = words.get(1).toInt()
        val score2 = words.get(2).toInt()
        val score3 = words.get(3).toInt()
//        if (80 > score1 && score2 <= 95) {
//            val body = "score" + score1 + "," + score2 + "," + words.get(3)
            context.write(StudentIdKey(LongWritable(studentId), "score"), StudentScoreWritable(score1, score2, score3))
//        }
    }
}

//class ScoreMapper : Mapper<Any, Text, LongWritable, StudentScoreWritable>() {
//
//    override fun map(key: Any, value: Text, context: Context) {
//        // Splits the words in the row by comma
//        val words = value.toString().split(",")
//        val studentId = words.get(0).toLong()
//        val score1 = words.get(1).toInt()
//        val score2 = words.get(2).toInt()
//        val score3 = words.get(3).toInt()
////        if (80 > score1 && score2 <= 95) {
////            val body = "score" + score1 + "," + score2 + "," + words.get(3)
//        context.write(LongWritable(studentId), StudentScoreWritable(score1, score2, score3))
////        }
//    }
//}