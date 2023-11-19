package com.zjq.jetpackcompose2023.cleancodehacks

fun messageOnlineUsers(usersInRoom: List<User>, message: String) {
    val (onlineUsers, offlineUsers) = usersInRoom.partition {
        it.isOnline
    }
    onlineUsers.forEach { user ->
        user.sendMessage(message)
    }

    offlineUsers.forEach { user ->
        user.sendMessage(message)
    }
}

private fun User.sendMessage(message: String) {
    //
}