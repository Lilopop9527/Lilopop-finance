import { TOKEN,DELTOKEN,LOGIN,HOME } from './enum'
export const token = (token)=>({
    type:TOKEN,
    token
})
export const delToken = (token)=>({
    type:DELTOKEN,
    token
})
export const login = (msg)=>({
    type:LOGIN,
    msg
})
export const home = (msg)=>({
    type:HOME,
    msg
})
