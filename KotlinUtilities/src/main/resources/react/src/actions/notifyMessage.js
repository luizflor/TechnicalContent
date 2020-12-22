import { NOTIFY_MESSAGE_ACTIONS} from '../actions/notifyMessage_types';
export const notifyInfo = (messageHeader, messageBody) => async dispatch => {
    console.log(messageBody, messageHeader);
    dispatch({
        type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO,
        payload: {
            show: true,
            hide: true,
            messageHeader: messageHeader,
            messageBody: messageBody,
            messageType: 'INFO'
        }
    });
}

export const notifyError = (messageHeader, messageBody) => async dispatch => {
    dispatch({
        type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_ERROR,
        payload: {
            show: true,
            hide: false,
            messageHeader: messageHeader,
            messageBody: messageBody,
            messageType: 'ERROR'
        }
    });
}

export const notifyWarning = (messageHeader, messageBody) => async dispatch => {
    dispatch({
        type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_WARNING,
        payload: {
            show: true,
            hide: false,
            messageHeader: messageHeader,
            messageBody: messageBody,
            messageType: 'WARNING'
        }
    });
}

export const notifyClear = (messageHeader, messageBody) => async dispatch => {
    dispatch({
        type: NOTIFY_MESSAGE_ACTIONS.NOTIFY_CLEAR,
        payload: {
            show: false,
            hide: false,
            messageHeader: "",
            messageBody: "",
            messageType: 'INFO'
        }
    });
}