import { TOKEN,DELTOKEN } from './enum'
export const token = (token)=>({
    type:TOKEN,
    token
})
export const delToken = (token)=>({
    type:DELTOKEN,
    token
})

