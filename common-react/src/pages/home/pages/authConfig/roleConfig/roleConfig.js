import React, {Component} from 'react';
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {departments, roles, stations,routes} from "../../../../../stores/auth/action";
import {Button, message, Popconfirm, Select, Table, Typography, Form, Input, InputNumber} from "antd";
import {FormInstance} from "antd";

class RoleConfig extends Component {
    formRef:any = React.createRef()
    //formRef = React.createRef<FormInstance>(null)
    constructor(props) {
        super(props);
        this.state = {
            select:[],
            columns:[
                {
                    title:'编号',
                    dataIndex:'key',
                    fixed:'left',
                    align:'center',
                    width:60
                },
                {
                    title:'名称',
                    dataIndex: 'roleName',
                    key:'roleName',
                    fixed: 'left',
                    align: 'center',
                    width: 200,
                    editable:true
                },
                {
                    title:'权重',
                    dataIndex: 'weight',
                    key:'weight',
                    fixed: 'left',
                    align: 'center',
                    width: 200,
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
                        <Button type='primary' onClick={()=>this.changeStatus(record.id,record.deleated)}>{record.deleated === '启用'?'禁用':'启用'}</Button>
                    )
                },
                {
                    title: '编辑',
                    dataIndex: 'edit',
                    key:'编辑',
                    align: 'center',
                    width:100,
                    render:(_,record)=>{
                        const editable = this.isEditing(record)
                        return editable?(
                            <span>
                                <Typography.Link onClick={()=>this.saveRole(record)} style={{ marginRight: 8 }}>保存</Typography.Link>
                                <Popconfirm title={'确定取消？'} onConfirm={()=>this.cancel()}><a>取消</a></Popconfirm>
                            </span>
                        ):(
                            <Typography.Link disabled={this.state.editingKey!==''} onClick={()=>this.setEdit(record)}>开始编辑</Typography.Link>
                        )
                    }
                }
            ],
            list:[],
            editingKey:'',
            roleName:'',
            weight:''
        }
    }

    componentDidMount() {
        this.getDepts()
        this.getStations()
        this.getAllRole()
        this.getAllRoutes()
    }
    async getDepts(){
        const local = this
        if (local.props.department){
            local.setState({
                depts:local.props.department
            })
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
            local.setState({
                depts:temp
            })
            local.props.dispatch(departments(temp))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async getStations(){
        const local = this
        if (local.props.station){
            local.setState({
                stations:local.props.station
            })
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
            local.setState({
                stations:temp
            })
            local.props.dispatch(stations(temp))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async getAllRole(){
        const local = this
        if (local.props.roles){
            local.createRole(local.props.roles)
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
            const r =local.createRole(roles1)
            local.setState({
                roles:r
            })
            local.props.dispatch(roles(r))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    createRole(roles){
        const data = []
        roles.map((item,index)=>{
            const del = item.deleated
            if (del === 0 || del === 1)
                item.deleated = del === 0?'启用':'禁用'
            data.push({
                ...item,
                key:index
            })
        })
        this.setState({
            roles:data
        })
        return data
    }
    async getAllRoutes(){
        const local = this
        if (local.props.routes){
            this.setState({
                routes:local.props.routes
            })
            return
        }
        const msg = await request({
            url:'/auth/routes/l',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const routes1 = res.data.data
            const r =[]
            routes1.map((item,index)=>{
                const t = {
                    value:item.id,
                    label:item.title,
                    path:item.path,
                    children:item.children
                }
                r.push(t)
            })
            local.setState({
                routes:r
            })
            local.props.dispatch(routes(r))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async changeStatus(id,del){
        const local = this
        const msg = await request({
            url:'/auth/role/del',
            method:'put',
            params:{
                id:id,
                del:del === '启用'?0:1
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = []
            const list = local.state.roles
            for (let i = 0; i < list.length; i++) {
                if (list[i].id === id){
                    list[i].deleated = del === '启用'?'禁用':'启用'
                }
                data.push(list[i])
            }
            local.setState({
                roles:data
            })
            message['success']('修改成功')
        }).catch(function (e) {
            message['error']('数据错误，请检查输入数据')
        })
    }
    mergerColumns = ()=>{
        const data = []
        this.state.columns.map((col)=>{
            if (!col.editable) {
                data.push(col)
            }else{
                data.push({
                    ...col,
                    onCell:(record,rowIndex)=>{
                        return{
                            record,
                            inputType: col.dataIndex === 'roleName'?'text':'number',
                            dataIndex: col.dataIndex,
                            title: col.title,
                            editing: this.isEditing(record),
                            onChange:(e)=>this.saveFormValue(e,col.dataIndex)
                        }
                    }
                })
            }
        })
        return data
    }
    isEditing(items){
        //console.log(item.key === this.state.editingKey)
        return items.key === this.state.editingKey
    }
    cancel(){
        this.setState({
            editingKey:''
        })
    }
    async saveRole(items){
        const local = this
        //console.log(this.state.roleName)
        const msg = await request({
            url:'/auth/role/update',
            method:'put',
            params:{
                id:items.id,
                roleName:local.state.roleName,
                weight:local.state.weight
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = []
            const list = local.state.roles
            for (let i = 0; i < list.length; i++) {
                if (list[i].id === items.id){
                    list[i].roleName = local.state.roleName
                    list[i].weight = local.state.weight
                }
                data.push(list[i])
            }
            local.setState({
                roles:data
            })
            message['success']('修改成功')
        }).catch(function (e) {
            message['error']('修改失败，请稍后再试')
        })
        this.setState({
            editingKey:''
        })
    }
    setEdit(items){
        this.formRef.current.setFieldsValue({
            roleName:'',weight:'',...items
        })
        this.setState({
            editingKey:items.key,
            roleName:items.roleName,
            weight:items.weight
        })
    }
    EditableCell = ({title,editing,children,dataIndex,record,inputType,handleSave,...restProps}) => {
        const inputNode = inputType === 'number'?<InputNumber/>:<Input/>
        return (
            <td {...restProps}>
                {editing
                    ?<Form.Item style={{margin: 0}} name={dataIndex} rules={[{ required: true, message: '不能为空' }]}>
                        {inputNode}
                    </Form.Item>
                    :children
                }
            </td>
        );
    }
    saveFormValue(e,dataIndex){
        if (dataIndex === 'weight'){
            this.setState({
                weight:e.target.value
            })
        }
        if (dataIndex === 'roleName'){
            this.setState({
                roleName:e.target.value
            })
        }

    }
    render() {
        const {select,roles} = this.state
        return (
            <div>
                <h1>角色列表</h1>
                <Form component={false} ref={this.formRef}>
                    <div className='list-wrap'>
                        <Table components={{
                            body:{
                                cell:this.EditableCell
                            }
                        }}
                               columns={this.mergerColumns()} dataSource={roles} scroll={{x:1500,y:640}}
                               pagination={false}
                        />
                    </div>
                </Form>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    department:state.departments,
    station:state.stations,
    roles:state.roles,
    routes:state.routes
})
export default connect(mapStateToProps)(RoleConfig);