package main

import extensions.split
import extensions.toText
import org.apache.hadoop.io.LongWritable
import org.apache.hadoop.io.Text
import org.apache.hadoop.mapreduce.Mapper
import java.util.regex.Pattern

/**
 * Maps the values into key-value tuple: (20096468734, "student:Testi9,1995")
 */
class StudentMapper : Mapper<Any, Text, StudentIdKey, StudentScoreWritable>() {

    override fun map(key: Any, value: Text, context: Context) {
        // Splits the words in the row by comma
        val words = value.toString().split(",")
        val studentId = words[0].toLong()
        val name = words[1].toString()
        val year = words[2].toInt()
        context.write(StudentIdKey(LongWritable(studentId), "student"), StudentScoreWritable(name, year))
//        if (year > 1990) {
//            val body = "student" + words.get(1) + "," + year
//            context.write(LongWritable(studentId), body.toText())
//        }
    }
}

//class StudentMapper : Mapper<Any, Text, LongWritable, StudentScoreWritable>() {
//
//    override fun map(key: Any, value: Text, context: Context) {
//        // Splits the words in the row by comma
//        val words = value.toString().split(",")
//        val studentId = words[0].toLong()
//        val name = words[1].toString()
//        val year = words[2].toInt()
//        context.write(LongWritable(studentId), StudentScoreWritable(name, year))
////        if (year > 1990) {
////            val body = "student" + words.get(1) + "," + year
////            context.write(LongWritable(studentId), body.toText())
////        }
//    }
//}