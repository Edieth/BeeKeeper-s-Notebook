package Entity

class Province {

    private var name: String = ""
    private var states: MutableList<String> = mutableListOf()


    constructor(name: String, states: MutableList<String>) {
        this.name = name
        this.states = states
    }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var States: MutableList<String>
        get() = this.states
        set(value) { this.states = value }


}