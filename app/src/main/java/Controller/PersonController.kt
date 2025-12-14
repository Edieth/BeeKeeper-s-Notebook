package Controller
import Data.FirebasePersonDataManager
import Data.IDataManager
import Data.RestInventoryDataManager
import Data.RestPersonDataManager
import Entity.InventoryItem
import Entity.Person
import android.content.Context
import android.util.Log.e
import cr.ac.utn.beekeepersnotebook.R

class PersonController {
    //private var dataManager: IDataManager<Person> = FirebasePersonDataManager()
    private var dataManager: IDataManager<Person> = RestPersonDataManager()

    private var context: Context

    constructor(context: Context) {
        this.context = context
    }
//Firebase conexion
    //fun addPerson(person: Person, onResult: (Boolean, String?) -> Unit) {
//dataManager.add(person) { ok, error ->
      //      if (!ok) {
            //    onResult(false, context.getString(R.string.ErrorMsgAdd) + " $error")
//} else {
      //          onResult(true, null)
      //      }
      //  }
//}

   // fun updatePerson(person: Person, onResult: (Boolean, String?) -> Unit) {
     //   dataManager.update(person) { ok, error ->
      //      if (!ok) {
     //           onResult(false, context.getString(R.string.ErrorMsgUpdate) + " ${error ?: ""}")
       //     } else {
      //          onResult(true, null)
           // }
//}
   // }

    //fun deletePerson(id: String, onResult: (Boolean, String?) -> Unit) {
       // dataManager.getById(id) { person ->
          //  if (person == null) {

             //   onResult(false, context.getString(R.string.MsgDataNotFound))
             //   return@getById
           // }

            //dataManager.delete(id) { ok, error ->
               // if (!ok) {
                   // onResult(false, context.getString(R.string.ErrorMsgRemove) + " ${error ?: ""}")
//} else {
                 //   onResult(true, null)
                //}
           // }
        //}
   // }

   // fun getByIdPerson(id: String, onResult: (Person?) -> Unit) {
     //   try {
           // return dataManager.getById(id, onResult)
        //} catch (e: Exception) {
        //    throw Exception(context.getString(R.string.ErrorMsgGetById))
       // }
    //}
    //fun getAll(onResult: (List<Person>) -> Unit) {
      //  dataManager.getAll { list ->
       //     onResult(list)
       // }
   // }

   // fun getByFullName(fullname: String, onResult: (Person?) -> Unit) {
       // try {
           // dataManager.getByFullName(fullname) { person ->
            //    onResult(person)
          //  }
        //} catch (e: Exception) {
           // throw Exception(context.getString(R.string.ErrorMsgGetById))
       // }
    //}

    fun addPerson(person: Person, onResult: (Boolean, String?) -> Unit) {
        dataManager.add(person) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgAdd) + " ${error ?: ""}")
            else onResult(true, null)
        }
    }

    fun updatePerson(person: Person, onResult: (Boolean, String?) -> Unit) {
        dataManager.update(person) { ok, error ->
            if (!ok) onResult(false, context.getString(R.string.ErrorMsgUpdate) + " ${error ?: ""}")
            else onResult(true, null)
        }
    }

    fun deletePerson(id: String, onResult: (Boolean, String?) -> Unit) {
        // opcional: validar que exista antes (como ya hacías)
        dataManager.getById(id) { person ->
            if (person == null) {
                onResult(false, context.getString(R.string.MsgDataNotFound))
                return@getById
            }
            dataManager.delete(id) { ok, error ->
                if (!ok) onResult(false, context.getString(R.string.ErrorMsgRemove) + " ${error ?: ""}")
                else onResult(true, null)
            }
        }
    }

    fun getByIdPerson(id: String, onResult: (Person?) -> Unit) {
        dataManager.getById(id, onResult)
    }

    fun getAll(onResult: (List<Person>) -> Unit) {
        dataManager.getAll(onResult)
    }
    fun getAll(onResult: (List<Person>) -> Unit) {
        dataManager.getAll { list ->
            onResult(list)
        }
    }

    fun getByFullName(fullname: String, onResult: (Person?) -> Unit) {
        dataManager.getByFullName(fullname, onResult)
    }




}


