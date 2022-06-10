package danp.lab05.jetpack_room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CEM_Dao {

    @Insert
    fun insertCEM(cem: CEM )

    @Update
    fun updateCEM(cem: CEM)

    @Query("DELETE FROM CEMS WHERE CEM_id = :id")
    fun deleteCEM(id: Int)

    @Query("SELECT * FROM CEMS WHERE CEM_id = :id")
    fun findProduct(id: Int): CEM

    @Query("SELECT * FROM CEMS")
    fun getAllProducts(): LiveData<List<CEM>>
}