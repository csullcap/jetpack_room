package danp.lab05.jetpack_room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CEMS")
class CEM {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "CEM_id")
    var CEM_id: Int = 0

    @ColumnInfo(name = "CEM_name")
    var CEM_name: String = ""

    @ColumnInfo(name = "CEM_departamento")
    var CEM_departamento: String= ""

    @ColumnInfo(name = "CEM_encargado")
    var CEM_encargado:String=""


    constructor() {}

    constructor(CEM_id: Int, CEM_name: String, CEM_departamento: String, CEM_encargado: String) {
        this.CEM_id = CEM_id
        this.CEM_name = CEM_name
        this.CEM_departamento = CEM_departamento
        this.CEM_encargado = CEM_encargado
    }

    constructor(CEM_name: String, CEM_departamento: String, CEM_encargado: String) {
        this.CEM_name = CEM_name
        this.CEM_departamento = CEM_departamento
        this.CEM_encargado = CEM_encargado
    }


}