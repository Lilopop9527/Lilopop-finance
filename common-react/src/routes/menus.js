import {lazy} from "react";
import UserCenter from "../pages/home/pages/userCenter/userCenter";
import {Navigate} from 'react-router-dom'
import UserList from "../pages/home/pages/userList/userList";
import AuthConfig from "../pages/home/pages/authConfig/authConfig";
import RoleConfig from "../pages/home/pages/authConfig/roleConfig/roleConfig";
import DepartmentConfig from "../pages/home/pages/authConfig/departmentConfig/departmentConfig";
import StationConfig from "../pages/home/pages/authConfig/stationConfig/stationConfig";

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
    },
    {
        path:'/userList',
        element: <UserList/>
    },
    {
        path: '/authConfig',
        element: <AuthConfig/>,
        children:[
            {
                path:'roleConfig',
                element:<RoleConfig/>
            },
            {
                path: 'deptConfig',
                element: <DepartmentConfig/>
            },
            {
                path: 'stationConfig',
                element: <StationConfig/>
            }
        ]
    }
]

export default menus