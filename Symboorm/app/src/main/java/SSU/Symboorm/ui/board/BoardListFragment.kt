package SSU.Symboorm.ui.board

import SSU.Symboorm.R
import SSU.Symboorm.common.FirestoreAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class BoardListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var firestoreAdapter: FirestoreAdapter<BoardAdapter.BoardViewHolder>

    override fun onStart() {
        super.onStart()
        firestoreAdapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        firestoreAdapter.stopListening()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_boardlist, container, false)

        firestoreAdapter = BoardAdapter(FirebaseFirestore.getInstance().collection("board").orderBy(("usernm")))

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = firestoreAdapter

        recyclerView = root.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
        return root
    }
}
