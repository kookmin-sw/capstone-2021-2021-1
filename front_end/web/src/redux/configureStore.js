import { combineReducers, createStore, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import {routerMiddleware, connectRouter} from "connected-react-router";
import reducer from "./reducers/index";
import { createBrowserHistory } from 'history';
import  { composeWithDevTools } from "redux-devtools-extension";

const env = process.env.NODE_ENV;

const history = createBrowserHistory()

const middlewares = [thunk, routerMiddleware(history)];

const reducers = combineReducers({
  state: reducer,
  router: connectRouter(history)
});

let store;

if (env === "development") {
  store = () =>
    createStore(reducers,
    composeWithDevTools(applyMiddleware(...middlewares)));
} else {
  store = () => createStore(reducers, applyMiddleware(...middlewares));
}

export {history};
export default store();
