package Entity

class InventoryItem {
    private var id: String = ""
    private var name: String = ""
    private var totalQuantity: Int = 0
    private var goodCondition: Int = 0
    private var badCondition: Int = 0
    private var discarded: Int = 0

    constructor()

    constructor(
        id: String,
        name: String,
        totalQuantity: Int,
        goodCondition: Int,
        badCondition: Int,
        discarded: Int
    ) {
        this.id = id
        this.name = name
        this.totalQuantity = totalQuantity
        this.goodCondition = goodCondition
        this.badCondition = badCondition
        this.discarded = discarded
    }

    var ID: String
        get() = this.id
        set(value) { this.id = value }

    var Name: String
        get() = this.name
        set(value) { this.name = value }

    var TotalQuantity: Int
        get() = this.totalQuantity
        set(value) { this.totalQuantity = value }

    var GoodCondition: Int
        get() = this.goodCondition
        set(value) { this.goodCondition = value }

    var BadCondition: Int
        get() = this.badCondition
        set(value) { this.badCondition = value }

    var Discarded: Int
        get() = this.discarded
        set(value) { this.discarded = value }

    fun FullInfoInventory(): String =
        "$this.name $this.totalQuantity  $this.goodCondition  $this.badCondition $this.discarded"
}