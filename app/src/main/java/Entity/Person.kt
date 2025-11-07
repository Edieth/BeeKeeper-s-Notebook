package Entity

import java.util.Date

class Person {

    private var id: String = ""
    private var name: String = ""
    private var fLastName: String = ""
    private var sLastName: String = ""
    private var email: String = ""
    private var password: String = ""

    constructor()

    constructor(
        id: String,
        name: String,
        fLastName: String,
        sLastName: String,
        email: String,
        password: String
    ) {
        this.id = id
        this.name = name
        this.fLastName = fLastName
        this.sLastName = sLastName
        this.email = email
        this.password = password
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var FLastName: String
        get() = this.fLastName
        set(value) { this.fLastName = value }

    var SLastName: String
        get() = this.sLastName
        set(value) { this.sLastName = value }

    var Email: String
        get() = this.email
        set(value) { this.email = value }

    var Password: String
        get() = this.password
        set(value) { this.password = value }

    fun FullName(): String = "$name $fLastName $sLastName"
}
