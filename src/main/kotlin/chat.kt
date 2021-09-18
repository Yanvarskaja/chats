import java.lang.RuntimeException

class Chat (val id : Int) {
    val users = mutableListOf<Int>()
    val messages = mutableListOf<Message>()
}

class Message (val senderId : Int, val messageId : Int, var text : String, isUnread : Boolean)

class ChatServise {
    val chats = mutableListOf<Chat>()

    fun sendMessage (chatId: Int, message: Message) {
        chats.find { it.id == chatId }?.messages?.add(message)
    }

    fun deleteChat(chatId: Int) {
        val chat = chats[chatId]
        chats.removeAt(chatId)
    }

    fun deleteMessage (chatId: Int, messageId: Int, message: Message, lastMessageId: Int) {
        val chat = chats[chatId]
        chats.find { it.id == messageId && messageId > lastMessageId}?.messages?.remove(message) ?: chats.removeAt(chatId)
    }

    fun editMessage (chatId: Int, messageId: Int, message: Message) {
        val chat = chats[chatId]
        val chatMessage = chats.find {it.id == messageId }?.messages?.get(messageId)?.text
    }

    fun getUnreadChats (chatId: Int, isUnread: Boolean, count: Int) {
        val chat = chats[chatId]
        val unreadChats = chat.messages.filter { isUnread -> true}.take(count)
    }

    fun getChatMessages (senderId: Int, chatId: Int, lastMessageId : Int, count : Int) {
        val chat = chats[chatId]
        chat.messages.sortWith(compareByDescending { it.messageId })
        val filteredMessages = chat.messages.filter { it.senderId == senderId && it.messageId > lastMessageId}.take(count)

    }

    fun getChats (senderId: Int) {
        chats.filter { it.id == senderId } ?: ChatNotFoundException("Сообщений нет")
    }

    class ChatNotFoundException(message: String) : RuntimeException(message)



}