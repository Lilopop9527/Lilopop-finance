import { TOKEN,DELTOKEN,LOGIN,HOME } from './enum'
import Login from "../../pages/login/login";
import Home from "../../pages/home/home";

const data = {
    token: "empty",
    element: <Login/>
}

const reducer = (state=data,action)=>{
    switch (action.type){
        case TOKEN:
            return {...state,token:action.token};
        case DELTOKEN:
            return {...state,token: "empty"};
        case LOGIN:
            return {...state,element: <Login/>}
        case HOME:
            return {...state,element: <Home/>}
        default :
            return state;
    }
}

export default reducer;