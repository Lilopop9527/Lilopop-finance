import request from "../../../../../utils/request";
import {connect} from "react-redux";
import {useEffect, useState} from "react";

const ListByUserId:React.FC = (props)=>{
    const [userId,setUserId] = useState(props.user.id)

    useEffect(()=>{
        getMsg()
    },[])

    const getMsg = async ()=>{
        const msg = await request({
            url:'/finance/voucher/g',
            method:'get',
            params:{
                id:userId
            },
            headers:{
                Auth:props.token
            }
        }).then(function (res) {
            console.log(res.data)
        })
    }
    return (
        <div>
            {userId}
        </div>
    )
}
const mapStateToProps = (state)=>({
    token:state.token,
    user:state.user
})
export default connect(mapStateToProps)(ListByUserId)