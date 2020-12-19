import React, { Component } from 'react';
import TopNav from '../TopNav';
import Footer from '../Footer';
import { connect } from 'react-redux';
import { NotifyMessage } from '../NotifyMessage/NotifyMessage';
import { notifyClear } from '../../actions';
import LeftSide from '../LeftSide/LeftSide';
import { Row, Col, } from 'react-bootstrap';

class Layout extends Component {

    onClose = () => {
        this.props.notifyClear();
    }

    render() {
        const notifyMessage = this.props.notifyMessage;
        // console.log(notifyMessage);

        return (
            <div>
                <TopNav />
                <NotifyMessage onClose={this.onClose} notifyMessage={notifyMessage} />

                <Row>
                    <Col md={1}>
                        <LeftSide />
                    </Col>
                    <Col md={11}>

                    </Col>
                </Row>

                <Footer />
            </div>
        )
    }
}


const mapStateToProps = state => ({
    notifyMessage: state.notifyMessageReducer.notifyMessage,
})

export default connect(mapStateToProps, { notifyClear })(Layout);


