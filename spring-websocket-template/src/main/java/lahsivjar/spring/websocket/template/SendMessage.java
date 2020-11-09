package lahsivjar.spring.websocket.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class SendMessage {
    @Autowired
    SimpMessagingTemplate template;

    public void Send1() {
        template.convertAndSend("/topic/1","msg 1");
    }
    public void Send2() {
        template.convertAndSend("/topic/2","msg 2");
    }
    public void Send3() {
        template.convertAndSend("/topic/3","msg 3");
    }
}

