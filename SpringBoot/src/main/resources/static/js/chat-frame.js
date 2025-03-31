function toggleChat(){
    var chatFrame = document.getElementById("chat-admin");
    if(chatFrame.style.display === "none" || chatFrame.style.display === ""){
        chatFrame.style.display = "block";
    }
    else{
        chatFrame.style.display = "none"
    }
}



stompClient.connect({}, function (frame) {
    console.log("WebSocket connected as: " + username);

    stompClient.subscribe('/user/' + username + '/queue/messages', function (message) {
        console.log("User subscribed to /user/" + username + "/queue/messages");
        var msg = JSON.parse(message.body);
        console.log("User received message:", msg);
        if (msg.senderUsername !== username) {
            showMessageInChannel(msg.channel, msg);
        } else {
            console.log("Ignoring message from self:", msg);
        }
    });

    fetchChatHistory();
}, function (error) {
    console.error("WebSocket connection error:", error);
});

function sendMessageToAdmin(event) {
    event.preventDefault();
    var messageContent = document.getElementById('messageInput-admin').value;
    if (messageContent.trim()) {
        var message = {
            senderUsername: username,
            recipient: "admin",
            content: messageContent,
            channel: 'chat-' + username
        };
        console.log("User sending message:", message);

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));

        showMessageInChannel('chat-' + username, message);

        document.getElementById('messageInput-admin').value = '';
    }
}

function showMessageInChannel(channel, message) {
    var messagesDiv = document.getElementById('messages-admin');
    if (messagesDiv) {
        var messageClass = (message.senderUsername === username) ? 'sent' : 'received';
        console.log("Showing message in channel:", channel, "with class:", messageClass, "content:", message.content);
        messagesDiv.innerHTML += `
                    <div class="message ${messageClass}">
                        <div class="message-bubble">${message.content}</div>
                    </div>`;
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }
}

function fetchChatHistory() {
    fetch('http://localhost:8081/api/chat/history/chat-' + username)
        .then(response => response.json())
        .then(history => {
            console.log("Fetched chat history:", history);
            var messagesDiv = document.getElementById('messages-admin');
            messagesDiv.innerHTML = '';
            history.forEach(msg => showMessageInChannel('chat-' + username, msg));
        })
        .catch(error => console.error("Error fetching chat history:", error));
}