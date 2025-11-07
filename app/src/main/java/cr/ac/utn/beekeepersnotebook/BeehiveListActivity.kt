package cr.ac.utn.beekeepersnotebook
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import Controller.BeehiveController
import cr.ac.utn.beekeepersNotebook.R

class BeehiveListActivity : AppCompatActivity(){
        private val ctrl = BeehiveController()
        private lateinit var zoneId: String
        private lateinit var lstData: ListView
        private lateinit var btnAdd: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_list)

            zoneId = intent.getStringExtra("zoneId")!!

            lstData = findViewById(R.id.lstData)
            btnAdd = findViewById(R.id.btnAdd)

            btnAdd.setOnClickListener {
                val intent = Intent(this, BeehiveAddActivity::class.java)
                intent.putExtra("zoneId", zoneId)
                startActivity(intent)
            }

            lstData.setOnItemClickListener { _, _, pos, _ ->
                val hive = ctrl.getByZone(zoneId)[pos]
                val intent = Intent(this, BeehiveDetailActivity::class.java)
                intent.putExtra("beehiveId", hive.ID)
                startActivity(intent)
            }
        }

        override fun onResume() {
            super.onResume()
            val filtered = ctrl.getByZone(zoneId)
            lstData.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filtered)
        }

}