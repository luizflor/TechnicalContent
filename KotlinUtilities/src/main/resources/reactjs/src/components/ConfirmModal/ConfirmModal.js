import React, { Component } from 'react';
import { Button, Modal } from 'react-bootstrap';

export class ConfirmModal extends Component {
    render() {
        const { show, handleClose, confirmHeader, confirmBody } = this.props;
        return (
            <>
                <Modal animation={false}
                    show={show}
                    onHide={handleClose}
                    backdrop="static"
                    keyboard={false}
                >
                    <Modal.Header closeButton>
                        <Modal.Title>{confirmHeader}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {confirmBody}
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => handleClose(false)}>
                            Cancel
                        </Button>
                        <Button variant="success" onClick={() => handleClose(true)} >Confirm</Button>
                    </Modal.Footer>
                </Modal>
            </>
        )
    }
}

export default ConfirmModal
