import {
    TOKEN,
    DELTOKEN,
    LOGIN,
    HOME,
    USERINFO,
    ROLE,
    ROUTE,
    USERDETAIL,
    DEPARTMENT,
    STATIONS,
    ROLES,
    ROUTES,
    LEADERS,
    FIRSTENTRY,
    SECONDENTRY
} from './enum'
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
export const departments = (dept)=>({
    type:DEPARTMENT,
    dept
})
export const stations = (sta)=>({
    type:STATIONS,
    sta
})
export const roles = (role)=>({
    type:ROLES,
    role
})
export const routes = (routes)=>({
    type:ROUTES,
    routes
})
export const leaders = (leaders)=>({
    type:LEADERS,
    leaders
})
export const firstEntry = (firstEntry)=>({
    type:FIRSTENTRY,
    firstEntry
})
export const secondEntry = (secondEntry)=>({
    type:SECONDENTRY,
    secondEntry
})