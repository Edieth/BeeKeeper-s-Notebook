package Entity

class Zone {
    private var id: String = ""
    private var name: String = ""
    private var beehiveIds: MutableList<String> = mutableListOf()

    constructor()
    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var BeehiveIds: MutableList<String>
        get() = this.beehiveIds
        set(value) { this.beehiveIds = value }

    override fun toString(): String = "$name (colmenas: ${beehiveIds.size})"
}