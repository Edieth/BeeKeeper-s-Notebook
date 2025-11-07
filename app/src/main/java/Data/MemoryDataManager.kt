package Data

import java.lang.Exception
import Entity.Person
import Entity.Beehive
import Entity.HarvestRecord
import Entity.InventoryItem
import Entity.Queen
import Entity.Zone
import Data.IDataManager


object MemoryDataManager: IDataManager {
    private val BeehiveList = mutableListOf<Beehive>()
    private val zones = mutableListOf<Zone>()
    private val queens = mutableListOf<Queen>()
    private val inventoryItems = mutableListOf<InventoryItem>()
    private val harvestRecords = mutableListOf<HarvestRecord>()
    private val persons = mutableListOf<Person>()

    fun addPerson(person: Person): Boolean {
        if (persons.any { it.Email == person.Email }) return false
        persons.add(person)
        return true
    }
    fun validateCredentials(email: String, password: String): Person? {
        return persons.find { it.Email == email && it.Password == password }
    }

    fun getBeehivesByZone(zoneId: String): List<Beehive> = BeehiveList.filter { it.ZoneID == zoneId }
    fun addBeehive(beehive: Beehive) {
        BeehiveList.add(beehive)
    }
    fun removeBeehive(id: String) {
        BeehiveList.removeIf { it.ID.trim()==id.trim() }
    }

    fun updateBeehive(beehive: Beehive) {
        removeBeehive(beehive.ID)
        addBeehive(beehive)
    }

    fun getAllBeehives(): List<Beehive> = BeehiveList.toList()

    fun getBeehiveById(id: String): Beehive? {
        try {
            var result = BeehiveList.
            filter { it.ID.trim() == id.trim() }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

    fun getByFullName(fullInfo: String): Beehive? {
        try {
            var result = BeehiveList.
            filter { it.FullInfo().trim() == fullInfo.trim() }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }
        fun addZone(zone: Zone){
            zones.add(zone)
        }
        fun updateZone(zone: Zone) {
            removeZone(zone.ID)
            addZone(zone)
        }
        fun removeZone(id: String) {
            zones.removeIf { it.ID.trim() == id.trim() }
        }
        fun getZoneById(id: String): Zone? {
            val result = zones.filter { it.ID.trim() == id.trim() }
            return if (result.any()) result[0] else null
        }
        fun getAllZones(): List<Zone> = zones.toList()

        fun addQueen(queen: Queen)  {
            queens.add(queen)
        }

        fun updateQueen(queen: Queen) {
            removeQueen(queen.ID)
            addQueen(queen)
        }
        fun removeQueen(id: String) {
            queens.removeIf { it.ID.trim() == id.trim() }
        }
        fun getQueenById(id: String): Queen? {
            val result = queens.
                filter { it.ID.trim() == id.trim() }
            return if (result.any()) result[0] else null
        }

        fun getAllQueens(): List<Queen> = queens.toList()

        fun addInventoryItem(item: InventoryItem) = inventoryItems.add(item)
        fun updateInventoryItem(item: InventoryItem) {
            removeInventoryItem(item.ID)
            addInventoryItem(item)
        }
        fun removeInventoryItem(id: String) { inventoryItems.removeIf { it.ID == id } }
        fun getInventoryById(id: String): InventoryItem? = inventoryItems.firstOrNull { it.ID == id }
        fun getAllInventoryItems(): List<InventoryItem> = inventoryItems.toList()

        fun addHarvest(record: HarvestRecord) = harvestRecords.add(record)
        fun updateHarvest(record: HarvestRecord) {
            removeHarvest(record.ID)
            addHarvest(record)
        }
        fun removeHarvest(id: String) { harvestRecords.removeIf { it.ID == id } }
        fun getHarvestById(id: String): HarvestRecord? = harvestRecords.firstOrNull { it.ID == id }
        fun getAllHarvests(): List<HarvestRecord> = harvestRecords.toList()
        fun getHarvestsByBeehive(beehiveId: String): List<HarvestRecord> = harvestRecords.filter { it.BeehiveID == beehiveId }

}