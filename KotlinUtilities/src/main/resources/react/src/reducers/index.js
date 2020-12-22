import {combineReducers} from 'redux';
import notifyMessageReducer from './notifyMessageReducer';
export default combineReducers({
    notifyMessageReducer: notifyMessageReducer,
});
