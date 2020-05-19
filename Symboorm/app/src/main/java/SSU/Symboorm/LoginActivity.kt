package SSU.Symboorm

import SSU.Symboorm.common.Util
import SSU.Symboorm.model.UserModel
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class LoginActivity : AppCompatActivity() {
    private var userId: EditText? = null
    private var userPw: EditText? = null
    private var sharedPreferences: SharedPreferences? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        userId = findViewById(R.id.user_id)
        userPw = findViewById(R.id.user_pw)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val signupText = findViewById<TextView>(R.id.signup)

        signupText.setOnClickListener(signupClick)
        loginBtn.setOnClickListener(loginClick)

        sharedPreferences = getSharedPreferences("symboorm", Activity.MODE_PRIVATE)
    }

    private var loginClick = View.OnClickListener {

        val email : String = addEmailFormat(userId!!.text.toString())
        if (!validateForm()) return@OnClickListener
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email,
            userPw!!.text.toString()
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sharedPreferences!!.edit().putString("user_id", userId!!.text.toString())
                    .apply()
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Util.showMessage(applicationContext, task.exception!!.message)
            }
        }
    }
    private var signupClick = View.OnClickListener {
        if (!validateForm()) return@OnClickListener

        val id = userId!!.text.toString()
        val email : String = addEmailFormat(id)
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, userPw!!.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sharedPreferences!!.edit().putString("user_id", id).apply()
                    val uid = FirebaseAuth.getInstance().uid
                    val userModel = UserModel()
                    userModel.uid=uid
                    userModel.userid=id
                    userModel.usernm=""
                    userModel.usermsg=("")
                    val db = FirebaseFirestore.getInstance()
                    db.collection("users").document(uid!!)
                        .set(userModel)
                        .addOnSuccessListener {
                            val intent =
                                Intent(this@LoginActivity, MainActivity::class.java).apply{
                                    putExtra("UID",uid)
                                    putExtra("ID",id)
                                }
                            startActivity(intent)
                            finish()
                            Log.d(
                                java.lang.String.valueOf(R.string.app_name),
                                "DocumentSnapshot added with ID: $uid"
                            )
                        }
                } else {
                    Util.showMessage(
                        applicationContext,
                        task.exception!!.message
                    )
                }
            }
    }



    private fun addEmailFormat(email: String): String {
        return "$email@soongsil.ac.kr"
    }

    private fun validateForm(): Boolean {
        var valid = true
        val email = userId!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            userId!!.error = "Required."
            valid = false
        } else {
            userId!!.error = null
        }
        val password = userPw!!.text.toString()
        if (TextUtils.isEmpty(password)) {
            userPw!!.error = "Required."
            valid = false
        } else {
            userPw!!.error = null
        }
        return valid
    }
}


