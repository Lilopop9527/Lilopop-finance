import React, {Component} from 'react';
import {connect} from "react-redux";
import {Button, Image, message} from "antd";
import './header.css'
import request from "../../../utils/request";
import {delToken, login} from "../../../stores/auth/action";
class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    async logout(){
        const {dispatch} = this.props
        const local = this
        const msg = await request({
            url:'/auth/auth/logout',
            method:'get',
            pragma:{
                id:local.props.user.id
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (response) {
            message['success']('退出登录')
            dispatch(delToken('empty'))
            dispatch(login(1))
            }
        ).catch(function (err) {
            message['error']('网络错误，请稍后再试')
        })

    }
    render() {
        const url = 'http://127.0.0.1:9000/userimg/'+this.props.user.img
        return (
            <div>
                <div className='header-user'>
                    {this.props.user.username}|{this.props.role[0].roleName}|<Image src={url} style={{width:40}}/>
                    <br/>
                    <Button type='link' onClick={()=>this.logout()}>退出登录</Button>
                </div>
            </div>
        );
    }
}
const mapStateToProps=(state)=>({
    user:state.user,
    role:state.role,
    token:state.token
})
export default connect(mapStateToProps)(Header);