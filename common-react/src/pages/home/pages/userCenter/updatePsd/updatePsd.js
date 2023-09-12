import React, {Component} from 'react';
import {Input,Button,message} from "antd";
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import md5 from "js-md5";
import '../userCenter.css'
React.Component.prototype.$md5 = md5
class UpdatePsd extends Component {
    constructor(props) {
        super(props);
        this.state={

        }
    }

    setNowPsd(e){
        const p = md5(e.target.value)
        this.setState({
            password1:p
        })
    }
    setChangedPsd(e){
        const p = md5(e.target.value)
        this.setState({
            password2:p
        })
    }
    setPsd(e){
        const p = md5(e.target.value)
        this.setState({
            password3:p
        })
    }
    async updatePsd(){
        const local = this
        if(this.state.password1.length<6){
            message['warning']("当前密码长度不足")
        }else if(this.state.password2.length<6 || this.state.password3.length<6){
            message['warning']("修改后的密码长度不足")
        }else if(this.state.password2 !== this.state.password3){
            message['warning']("两个修改后的密码不一致")
        }else if(this.state.password2 === this.state.password1 && this.state.password1 === this.state.password3){
            message['success']("修改完成")
        }else{
            const msg = await request({
                url:'/auth/user/upPsd',
                method:'put',
                params:{
                    password1:local.state.password1,
                    password2:local.state.password2,
                    id:local.props.user.id
                },
                headers:{
                    Auth:local.props.token
                }
            }).then(function (res) {
                if (res.data.code !== 200){
                    message['error'](res.data.message)
                }else{
                    message['success']('修改成功')
                    local.props.close()
                }
            }).catch(function (error) {
                message['error']('修改失败，请检查密码')
            })
        }
    }
    render() {
        return (
            <div className='msg'>
                <Input.Password addonBefore='当前密码' onChange={e=>this.setNowPsd(e)} style={{width:'70%', marginBottom:'30px'}} placeholder='请输入当前密码'/>
                <Input.Password addonBefore='第一次输入' onChange={e=>this.setChangedPsd(e)} style={{width:'70%', marginBottom:'30px'}} placeholder='请输入修改后的密码'/>
                <Input.Password addonBefore='第二次输入' onChange={e=>this.setPsd(e)} style={{width:'70%', marginBottom:'30px'}} placeholder='重复输入修改后的密码'/>
                <br/>
                <Button type='primary' onClick={()=>this.updatePsd()}>提交修改</Button>
                <Button onClick={()=>this.props.close()}>取消</Button>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    user:state.user
})
export default connect(mapStateToProps)(UpdatePsd);