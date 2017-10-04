package main

import org.apache.hadoop.io.WritableComparable
import org.apache.hadoop.io.WritableComparator

class StudentIdKeyComparator : WritableComparator() {

    override fun compare(k1 : WritableComparable<Any>, k2 : WritableComparable<Any>) : Int{
        return k1.compareTo(k2)
    }
}