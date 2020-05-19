package SSU.Symboorm

import SSU.Symboorm.common.Util
import SSU.Symboorm.model.UserModel
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserInfoActivity : AppCompatActivity() {
    private var nickname: EditText? = null
    private var statemsg: EditText? = null
    private var sharedPreferences: SharedPreferences? = null
    private var uid: String? = null
    private var id: String? = null
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_userinfo)
/*        val savebtn = findViewById<Button>(R.id.saveBtn)
        nickname = findViewById(R.id.user_name)
        statemsg = findViewById(R.id.user_msg)
        sharedPreferences = getSharedPreferences("symboorm", Activity.MODE_PRIVATE)

        uid = intent.getStringExtra("UID")
        id = intent.getStringExtra("ID")
        savebtn.setOnClickListener(saveClick)*/
    }

    private var saveClick = View.OnClickListener {
        if (!validateForm()) return@OnClickListener

        val usernm = nickname!!.text.toString()
        sharedPreferences!!.edit().putString("user_id", id).apply()
        val userModel = UserModel()
        userModel.uid = uid
        userModel.uid = id
        userModel.usernm = usernm
        userModel.usermsg = statemsg?.toString()

        FirebaseFirestore.getInstance().collection("users").document(uid!!)
            .set(userModel)
            .addOnSuccessListener {
                val intent =
                    Intent(this@UserInfoActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                Log.d(
                    java.lang.String.valueOf(R.string.app_name),
                    "DocumentSnapshot added with ID: $uid"
                )
            }
    }

    private fun validateForm(): Boolean {
        var valid = true
        val usernm = nickname!!.text.toString()
        if (TextUtils.isEmpty(usernm)) {
            nickname!!.error = "Required."
            valid = false
        } else {
            nickname!!.error = null
        }
        return valid
    }
}