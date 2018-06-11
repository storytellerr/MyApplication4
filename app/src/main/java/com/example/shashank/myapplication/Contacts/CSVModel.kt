package com.example.shashank.myapplication.Contacts

class Customer {
    var name: String? = null
    var number: Int = 0

    constructor() {}
    constructor( name: String?,  number: Int) {
        this.name = name
        this.number = number
    }

}