import { NOTIFY_MESSAGE_ACTIONS} from '../actions/notifyMessage_types';

const initialState = {
    notifyMessage : {
        show: false,
        hide: false,
        messageHeader: '',
        messageBody: '',
        messageType: ''
    }
}
const reducer = (state = initialState, action) => {
    switch (action.type) {
        case NOTIFY_MESSAGE_ACTIONS.NOTIFY_INFO:
        case NOTIFY_MESSAGE_ACTIONS.NOTIFY_WARNING:
        case NOTIFY_MESSAGE_ACTIONS.NOTIFY_ERROR:
        case NOTIFY_MESSAGE_ACTIONS.NOTIFY_CLEAR:
            return {
                ...state,
                notifyMessage: { ...action.payload}
            }
            break;
        default:
            return state;
    }
}

export default reducer;