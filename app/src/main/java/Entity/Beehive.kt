package Entity
import android.graphics.Bitmap
import java.util.Date

class Beehive {
    private var id: String=""
    private var name: String=""
    private var typeHive: String=""
    private var typeQueen: String=""


    private lateinit var birthdayQueen: Date


        constructor(id: String, name: String, typehive: String
                    , typequeen: String, phone: Int, email: String
                    , birthday: Date){
            this.id=id
            this.name=name
            this.typeHive=typehive
            this.typeQueen=typequeen
            this.birthdayQueen=birthday

        }

        var ID: String
            get() = this.id
            set(value) {this.id=value}

        var Name: String
            get()=this.name
            set(value) {this.name=value}

        var TypeHive: String
            get()=this.typeHive
            set(value) {this.typeHive=value}

        var TypeQueen: String
            get()=this.typeQueen
            set(value) {this.typeQueen=value}


        var BirthDate: Date
            get()=this.birthdayQueen
            set(value) {this.birthdayQueen=value}

    fun FullInfoBehive()="$this.name $this.TypeHive $this.typeQueen $this.birthdayQueen"

    }

