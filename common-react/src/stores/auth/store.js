import {createStore,applyMiddleware} from "redux";
import Reducer from "./reducer";
import thunk from "redux-thunk";

const storeEnhancer = applyMiddleware(thunk)

const store = createStore(Reducer,storeEnhancer)

export default store;