package Data

import Entity.Beehive
import java.lang.Exception

object MemoryDataManager: IDataManager {
    private var BeehiveList = mutableListOf<Beehive>()

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
            filter { it.FullInfoBehive().trim() == fullInfo.trim() }
            return if (result.any()) result[0] else null
        }catch (e: Exception){
            throw e
        }
    }
}