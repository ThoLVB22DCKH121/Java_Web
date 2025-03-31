var users = /*[[${users}]]*/ [];
console.log("Loaded users:", users);

var socket = new SockJS('http://localhost:8081/ws');
var stompClient = Stomp.over(socket);
var adminUsername = "admin";

stompClient.connect({}, function (frame) {
    console.log("Connected as: " + adminUsername);

    stompClient.subscribe('/user/' + adminUsername + '/queue/messages', function (message) {
        var msg = JSON.parse(message.body);

        showMessageInChannel(msg.channel, msg);
    });
});

function sendMessageToUser(username, event) {
    event.preventDefault();
    var messageContent = document.getElementById('messageInput-' + username).value;
    if (messageContent.trim()) {
        var message = {
            senderUsername: adminUsername,
            recipient: username,
            content: messageContent,
            channel: 'chat-' + username
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(message));

        showMessageInChannel('chat-' + username, message);

        document.getElementById('messageInput-' + username).value = '';
    }
}


function showMessageInChannel(channel, message) {
    var username = channel.split('-')[1];
    var messagesDiv = document.getElementById('messages-' + username);
    if (messagesDiv) {
        var messageClass = (message.senderUsername === adminUsername) ? 'sent' : 'received';
        messagesDiv.innerHTML += `
                    <div class="message ${messageClass}">
                        <div class="message-bubble">${message.content}</div>
                    </div>`;
        messagesDiv.scrollTop = messagesDiv.scrollHeight;
    }
}

function openChat(event, element) {
    event.preventDefault();
    var username = element.getAttribute("data-username");
    document.querySelectorAll(".chat-frame").forEach(chat => chat.style.display = "none");
    var chatFrame = document.getElementById("chat-" + username);
    chatFrame.style.display = "block";
    fetchChatHistory(username);
}


function closeChat(username) {
    document.getElementById("chat-" + username).style.display = "none";
}

function fetchChatHistory(username) {
    fetch('http://localhost:8081/api/chat/history/chat-' + username)
        .then(response => response.json())
        .then(history => {
            var messagesDiv = document.getElementById('messages-' + username);
            messagesDiv.innerHTML = '';
            history.forEach(msg => showMessageInChannel('chat-' + username, msg));
        })
        .catch(error => console.error("Error fetching chat history:", error));
}