import { TOKEN,DELTOKEN,LOGIN,HOME,USERINFO,ROLE,ROUTE,USERDETAIL } from './enum'
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
export const userInfo = (info)=>({
    type:USERINFO,
    info
})
export const role = (role)=>({
    type:ROLE,
    role
})
export const route = (route)=>({
    type:ROUTE,
    route
})
export const detail = (detail)=>({
    type:USERDETAIL,
    detail
})
