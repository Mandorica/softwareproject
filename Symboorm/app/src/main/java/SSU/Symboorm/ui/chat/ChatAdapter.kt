package SSU.Symboorm.ui.chat

import SSU.Symboorm.model.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ChatAdapter() : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    class ChatViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    var messageList: List<Message> = ArrayList<Message>()
    var beforeDay: String? = null
    var firestore: FirebaseFirestore
    private val listenerRegistration: ListenerRegistration? = null

    init{
     firestore = FirebaseFirestore.getInstance()

    }

    fun startListening() {
        beforeDay = null

        val roomRef: CollectionReference =
            firestore.collection("rooms").document().collection("messages")
        // my chatting room information

    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ChatAdapter.ChatViewHolder {

        var view: View? = null
        view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)

        return ChatViewHolder(view as TextView)
    }
    class MessageViewHolderViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)


    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val message = messageList[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = messageList.size

}