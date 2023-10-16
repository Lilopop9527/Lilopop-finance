import React, {Component} from 'react';
import {connect} from "react-redux";
import { LoadingOutlined, PlusOutlined,UploadOutlined } from '@ant-design/icons';
import {Button, Form, Input, Radio, DatePicker, message, Modal, Space, Avatar, Upload, Image} from "antd";
import {delToken, login,userInfo} from "../../../../stores/auth/action";
import request from "../../../../utils/request";
import UpdatePsd from "./updatePsd/updatePsd"
import UpdateUserDetail from "./updateUserDetail/updateUserDetail";
import './userCenter.css'
import {minioprev} from "../../../../enum/enums";
class UserCenter extends Component {
    constructor(props) {
        super(props);
        this.state = {
            showMod: false,
            loading:false,
            showDetail:false
        }
    }


    componentDidMount() {
        const p = this.props
        const token = p.token
        this.setState({
            url:minioprev+p.user.img
        })
        const {dispatch} = p
        if(token === 'empty'||!token||token === ''){
            dispatch(delToken('empty'))
            dispatch(login(1))
        }
    }
    saveUsername(e){
        this.setState({
            username:e.target.value
        })
    }
    saveEmail(e){
        this.setState({
            email:e.target.value
        })
    }
    savePhone(e){
        this.setState({
            phone:e.target.value
        })
    }
    async updateUsername(){
        const local = this
        const user = this.props.user
        if (this.state.username === null || this.state.username.length < 8 || this.state.username.length > 16){
            message['warning']('账号长度不足，请检查输入')
        }else if (this.state.username === this.props.user.username){
            message['warning']('账号未修改')
        }else{
            const msg = await request({
                url:'/auth/user/upU',
                method:'put',
                params:{
                    username:local.state.username,
                    id:local.props.user.id
                },
                headers:{
                    Auth:local.props.token
                }
            }).then(function (res) {
                if (res.data.code === 200){
                    user.username = local.state.username
                    local.saveUser(user)
                    message['success']('修改成功')
                }else{
                    message['error'](res.data.message)
                }
            }).catch(function (error) {
                message['error']("网络错误，请稍后再试")
            }
            )
        }
    }
    async updateEmail(){
        const local = this
        const user = this.props.user
        const pattern = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/
        if(this.state.email==null&&!pattern.test(this.state.email)){
            message['error']('邮箱格式不正确，请检查输入')
        }else if(this.state.email === local.props.email){
            message['warning']('邮箱未修改')
        }else{
            const msg = await request({
                url:'/auth/user/upE',
                method:'put',
                params:{
                    email:local.state.email,
                    id:local.props.user.id
                },
                headers:{
                    Auth:local.props.token
                }
            }).then(function (res) {
                if (res.data.code === 200){
                    user.email = local.state.email
                    local.saveUser(user)
                    message['success']('修改成功')
                }else{
                    message['error'](res.data.message)
                }
            }).catch(function (error) {
                    message['error']("网络错误，请稍后再试")
                }
            )
        }
    }

    async updatePhone(){
        const local = this
        const user = this.props.user
        const pattern = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/
        if(this.state.phone==null&&!pattern.test(this.state.phone)){
            message['error']('手机号格式不正确，请检查输入')
        }else if(this.state.phone === local.props.phone){
            message['warning']('手机号未修改')
        }else{
            const msg = await request({
                url:'/auth/user/upP',
                method:'put',
                params:{
                    phone:local.state.phone,
                    id:local.props.user.id
                },
                headers:{
                    Auth:local.props.token
                }
            }).then(function (res) {
                if (res.data.code === 200){
                    user.phone = local.state.phone
                    local.saveUser(user)
                    message['success']('修改成功')
                }else{
                    message['error'](res.data.message)
                }
            }).catch(function (error) {
                    message['error']("网络错误，请稍后再试")
                }
            )
        }
    }

    updatePsd(){
        this.setState({
            showMod:true
        })
    }
    saveUser(user){
        const {dispatch} = this.props
        dispatch(userInfo(user))
    }
    closeMod(){
        this.setState({
            showMod:false
        })
    }
    handleChange = async ({file})=>{
        const result = file.response
        if (file.status === 'done'){
            const user = this.props.user
            const u = {
                id:user.id,
                username:user.username,
                phone:user.phone,
                email:user.email,
                img:result.data
            }
            this.saveUser(u)
            this.setState({
                loading:false,
                url:minioprev+result.data
            })
        }else if (file.status === 'uploading'){
            this.setState({
                loading:true
            })
        }else{
            //message['error']("上传图片失败")
        }
    }

    showUpdateDetail(){
        this.setState({
            showDetail:true
        })
    }
    closeUpdateDetail(){
        this.setState({
            showDetail:false
        })
    }
    render() {
        const local = this
        const path = "http://127.0.0.1:8080/auth/user/img/"+local.props.user.id
        return (
            <div className='home-wrap'>
                <div className='msg'>
                    <Form
                        labelCol={{span:4}}
                        wrapperCol={{span:14}}
                        style={{maxWidth:600}}
                        labelWrap='true'
                    >
                        <Form.Item label="账号">
                            <Input onChange={e=>this.saveUsername(e)} defaultValue={local.props.user.username}/>
                            <Button onClick={()=>this.updateUsername()}>修改账号</Button>
                        </Form.Item>
                        <Form.Item label="邮箱">
                            <Input onChange={e=>this.saveEmail(e)} defaultValue={local.props.user.email}/>
                            <Button onClick={()=>this.updateEmail()}>修改邮箱</Button>
                        </Form.Item>
                        <Form.Item label="手机">
                            <Input onChange={e=>this.savePhone(e)} defaultValue={local.props.user.phone}/>
                            <Button onClick={()=>this.updatePhone()}>修改手机号</Button>
                        </Form.Item>
                        <Form.Item label="密码">
                            <Button onClick={()=>this.updatePsd()}>修改密码</Button>
                            <Modal
                                title='修改密码'
                                open={this.state.showMod}
                                footer={[]}
                                onCancel={()=>{
                                    this.setState({
                                        showMod:false
                                    })
                                }}
                            >
                                <UpdatePsd close={this.closeMod.bind(this)}/>
                            </Modal>
                        </Form.Item>
                        <Form.Item label="头像">
                            <Image src={local.state.url} style={{width:100}}/>
                            <Upload
                                headers={{Auth:local.props.token}}
                                action={path}
                                accept="image/*"
                                name="file"
                                onChange={local.handleChange}
                            >
                                <Button icon={<UploadOutlined />}>上传图片</Button>
                            </Upload>
                        </Form.Item>
                        <Form.Item label="详细信息" >
                            <Button onClick={()=>this.showUpdateDetail()}>点击修改</Button>
                            <Modal
                                title='修改详细信息'
                                open={this.state.showDetail}
                                footer={[]}
                                onCancel={()=> {
                                    this.setState({
                                        showDetail: false
                                    })
                                }}
                            >
                                <UpdateUserDetail close={this.closeUpdateDetail.bind(this)}/>
                            </Modal>
                        </Form.Item>
                    </Form>
                </div>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    detail:state.detail,
    user:state.user
})
export default connect(mapStateToProps)(UserCenter);