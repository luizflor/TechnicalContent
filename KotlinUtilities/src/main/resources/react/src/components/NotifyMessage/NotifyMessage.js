import React, { Component } from 'react'
import { Toast } from 'react-bootstrap';
import './NotifyMessage.css';


export class NotifyMessage extends Component {
    onClose=() => {
         this.props.onClose();
    }

    render() {
        const {show ,hide, messageHeader, messageBody , messageType} = this.props.notifyMessage;

        //  console.log(show, hide, messageHeader, messageBody , messageType);

        let bg = "bg-success";
        switch(messageType) {
            case "ERROR":
                bg = "bg-danger";
                break;
            case "INFO":
                bg = 'bg-success';
                break;
            case "WARNING":
                bg='bg-warning';
                break;
            default:
                break;
        }

        return (
            <div aria-live="polite" aria-atomic="true">
            <div className="myToast">
                <Toast onClose={()=> this.onClose()} show={show} autohide={hide} animation={false}>
                    <Toast.Header className={"text-white " + bg}>
                        <strong className="mr-auto" >{messageHeader}</strong>
                    </Toast.Header>
                    <Toast.Body className="test-dark bg-light">
                        {messageBody}
                    </Toast.Body>

                </Toast>
            </div>
            </div>
        )
    }
}

export default NotifyMessage;

