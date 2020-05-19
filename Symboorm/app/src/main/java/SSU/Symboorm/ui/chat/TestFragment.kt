package SSU.Symboorm.ui.chat

import SSU.Symboorm.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TestFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var firestore: FirebaseFirestore
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_chat, container, false)
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)



        firestore = FirebaseFirestore.getInstance()

        recyclerView = root.findViewById(R.id.recyclerView)
        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ChatAdapter()

        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
        return root
    }

}




