import React, {Component} from 'react';
import request from "../../../../utils/request";
import {connect} from "react-redux";
import {Button, DatePicker, Form, Input, message, Radio,Select} from "antd";
import {departments, detail, roles, stations} from "../../../../stores/auth/action";
import dayjs from "dayjs";
import customParseFormat from "dayjs/plugin/customParseFormat";
import weekday from "dayjs/plugin/weekday";
import localeData from "dayjs/plugin/localeData";
import './userList.css'
const format = 'YYYY-MM-DD'
const {TextArea} = Input
dayjs.extend(customParseFormat)
dayjs.extend(weekday)
dayjs.extend(localeData)
class UserDetail extends Component {
    constructor(props) {
        super(props);
        this.state={
            uDept:[],
            uStation:[],
            uRole:[],
            roles:[],
            detail:{...this.props.user.detail},
            birthday:this.props.user.detail.birthday
        }
    }
    componentDidMount() {
        this.getDepts()
        this.getStations()
        this.getAllRole()
        const dept = []
        const sta=[]
        const r =[]
        this.props.user.depts.map((item,index)=>{
            const t = {
                value:item.id,
                label:item.name
            }
            dept.push(t)
        })
        this.props.user.stations.map((item,index)=>{
            const t = {
                value:item.id,
                label:item.name
            }
            sta.push(t)
        })
        this.props.user.roles.map((item,index)=>{
            const t = {
                value:item.id,
                label:item.name
            }
            r.push(t)
        })
        this.setState({
            uDept:dept,
            uStation:sta,
            uRole:r
        })
    }
    async getDepts(){
        const local = this
        if (local.props.department){
            return
        }
        const msg = await request({
            url: '/auth/dept/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const temp = []
            res.data.data.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.name
                }
                temp.push(t)
            })
            local.props.dispatch(departments(temp))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
            local.closeModal()
        })
    }
    async getStations(){
        const local = this
        if (local.props.station){
            return
        }
        const msg = await request({
            url: '/auth/sta/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const temp = []
            res.data.data.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.name
                }
                temp.push(t)
            })
            local.props.dispatch(stations(temp))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
            local.closeModal()
        })
    }
    async getAllRole(){
        const local = this
        if (local.props.roles){
            this.setState({
                roles:local.props.roles
            })
            return
        }
        const msg = await request({
            url:'/auth/role/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const roles1 = res.data.data
            const r =[]
            roles1.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.roleName
                }
                r.push(t)
            })
            local.setState({
                roles:r
            })
            local.props.dispatch(roles(r))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    closeModal(){
        this.props.close(-1)
    }
    submit = async (values) => {
        const local = this
        const {dispatch} = local.props
        const msg = await request({
            url:'/auth/user/detail',
            method:'put',
            params:{
                ...local.state.detail,
                birthday: local.state.birthday
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            if (res.data.code === 200)
                message['success']("更新成功")
            dispatch(detail(res.data.data))
        }).catch(function (err) {
            message['error']('更新失败，请稍后再试')
        })
    };
    fieldChange=(field,fields)=>{
        this.setState({
            detail:fields
        })
    }
    changeDate(e){
        if (!e)return
        this.setState({
            birthday:e.valueOf()
        })
    }
    changeDept=(value)=>{
        this.setState({
            uDept:value
        })
    }
    changeSta=(value)=>{
        this.setState({
            uStation:value
        })
    }
    changeRole=(value)=>{
        this.setState({
            uRole:value
        })
    }
    async changeUTD(){
        const deptIds = this.changeToString(this.state.uDept)
        const local = this
        const msg = await request({
            url:'/auth/dept/utd',
            method:'put',
            params:{
                userId:local.state.detail.userId,
                deptIds:deptIds
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            message['success']('修改部门成功')
        }).catch(function (err) {
            message['error']('修改部门信息失败')
        })
    }
    async changeUTS(){
        const staIds = this.changeToString(this.state.uStation)
        const local = this
        const msg = await request({
            url:'/auth/sta/uts',
            method:'put',
            params:{
                userId:local.state.detail.userId,
                staIds:staIds
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            message['success']('修改岗位成功')
        }).catch(function (err) {
            message['error']('修改岗位信息失败')
        })
    }
    async changeUTR(){
        const roleIds = this.changeToString(this.state.uRole)
        const local = this
        const msg = await request({
            url:'/auth/role/utr',
            method:'put',
            params:{
                userId:local.state.detail.userId,
                roleIds:roleIds
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            message['success']('修改角色成功')
        }).catch(function (err) {
            message['error']('修改角色信息失败')
        })
    }
    changeToString(arr){
        const n = arr.length
        let str = arr[0]+','
        for (let i = 1; i < n-1 ; i++) {
            str = str + arr[i] +','
        }
        str = str + arr[n-1]
        return str
    }
    render() {
        const local = this
        const date = local.state.detail.birthday === null?dayjs(new Date()).format(format):dayjs(new Date(parseInt(local.state.detail.birthday))).format(format)
        return (
            <div className='msg'>
                <div className='select'>
                    部门：<Select
                        mode="multiple"
                        size={'middle'}
                        placeholder="请设置部门"
                        value={local.state.uDept}
                        onChange={local.changeDept}
                        style={{ width: '70%' }}
                        options={local.props.department}
                    />
                    <br/>
                    <Button className='button' type='primary' onClick={this.changeUTD.bind(this)}>修改部门</Button>
                </div>
                <div className='select'>
                    岗位：<Select
                    mode="multiple"
                    size={'middle'}
                    placeholder="请设置岗位"
                    value={local.state.uStation}
                    onChange={local.changeSta}
                    style={{ width: '70%' }}
                    options={local.props.station}
                />
                    <br/>
                    <Button className='button' type='primary' onClick={this.changeUTS.bind(this)}>修改岗位</Button>
                </div>
                <div className='select'>
                    角色：<Select
                    mode="multiple"
                    size={'middle'}
                    placeholder="请设置角色"
                    value={local.state.uRole}
                    onChange={local.changeRole}
                    style={{ width: '70%' }}
                    options={local.state.roles}
                />
                    <br/>
                    <Button className='button' type='primary' onClick={this.changeUTR.bind(this)}>修改角色</Button>
                </div>
                <Form
                    labelCol={{span:4}}
                    wrapperCol={{span:14}}
                    style={{maxWidth:600}}
                    onFinish={local.submit}
                    onValuesChange={local.fieldChange}
                    initialValues={local.state.detail}
                >
                    <Form.Item name='userId' label='userId' hidden={true}>
                        <Input disabled={true}/>
                    </Form.Item>
                    <Form.Item name='id' label='id' hidden={true}>
                        <Input disabled={true}/>
                    </Form.Item>
                    <Form.Item name='firstName' label='姓'>
                        <Input placeholder='请输入姓'/>
                    </Form.Item>
                    <Form.Item name='lastName' label='名'>
                        <Input placeholder='请输入名'/>
                    </Form.Item>
                    <Form.Item name='date' label='生日' initialValue={dayjs(date,format)}>
                        <DatePicker name='date' placeholder='请选择生日' format={format} onChange={(e)=>this.changeDate(e)}/>
                    </Form.Item>
                    <Form.Item name='cardNumber' label='身份证'>
                        <Input placeholder='请输入身份证号'/>
                    </Form.Item>
                    <Form.Item name='height' label='身高'>
                        <Input placeholder='请输入身高' addonAfter='cm'/>
                    </Form.Item>
                    <Form.Item name='weight' label='体重'>
                        <Input placeholder='请输入体重' addonAfter='kg'/>
                    </Form.Item>
                    <Form.Item name='bloodType' label='血型'>
                        <Radio.Group name = 'bloodType'>
                            <Radio value={'A'}>A</Radio>
                            <Radio value={'B'}>B</Radio>
                            <Radio value={'AB'}>AB</Radio>
                            <Radio value={'O'}>O</Radio>
                        </Radio.Group>
                    </Form.Item>
                    <Form.Item name='hobby' label='兴趣'>
                        <TextArea
                            showCount
                            maxLength={50}
                            style={{ height: 120, marginBottom: 24 }}
                            placeholder="请输入您的兴趣爱好"
                        />
                    </Form.Item>
                    <Form.Item name='introduce' label='个人介绍'>
                        <TextArea
                            showCount
                            maxLength={200}
                            style={{ height: 120, marginBottom: 24 }}
                            placeholder="请输入您的个人介绍"
                        />
                    </Form.Item>
                    <Form.Item wrapperCol={{ offset: 8 }}>
                        <Button type="primary" htmlType="submit">
                            提交
                        </Button>
                    </Form.Item>
                </Form>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    department: state.departments,
    station:state.stations,
    roles:state.roles
})
export default connect(mapStateToProps)(UserDetail);