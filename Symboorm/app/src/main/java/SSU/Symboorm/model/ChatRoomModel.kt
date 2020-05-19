package SSU.Symboorm.model

class ChatRoomModel {
    private var roomID: String? = null
    private var title: String? = null
    private var photo: String? = null
    private var lastMsg: String? = null
    private var lastDatetime: String? = null
    private var userCount: Int? = null
    private var unreadCount: Int? = null

    fun getRoomID(): String? {
        return roomID
    }

    fun setRoomID(roomID: String?) {
        this.roomID = roomID
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun getPhoto(): String? {
        return photo
    }

    fun setPhoto(photo: String?) {
        this.photo = photo
    }

    fun getLastMsg(): String? {
        return lastMsg
    }

    fun setLastMsg(lastMsg: String?) {
        this.lastMsg = lastMsg
    }

    fun getLastDatetime(): String? {
        return lastDatetime
    }

    fun setLastDatetime(lastDatetime: String?) {
        this.lastDatetime = lastDatetime
    }

    fun getUserCount(): Int? {
        return userCount
    }

    fun setUserCount(userCount: Int?) {
        this.userCount = userCount
    }

    fun getUnreadCount(): Int? {
        return unreadCount
    }

    fun setUnreadCount(unreadCount: Int?) {
        this.unreadCount = unreadCount
    }
}