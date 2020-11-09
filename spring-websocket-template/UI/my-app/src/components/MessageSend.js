import React from "react";
import axios from 'axios';


class MessageSend extends React.Component {

    onSend = (n) => {

        let url ='';
        switch (n) {
            case 1:
                url = 'http://localhost:8080/send1';
                break;
            case 2:
                url = 'http://localhost:8080/send2';
                break;
            case 3:
                url = 'http://localhost:8080/send3';
                break;
            default:
                url = 'http://localhost:8080/send1';
                break;
        }
        axios.get(url).then().catch((e)=>console.error(e));
        console.log(n,url);
    }

    render() {
        return (
            <>
                <button onClick={() => this.onSend(1)}>Send 1</button>
                <button onClick={() => this.onSend(2)}>Send 2</button>
                <button onClick={() => this.onSend(3)}>Send 3</button>
            </>
        )
    }
}

export default MessageSend