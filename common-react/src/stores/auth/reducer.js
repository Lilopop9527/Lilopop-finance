import { TOKEN,DELTOKEN } from './enum'


const tokenData = {
    token: "empty"
}

const reducer = (state=tokenData,action)=>{
    switch (action.type){
        case TOKEN:
            return {...state,token:action.token};
        case DELTOKEN:
            return {...state,token: "empty"};
        default :
            return state;
    }
}

export default reducer;