package lahsivjar.spring.websocket.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private ChatHistoryDao chatHistoryDao;

    @Autowired
    private SendMessage sendMessage;

    /*
     * This MessageMapping annotated method will be handled by
     * SimpAnnotationMethodMessageHandler and after that the Message will be
     * forwarded to Broker channel to be forwarded to the client via WebSocket
     */
    @MessageMapping("/all")
    @SendTo("/topic/all")
    public Map<String, String> post(@Payload Map<String, String> message) {
        if (message.containsKey("timestamp")) {
            message.replace("timestamp", Long.toString(System.currentTimeMillis()));
        } else {
            message.put("timestamp", Long.toString(System.currentTimeMillis()));
            chatHistoryDao.save(message);
        }
        return message;
    }

    @RequestMapping("/history")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public List<Map<String, String>> getChatHistory() {
        return chatHistoryDao.get();
    }

    @RequestMapping("/resend")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public void resend() {
        List<Map<String, String>> history = chatHistoryDao.get();
        post(history.get(0));
//        return history;
    }
    @RequestMapping("/send1")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public void send1() {
        sendMessage.Send1();
    }
    @RequestMapping("/send2")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public void send2() {
        sendMessage.Send2();
    }
    @RequestMapping("/send3")
    @CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
    public void send3() {
        sendMessage.Send3();
    }
}
