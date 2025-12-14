package cr.ac.utn.beekeepersnotebook

import Adapter.BeehiveAdapter
import Controller.BeehiveController
import Controller.QueenController
import Entity.Beehive
import Entity.Queen
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class BeehiveActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var btnAddBeehive: Button
    private lateinit var adapter: BeehiveAdapter

    private lateinit var controller: BeehiveController
    private lateinit var queenController: QueenController

    private var personId: String = ""

    private val hives = mutableListOf<Beehive>()
    private var zoneId: String = ""
    private var zoneName: String = ""

    private var availableQueens: List<Queen> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_beehive)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ UID logueado
        personId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        if (personId.isBlank()) {
            Toast.makeText(this, "No hay usuario logueado", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        zoneId = intent.getStringExtra("ZONE_ID") ?: ""
        zoneName = intent.getStringExtra("ZONE_NAME") ?: ""
        title = "Colmenas - $zoneName"

        // ✅ Controllers con personId por constructor (Correcto)
        controller = BeehiveController(this, personId)
        queenController = QueenController(this, personId)

        recycler = findViewById(R.id.recyclerBeehives)
        btnAddBeehive = findViewById(R.id.btnAddBeehive)

        adapter = BeehiveAdapter(
            hives,
            onItemClick = { hive -> openDetails(hive) },
            onItemLongClick = { hive -> showHiveOptions(hive) }
        )

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        btnAddBeehive.setOnClickListener { addHiveDialog() }

        loadHives()
    }

    private fun loadHives() {
        // CORRECCIÓN: Cambiamos .getAll por .getByPerson
        controller.getByPerson { list ->
            hives.clear()
            // Filtramos por zona localmente
            hives.addAll(list.filter { it.ZoneID == zoneId })
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Colmenas cargadas: ${hives.size}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openDetails(hive: Beehive) {
        val intent = Intent(this, BeehiveDetailActivity::class.java)
        intent.putExtra("HIVE_ID", hive.ID)
        startActivity(intent)
    }

    private fun showHiveOptions(hive: Beehive) {
        val options = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Colmena: ${hive.Name}")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editHiveDialog(hive)
                    1 -> deleteHive(hive)
                }
            }
            .show()
    }

    private fun addHiveDialog() {
        val view = layoutInflater.inflate(R.layout.dialog_beehive, null)
        val txtName = view.findViewById<EditText>(R.id.txtBeehiveName)
        val txtBoxType = view.findViewById<EditText>(R.id.txtBeehiveBoxType)
        val spQueen = view.findViewById<Spinner>(R.id.spQueen)

        loadAvailableQueensForHive(null) { queens ->
            availableQueens = queens

            val labels = mutableListOf<String>()
            labels.add("Sin reina asignada")
            labels.addAll(queens.map { q -> "${q.Type} - ${q.getEntryDateAsString()}" })

            val spinAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                labels
            )
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spQueen.adapter = spinAdapter
        }

        AlertDialog.Builder(this)
            .setTitle("Nueva colmena")
            .setView(view)
            .setPositiveButton("Guardar") { _, _ ->
                val name = txtName.text.toString().trim()
                val boxType = txtBoxType.text.toString().trim()

                if (name.isEmpty()) {
                    Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val selectedPos = spQueen.selectedItemPosition
                val selectedQueen: Queen? =
                    if (selectedPos > 0 && availableQueens.isNotEmpty()) availableQueens[selectedPos - 1]
                    else null

                val hive = Beehive().apply {
                    Name = name
                    BoxType = boxType
                    ZoneID = zoneId
                    QueenID = selectedQueen?.ID ?: ""
                }

                // Controller ya tiene personId, solo pasamos hive
                controller.addBeehive(hive) { ok, msg ->
                    if (ok) {
                        // Marcar reina como asignada (si se eligió)
                        selectedQueen?.let { q ->
                            q.HiveID = hive.ID
                            queenController.updateQueen(q) { _, _ -> }
                        }

                        Toast.makeText(this, "Colmena guardada", Toast.LENGTH_SHORT).show()
                        loadHives()
                    } else {
                        Toast.makeText(this, msg ?: "Error al guardar colmena", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun editHiveDialog(hive: Beehive) {
        val view = layoutInflater.inflate(R.layout.dialog_beehive, null)
        val txtName = view.findViewById<EditText>(R.id.txtBeehiveName)
        val txtBoxType = view.findViewById<EditText>(R.id.txtBeehiveBoxType)
        val spQueen = view.findViewById<Spinner>(R.id.spQueen)

        txtName.setText(hive.Name)
        txtBoxType.setText(hive.BoxType)

        val oldQueenId = hive.QueenID

        loadAvailableQueensForHive(hive.ID) { queens ->
            availableQueens = queens

            val labels = mutableListOf<String>()
            labels.add("Sin reina asignada")
            labels.addAll(queens.map { q -> "${q.Type} - ${q.getEntryDateAsString()}" })

            val spinAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                labels
            )
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spQueen.adapter = spinAdapter

            if (!oldQueenId.isNullOrEmpty()) {
                val idx = queens.indexOfFirst { it.ID == oldQueenId }
                if (idx >= 0) spQueen.setSelection(idx + 1)
            }
        }

        AlertDialog.Builder(this)
            .setTitle("Editar colmena")
            .setView(view)
            .setPositiveButton("Guardar") { _, _ ->
                val name = txtName.text.toString().trim()
                val boxType = txtBoxType.text.toString().trim()

                if (name.isEmpty()) {
                    Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val selectedPos = spQueen.selectedItemPosition
                val selectedQueen: Queen? =
                    if (selectedPos > 0 && availableQueens.isNotEmpty()) availableQueens[selectedPos - 1]
                    else null

                hive.Name = name
                hive.BoxType = boxType
                hive.QueenID = selectedQueen?.ID ?: ""

                controller.updateBeehive(hive) { ok, msg ->
                    if (ok) {
                        // 1) Des-asignar reina anterior si cambió
                        if (!oldQueenId.isNullOrEmpty() && oldQueenId != selectedQueen?.ID) {
                            queenController.getById(oldQueenId) { qOld ->
                                qOld?.let {
                                    it.HiveID = ""
                                    queenController.updateQueen(it) { _, _ -> }
                                }
                            }
                        }

                        // 2) Asignar nueva reina
                        selectedQueen?.let { q ->
                            q.HiveID = hive.ID
                            queenController.updateQueen(q) { _, _ -> }
                        }

                        Toast.makeText(this, "Colmena actualizada", Toast.LENGTH_SHORT).show()
                        loadHives()
                    } else {
                        Toast.makeText(this, msg ?: "Error al actualizar colmena", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteHive(hive: Beehive) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar colmena")
            .setMessage("¿Desea eliminar la colmena ${hive.Name}?")
            .setPositiveButton("Eliminar") { _, _ ->
                controller.deleteBeehive(hive.ID) { ok, msg ->
                    if (ok) {
                        Toast.makeText(this, "Colmena eliminada", Toast.LENGTH_SHORT).show()
                        loadHives()
                    } else {
                        Toast.makeText(this, msg ?: "Error al eliminar colmena", Toast.LENGTH_LONG).show()
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun loadAvailableQueensForHive(
        currentHiveId: String?,
        onResult: (List<Queen>) -> Unit
    ) {
        // Traemos reinas del usuario y filtramos por zona y disponibilidad
        queenController.getByPerson { list ->
            val filtered = list.filter { q ->
                q.ZoneID == zoneId &&
                        (q.HiveID.isEmpty() || q.HiveID == (currentHiveId ?: ""))
            }
            onResult(filtered)
        }
    }
}