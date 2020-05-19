package SSU.Symboorm.ui.board

import SSU.Symboorm.R
import SSU.Symboorm.common.Util
import SSU.Symboorm.model.PostModel
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import java.util.*


class PostActivity : AppCompatActivity(){
    private var editTitle : EditText? = null
    private var editPrice : EditText? = null
    private var editTime : EditText? = null
    private var editContent : EditText? = null
    private var id : String? = null
    private var uid : String? = null
    private var time : Date? = null
    private var category : String? = null
    private var myCalendar : Calendar = Calendar.getInstance()
    private var categoryArray = arrayOf("도와주세요", "힘써주세요", "갖다주세요", "받아주세요", "지켜주세요")

    var myDatePicker =
        OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = month
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
            updateLabel()
        }
    private fun updateLabel() {
        val myFormat = "yyyy/MM/dd" // 출력형식   2018/11/28
        val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
        val et_date = findViewById<View>(R.id.edit_date) as EditText
        et_date.setText(sdf.format(myCalendar.time))
        time = myCalendar.time
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        val spinner: Spinner = findViewById(R.id.spinner)

        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categoryArray)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                category = categoryArray[0]
            }


            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                category = categoryArray[position]
            }
        }

        editTitle = findViewById(R.id.edit_title)
        editPrice = findViewById(R.id.edit_price)
        editContent = findViewById(R.id.edit_content)
        uid = FirebaseAuth.getInstance().uid
        id = intent.getStringExtra("ID")
        val registbtn = findViewById<Button>(R.id.registerBtn)
        registbtn.setOnClickListener() {
            val newBoard = FirebaseFirestore.getInstance().collection("board").document()
            createBoard(newBoard)
        }

        val et_Date = findViewById<View>(R.id.edit_date) as EditText
        et_Date.setOnClickListener {
            DatePickerDialog(
                this@PostActivity,
                myDatePicker,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()
        }

        val et_time = findViewById<View>(R.id.edit_time) as EditText
        et_time.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(this@PostActivity,
                OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    var selectedHour = selectedHour
                    var state = "AM"
                    // 선택한 시간이 12를 넘을경우 "PM"으로 변경 및 -12시간하여 출력 (ex : PM 6시 30분)
                    if (selectedHour > 12) {
                        selectedHour -= 12
                        state = "PM"
                    }
                    // EditText에 출력할 형식 지정
                    et_time.setText(state + " " + selectedHour + "시 " + selectedMinute + "분")
                    time?.hours = selectedHour
                    time?.minutes = selectedMinute
                }, hour, minute, false
            ) // true의 경우 24시간 형식의 TimePicker 출현
            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }


        back_text.setOnClickListener(){
            onBackPressed()
        }

    }

    private fun createBoard(board: DocumentReference) {
        val title = editTitle?.text.toString()
        val body = editContent?.text.toString()
        val price = Integer.parseInt(editPrice?.text.toString())
        val postModel = PostModel()
        postModel.title = title
        postModel.category = category
        postModel.body = body
        postModel.uid = uid
        postModel.timestamp = time
        postModel.price = price
        board.set(postModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onBackPressed()
            }else {
                Util.showMessage(
                    applicationContext,
                    task.exception!!.message
                )
            }
        }
    }



}