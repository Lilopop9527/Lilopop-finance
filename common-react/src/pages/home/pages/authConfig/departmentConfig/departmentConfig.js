import React, {Component} from 'react';
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {Button, Form, Input, message, Modal, Popconfirm, Select, Table, Typography} from "antd";
import {departments, leaders} from "../../../../../stores/auth/action";
import CreateDept from "./createDept";

class DepartmentConfig extends Component{
    formRef:any = React.createRef()
    constructor(props) {
        super(props);
        this.state={
            columns : [
                {
                    title:'编号',
                    dataIndex:'key',
                    fixed:'left',
                    align:'center',
                    width:60
                },
                {
                    title:'部门名称',
                    dataIndex:'name',
                    fixed:'left',
                    align:'center',
                    width:60,
                    editable:true
                },
                {
                    title:'负责人',
                    dataIndex:'principalId',
                    fixed:'left',
                    align:'center',
                    width:60,
                    editable:true
                },
                {
                    title: '状态',
                    dataIndex: 'deleated',
                    key:'deleated',
                    align: 'center',
                    width: 80
                },
                {
                    title: '修改状态',
                    dataIndex: 'changeSta',
                    key:'修改状态',
                    align: 'center',
                    width: 100,
                    render:(_,record)=>(
                        <Button type='primary'
                                onClick={()=>this.changeStatus(record.id,record.deleated)}>
                            {record.deleated === '启用'?'禁用':'启用'}
                        </Button>
                    )
                },
                {
                    title: '编辑',
                    dataIndex: 'edit',
                    key: '编辑',
                    align: 'center',
                    width: 100,
                    render: (_, record) => {
                        const editable = this.isEditing(record)
                        return editable ? (
                            <span>
                        <Typography.Link onClick={()=>this.saveDept(record)}
                                         style={{marginRight: 8}}>保存</Typography.Link>
                        <Popconfirm title={'确定取消？'} onConfirm={()=>this.cancel()}><a>取消</a></Popconfirm>
                    </span>
                        ) : (
                            <Typography.Link disabled={this.state.editKey !== ''}
                                             onClick={()=>this.setEdit(record)}>开始编辑</Typography.Link>
                        )
                    }
                }
            ],
            department:[],
            leaders:[],
            name:'',
            principalId:'',
            editKey:'',
            leader:-1,
            showCreate:false
        }
    }
    componentDidMount() {
        this.getLeaders().catch(function (e) {
            console.log(e)
        })
        this.getDepts().catch(function (e) {
            console.log(e)
        })
    }
    async getDepts(){
        const local = this
        if (local.props.departments){
            const data = local.createDepts(local.props.departments)
            local.setState({
                department:data
            })
            return
        }
        const msg = await request({
            url:'/auth/dept/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = local.createDepts(res.data.data)
            local.setState({
                department:data
            })
            local.props.dispatch(departments(res.data.data))
        })
    }
    async getLeaders(){
        const local = this
        if (local.props.leaders){
            const list = local.props.leaders
            const data = local.createLeaders(list)
            local.setState({
                leaders:data
            })
            return
        }
        const msg = await request({
            url:'/auth/user/lea',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const list = res.data.data
            const data = local.createLeaders(list)
            local.setState({
                leaders:data
            })
            local.props.dispatch(leaders(list))
        })
    }
    createLeaders(data){
        const list = []
        data.map((item)=>{
            const t = {
                value:item.id,
                label:item.username,
                key:item.id
            }
            list.push(t)
        })
        return list
    }
    createDepts(data){
        const list = []
        const local = this.state.leaders
        data.map((item,index)=>{
            const flag = item.deleated
            let name = item.principalId
            if (name !== -1){
                for (let i = 0; i < local.length; i++) {
                    if (local[i].id === name){
                        name = local[i].username
                        break
                    }
                }
            }
            item.deleated = flag === 1?'禁用':'启用'
            item.principalId = name === -1?'未选择':name
            list.push({
                ...item,
                key:index+1
            })
        })
        return list
    }
    async changeStatus(id,del){
        const local = this
        const msg = await request({
            url:'/auth/dept/sta',
            method:'put',
            params:{
                id:id,
                del:del === '启用'?1:0
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const list = local.state.department
            const data = []
            for (let i = 0; i < list.length; i++) {
                if (list[i].id === id){
                    list[i].deleated = del === '启用'?'禁用':'启用'
                }
                data.push(list[i])
            }
            local.setState({
                department:data
            })
            message['success']('修改成功')
        }).catch(function (e) {
            message['error']('修改失败，请稍后再试')
        })
    }
    isEditing(record){
        return record.key === this.state.editKey
    }
    async saveDept(item){
        const local = this
        const msg = await request({
            url:'/auth/dept/update',
            method:'put',
            params:{
                id:item.id,
                name:local.state.name,
                principalId:local.state.principalId
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = []
            const list = local.state.department
            for (let i = 0; i < list.length; i++) {
                if (list[i].id === item.id ){
                    list[i].name = local.state.name
                    list[i].principalId = local.state.principalId
                }
                data.push(list[i])
            }
            local.setState({
                department:data
            })
            message['success']('修改成功')
            local.cancel()
        }).catch(function (e) {
            message['error']('修改失败，请稍后再试')
        })
    }
    cancel(){
        this.setState({
            editKey:''
        })
    }
    setEdit(record){
        this.formRef.current.setFieldsValue({
            name:'',principalId:'',...record
        })
        this.setState({
            editKey:record.key,
            name:record.name,
            principalId:record.principalId
        })
    }
    megerColumns = ()=>{
        const data = []
        this.state.columns.map((col)=>{
            if (!col.editable){
                data.push(col)
            }else{
                data.push({
                    ...col,
                    onCell:(record,rowIndex)=>{
                        return{
                            record,
                            inputType:col.dataIndex === 'name'?'text':'select',
                            dataIndex:col.dataIndex,
                            title:col.title,
                            editing:this.isEditing(record),
                            onChange:(e)=>this.saveFormValue(e,col.dataIndex)
                        }
                    }
                })
            }
        })
        return data
    }
    saveFormValue(e,dataIndex){
        if (dataIndex === 'name'){
            this.setState({
                name:e.target.value
            })
        }
    }
    EditableCell = ({title,editing,children,dataIndex,record,inputType,handleSave,...restProps})=>{
        let defaultValue = -1
        if (record){
            defaultValue = record.principalId
        }
        const inputNode = inputType === 'text'?<Input/>:<Select defaultValue={defaultValue === -1?'未选择':defaultValue}
                                                                options={this.state.leaders}
                                                                style={{ width: 120 }}
                                                                onChange={(e)=>this.changeLeader(e)}
                                                        />
        return (
            <td {...restProps}>
                {editing?(
                        <Form.Item style={{margin: 0}} name={dataIndex}
                                   rules={[{ required: true, message: '不能为空' }]}>
                            {inputNode}
                        </Form.Item>
                    )
                    :(children)
                }
            </td>
        )
    }
    changeLeader(val){
        this.setState({
            leader:val,
            principalId:val
        })
    }
    closeModal(){
        this.setState({
            showCreate:false
        })
    }
    showModal(){
        this.setState({
            showCreate:true
        })
    }
    addDept(data){
        const local = this.props.leaders
        let name = data.principalId?data.principalId:-1
        const list = this.state.department
        const t =[]
        list.map((item)=>{
            t.push(item)
        })

        if (name !== -1){
            for (let i = 0; i < local.length; i++) {
                if (local[i].id === name){
                    name = local[i].username
                    break
                }
            }
        }
        const temp = {
            id:data.id,
            name:data.name,
            deleated:data.deleated===0?'启用':'禁用',
            principalId:name === -1?'未选择':name,
            key:local.length+1
        }
        t.push(temp)
        this.setState({
            department:t
        })
        this.props.dispatch(departments(t))
    }
    render(){
        return (
            <div>
                <h1>部门列表</h1>
                <Form component={false} ref={this.formRef}>
                    <Table components={{
                        body:{
                            cell:this.EditableCell
                        }
                    }}
                           columns={this.megerColumns()} dataSource={this.state.department} scroll={{x:1500,y:640}}
                           pagination={false} bordered
                    />
                </Form>
                <Button type='primary' className='button' onClick={()=>this.showModal()}>创建部门</Button>
                <Modal title='创建部门' open={this.state.showCreate} footer={[]}
                    onCancel={()=>{
                        this.closeModal()
                    }}
                       cancelText='取消'
                >
                    <CreateDept close = {this.closeModal.bind(this)} addDept = {this.addDept.bind(this)}/>
                </Modal>
            </div>
        )
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    leaders:state.leaders,
    departments:state.departments
})
export default connect(mapStateToProps)(DepartmentConfig)