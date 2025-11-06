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

    fun updateBeehive(beehive: Beehive){
        try {
            dataManager.update(beehive)
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgUpdate))
        }
    }

    fun getBeehive(): List<Beehive>{
        try {
            return dataManager.getAll()
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetAll))
        }
    }

    fun getById(id: String): Beehive?{
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

    fun getFullInfoBehive(id: String): Beehive{
        try {
            val result = dataManager.FullInfoBehive(id)
            if (result == null){
                throw Exception(context
                    .getString(R.string.ErrorMsgGetById))
            }
            return result
        }catch (e: Exception){
            throw Exception(context
                .getString(R.string.ErrorMsgGetById))
        }
        fun removeBeehive(id: String){
            try{
                val result = dataManager.getById(id)
                if (result == null){
                    throw Exception(context
                        .getString(R.string.MsgDataNoFound))
                }
                dataManager.remove(id)
            }catch (e: Exception){
                throw Exception(context
                    .getString(R.string.ErrorMsgRemove))
            }
    }
    }
}