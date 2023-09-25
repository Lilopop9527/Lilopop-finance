import React, {Component} from 'react';
import {connect} from "react-redux";
import {delToken, detail, login} from "../../../../../stores/auth/action";
import '../userCenter.css'
import dayjs from "dayjs";
import weekday from 'dayjs/plugin/weekday'
import localeData from 'dayjs/plugin/localeData'
import customParseFormat from 'dayjs/plugin/customParseFormat';
import {Button, DatePicker, Form, Input, message, Radio, Select} from "antd";
import request from "../../../../../utils/request";
const {TextArea} = Input
const format = 'YYYY-MM-DD'
dayjs.extend(customParseFormat)
dayjs.extend(weekday)
dayjs.extend(localeData)
class UpdateUserDetail extends Component {
    constructor(props) {
        super(props);
        this.state={
            detail:{...this.props.detail},
            birthday:this.props.detail.birthday
        }
    }

    componentDidMount() {
        const p = this.props
        const token = p.token
        const {dispatch} = p
        if(token === 'empty'||!token||token === ''){
            dispatch(delToken('empty'))
            dispatch(login(1))
        }
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
        this.setState({
            birthday:e.valueOf()
        })
    }
    render() {
        const local = this
        const date = local.state.detail.birthday === null?null:dayjs(new Date(parseInt(local.state.detail.birthday))).format(format)
        return (
            <div className='msg'>
                <Form
                    labelCol={{span:4}}
                    wrapperCol={{span:14}}
                    style={{maxWidth:600}}
                    onFinish={this.submit}
                    onValuesChange={this.fieldChange}
                    initialValues={this.state.detail}
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
    detail:state.detail,
    user:state.user
})
export default connect(mapStateToProps)(UpdateUserDetail);