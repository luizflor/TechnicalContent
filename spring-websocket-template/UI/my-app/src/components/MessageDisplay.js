import React from "react";


class MessageDisplay extends React.Component {
    render() {
        const {messages} = this.props;
        console.log(this.props);
        if(messages) {
            return (
                <>
                    <h1>Message Display {this.props.message}</h1>
                </>
            )
        } else {
            return null;
        }
    }
}

export default MessageDisplay