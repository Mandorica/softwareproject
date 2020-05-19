package SSU.Symboorm.model

import java.util.*

class Message {
    var uid: String? = null
    var msg: String? = null
    var msgtype: String? = null
    var timestamp: Date? = null
    var readUsers: List<String> = ArrayList()
    var filename: String? = null
    var filesize: String? = null
}