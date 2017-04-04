package com.goldproductions.dominik.letsgohiking.examples

open class Person(protected val firstName: String, protected val lastName: String) {

    open fun add (a: Int, b: Int = 0): Int {

        return a + b
    }

    fun test() {

        add(2, 3)
        add(a = 2, b = 3)
        add(2)

        val a: Int = 1
        var b = 2

        val array: Array<String> = arrayOf("a", "b", "c")
        array[0] = array[1]    // "a" wird zu "b"
        val arrayWithSizeFive: Array<String?> = arrayOfNulls<String?>(5)

        val mutableList: MutableList<String> = mutableListOf("a", "b", "c")
        val immutableList: List<String> = listOf("a", "b", "c")

        mutableList.add("d")
//        immutableList.add("d") //compilerfehler

        for (i in 0..immutableList.size step 1) {
            print(immutableList[i])
        }
        // dasselbe als foreach Schleife
        for (string in immutableList) {
            print(string)
        }

        var string: String? = null
//        var string: String = null //compilerfehler


    }
}