package cr.ac.utn.beekeepersnotebook.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import Controller.ZoneController
import Entity.Zone



class ZoneListActivity : AppCompatActivity() {

    private val ctrl = ZoneController()
    private lateinit var lstData: ListView
    private lateinit var btnAdd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        lstData = findViewById(R.id.lstData)
        btnAdd = findViewById(R.id.btnAdd)

        btnAdd.setOnClickListener {
            startActivity(Intent(this, ZoneAddActivity::class.java))
        }

        lstData.setOnItemClickListener { _, _, pos, _ ->
            val zone = ctrl.getAll()[pos]
            val intent = Intent(this, ui.beehive.BeehiveListActivity::class.java)
            intent.putExtra("zoneId", zone.ID)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()
        lstData.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ctrl.getAll())
    }
}