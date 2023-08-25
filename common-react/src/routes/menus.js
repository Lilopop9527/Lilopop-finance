import {lazy} from "react";
import UserCenter from "../pages/home/pages/userCenter/userCenter";
import {Navigate} from 'react-router-dom'

const Home =lazy(()=>import('../pages/home/pages/home/home'))
const menus =[
    {
        path: '/home',
        element: <Home/>
    },
    {
        path: '/',
        element: <Navigate to='/home'/>
    },
    {
        path: '/userCenter',
        element: <UserCenter/>
    }
]

export default menus