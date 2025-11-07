package Data

import Entity.Beehive

interface IDataManager <T> {
    fun add(behive: Beehive)
    fun update(behive: Beehive)
    fun remove(id: String)
    fun getByFullName(FullInfoBehive: String): Beehive?

    fun insert(item: T): Boolean

    fun update(item: T): Boolean

    fun delete(id: String): Boolean

    fun getById(id: String): T?

    fun getAll(): MutableList<T>
}