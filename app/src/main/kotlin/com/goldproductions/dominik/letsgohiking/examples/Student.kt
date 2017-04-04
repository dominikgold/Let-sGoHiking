package com.goldproductions.dominik.letsgohiking.examples

class Student(firstName: String, lastName: String,
              private val matrNr: Int) : Person(firstName, lastName) {

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

    override fun add(a: Int, b: Int): Int {

        return a + b
    }
}