package cr.ac.utn.beekeepersnotebook
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import Controller.PersonController



class SingInActivity : AppCompatActivity() {
    private lateinit var userCtrl: PersonController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        userCtrl = PersonController(this)

        val layout = findViewById<LinearLayout>(R.id.containerForm)

        val email = EditText(this).apply {
            hint = "Email"
        }

        val password = EditText(this).apply {
            hint = "Password"
            inputType = android.text.InputType.TYPE_CLASS_TEXT or
                    android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }

        val btnLogin = Button(this).apply {
            text = "Sign In"
            setOnClickListener {
                val result = userCtrl.auth(email.text.toString(), password.text.toString())
                if (result != null) {
                    Toast.makeText(this@SignInActivity, "Bienvenido", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@SignInActivity, ui.zone.ZoneListActivity::class.java))
                } else {
                    Toast.makeText(this@SignInActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val linkForgot = TextView(this).apply {
            text = "¿Olvidó su contraseña?"
            setOnClickListener {
                startActivity(Intent(this@SignInActivity, ForgotPasswordActivity::class.java))
            }
        }

        val linkSignUp = TextView(this).apply {
            text = "Crear cuenta"
            setOnClickListener {
                startActivity(Intent(this@SignInActivity, SignUpActivity::class.java))
            }
        }

        layout.addView(email)
        layout.addView(password)
        layout.addView(btnLogin)
        layout.addView(linkForgot)
        layout.addView(linkSignUp)
    }

}