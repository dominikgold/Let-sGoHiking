package com.goldproductions.dominik.letsgohiking.mvvm.base

class Student(firstName: String, lastName: String, private val matrNr: Int) : Person(firstName, lastName) {
    init {
        println("new student: " + firstName + lastName)
    }

    constructor(firstName: String, lastName: String, matrNr: Int, major: String) : this(firstName, lastName, matrNr) {
        this.major = major
    }

    private var age: Int? = null

    // kein Setter, da read-only
    // muss nicht initialisiert werden, da read-only
    // mit überschriebenem Getter
    private val name: String
        get() = firstName + " " + lastName

    // default Getter und Setter
    private var yearOfStudies: Int = 1

    // default Getter, überschriebener Setter
    private var major: String? = null
        set(value) {
            major = value
            yearOfStudies = 1
        }

    override fun toString(): String {
        return name + " " + matrNr
    }

    fun add(a: Int, b: Int = 0): Int {
        return a + b
    }

    fun add() {

        add(2, 3)
        add(a = 2, b = 3)
        add(2)

        val array: Array<String> = arrayOf("a", "b", "c")
        array[0] = array[1]    // "a" wird zu "b"
        val arrayWithSizeFive: Array<String?> = kotlin.arrayOfNulls<String?>(5)

        val mutableList: MutableList<String> = mutableListOf("a", "b", "c") // veränderliche Liste
        val immutableList: List<String> = mutableList   // unveränderliche Liste

        mutableList.add("d")    // nur möglich bei veränderlichen Listen
        immutableList.size      // ergibt 4: immutableList ist immer auf demselben Stand wie mutableList

        for (i in 0..immutableList.size step 1) {
            print(immutableList[i])
        }
        // dasselbe als foreach Schleife
        for (string in immutableList) {
            print(string)
        }
    }
}