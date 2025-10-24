package Controller
import Data.IDataManager
import Data.MemoryDataManager
import Entity.Beehive
import android.content.Context
import cr.ac.utn.beekeepersnotebook.R

class BeehiveController {
    private var dataManager: IDataManager = MemoryDataManager
    private var context: Context

    constructor(context: Context){
        this.context=context
    }

    fun addBeehive(beehive: Beehive){
        try {
            dataManager.add(beehive)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgAdd))
        }
    }

    fun updatePerson(beehive: Beehive){
        try {
            dataManager.update(Beehive)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getPeople(): List<Beehive>{
        try {
            return dataManager.getAll()
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getById(id: String): Beehive{
        try {
            val result = dataManager.getById(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.ErrorMsgGetById))
            }
            return result
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

    fun getByFullName(id: String): Beehive{
        try {
            val result = dataManager.getByFullName(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.ErrorMsgGetById))
            }
            return result
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
    }

}