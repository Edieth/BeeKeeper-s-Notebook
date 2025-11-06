package Data

import Entity.Beehive
import java.lang.Exception
import Entity.HarvestRecord
import Entity.InventoryItem
import Entity.Queen
import Entity.Zone
import Data.IDataManager


object MemoryDataManager: IDataManager {
    private var BeehiveList = mutableListOf<Beehive>()
    private var zones = mutableListOf<Zone>()
    private var beehives = mutableListOf<Beehive>()
    private val queens = mutableListOf<Queen>()
    private var inventoryItems = mutableListOf<InventoryItem>()
    private var harvestRecords = mutableListOf<HarvestRecord>()

    override fun add(beehive: Beehive) {
        BeehiveList.add(beehive)
    }

    override fun remove(id: String) {
        BeehiveList.removeIf { it.ID.trim()==id.trim() }
    }

    override fun update(beehive: Beehive) {
        remove(beehive.ID)
        add(beehive)
    }

    override fun getAll()= BeehiveList

    override fun getById(id: String): Beehive? {
        try {
            var result = BeehiveList.
            filter { it.ID.trim() == id.trim() }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

    override fun getByFullName(fullInfo: String): Beehive? {
        try {
            var result = BeehiveList.
            filter { it.FullInfo().trim() == fullInfo.trim() }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }

        fun addZone(zone: Zone) = zones.add(zone)
        fun updateZone(zone: Zone) {
            removeZone(zone.ID)
            addZone(zone)
        }
        fun removeZone(id: String) { zones.removeIf { it.ID == id } }
        fun getZoneById(id: String): Zone? = zones.firstOrNull { it.ID == id }
        fun getAllZones(): List<Zone> = zones.toList()

        fun addBeehive(beehive: Beehive) = beehives.add(beehive)
        fun updateBeehive(beehive: Beehive) {
            removeBeehive(beehive.ID)
            addBeehive(beehive)
        }
        fun removeBeehive(id: String) { beehives.removeIf { it.ID == id } }
        fun getBeehiveById(id: String): Beehive? = beehives.firstOrNull { it.ID == id }
        fun getAllBeehives(): List<Beehive> = beehives.toList()
        fun getBeehivesByZone(zoneId: String): List<Beehive> = beehives.filter { it.ZoneID == zoneId }

        fun addQueen(queen: Queen) = queens.add(queen)
        fun updateQueen(queen: Queen) {
            removeQueen(queen.ID)
            addQueen(queen)
        }
        fun removeQueen(id: String) { queens.removeIf { it.ID == id } }
        fun getQueenById(id: String): Queen? = queens.firstOrNull { it.ID == id }
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