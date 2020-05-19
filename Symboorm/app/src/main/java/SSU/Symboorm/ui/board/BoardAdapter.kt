package SSU.Symboorm.ui.board

import SSU.Symboorm.R
import SSU.Symboorm.common.FirestoreAdapter
import SSU.Symboorm.model.PostModel
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.Format
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*


class BoardAdapter(query: Query) : FirestoreAdapter<BoardAdapter.BoardViewHolder>(query){
    class BoardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var boardCategory: TextView = view.findViewById(R.id.board_category)
        var userName: TextView = view.findViewById(R.id.user_name)
        var userBody: TextView = view.findViewById(R.id.user_msg)
        var timestamp: TextView = view.findViewById(R.id.item_time)
        var price: TextView = view.findViewById(R.id.item_price)
    }


    private var storageReference: StorageReference? = null
    init{
        storageReference = FirebaseStorage.getInstance().reference
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BoardViewHolder {
        return BoardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_board, parent, false))
    }

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        val documentSnapshot : DocumentSnapshot = getSnapshot(position)
        val post = documentSnapshot.toObject(PostModel::class.java)

        if (post != null) {
            holder.boardCategory.text = post.category
            holder.userName.text = post.title
            holder.userBody.text = post.body
            holder.timestamp.text = SimpleDateFormat("MM월 dd일 HH시 mm분까지", Locale.KOREA).format(post.timestamp)
            holder.price.text = String.format("%d원", post.price)



            holder.itemView.setOnClickListener(){
                val intent = Intent(it.context, BoardActivity::class.java).apply{
                    putExtra("category", post.category)
                    putExtra("body",post.body)
                    putExtra("title",post.title)
                    putExtra("uid",post.uid)
                }
                it.context.startActivity(intent)
            }
        }



    }


}


