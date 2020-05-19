package SSU.Symboorm.ui.board

import SSU.Symboorm.R
import SSU.Symboorm.ui.chat.ChatActivity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)
        val category = intent.getStringExtra("category")
        val title = intent.getStringExtra("title")
        val body = intent.getStringExtra("body")
        val uid = intent.getStringExtra("uid")


        val titleText = findViewById<TextView>(R.id.board_title)
        val bodyText = findViewById<TextView>(R.id.board_body)
        val profile = findViewById<TextView>(R.id.profile)


        if(category!= null) {
            titleText.text = title
            bodyText.text = body
        }

        back_text.setOnClickListener(){
            onBackPressed()
        }
        contactBtn.setOnClickListener(){
            val intent = Intent(this@BoardActivity, ChatActivity::class.java).apply {
                putExtra("toUid", uid)
            }
            startActivity(intent)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        finish()
    }

}