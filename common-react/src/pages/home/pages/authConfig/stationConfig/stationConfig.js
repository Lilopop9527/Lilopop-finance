import React, {Component} from 'react';
import {connect} from "react-redux";
import request from "../../../../../utils/request";
import {Button, Form, Input, InputNumber, message, Modal, Popconfirm, Table, Typography} from "antd";
import {stations} from "../../../../../stores/auth/action";
import CreateStation from "./createStation";
class StationConfig extends Component {
    formRef:any = React.createRef()
    constructor(props) {
        super(props);
        this.state = {
            columns:[
                {
                    title:'编号',
                    dataIndex:'key',
                    fixed:'left',
                    align:'center',
                    width:50
                },
                {
                    title:'名称',
                    dataIndex: 'name',
                    key:'name',
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
                                <Typography.Link onClick={()=>this.saveStation(record)} style={{ marginRight: 8 }}>保存</Typography.Link>
                                <Popconfirm title={'确定取消？'} onConfirm={()=>this.cancel()}><a>取消</a></Popconfirm>
                            </span>
                        ):(
                            <Typography.Link disabled={this.state.editingKey!==''} onClick={()=>this.setEdit(record)}>开始编辑</Typography.Link>
                        )
                    }
                }
            ],
            editingKey:'',
            name:'',
            stations:[],
            showModal:false
        }
    }
    componentDidMount() {
        this.getStations()
    }

    async getStations(){
        const local = this
        if (local.props.station){
            const list = local.props.station
            const data = []
            list.map((item,index)=>{
                data.push({
                    id:item.id,
                    name:item.name,
                    deleated:item.deleated === 0?'启用':'禁用',
                    key:index+1
                })
            })
            local.setState({
                stations:data
            })
            return
        }
        const msg = await request({
            url: '/auth/sta/la',
            method:'get',
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const temp = []
            res.data.data.map((item,index)=>{
                const t = {
                    id:item.id,
                    name:item.name,
                    deleated:item.deleated === 0?'启用':'禁用',
                    key:index+1
                }
                temp.push(t)
            })
            local.setState({
                stations:temp
            })
            local.props.dispatch(stations(res.data.data))
        }).catch(function (e) {
            message['error']('服务器错误，请稍后再试')
        })
    }
    async changeStatus(id,del){
        const local = this
        const msg = await request({
            url:'/auth/sta/del',
            method:'put',
            params:{
                id:id,
                del:del === '启用'?1:0
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = []
            const list = local.state.stations
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
                            inputType: 'text',
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
        return items.key === this.state.editingKey
    }
    cancel(){
        this.setState({
            editingKey:''
        })
    }
    setEdit(items){
        this.formRef.current.setFieldsValue({
            name:'',...items
        })
        this.setState({
            editingKey:items.key,
            name:items.name
        })
    }
    EditableCell = ({title,editing,children,dataIndex,record,inputType,handleSave,...restProps}) => {
        const inputNode = <Input/>
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
        if (dataIndex === 'name'){
            this.setState({
                name:e.target.value
            })
        }

    }
    async saveStation(item){
        const local = this
        const msg = await request({
            url:'/auth/sta/update',
            method:'put',
            params:{
                id:item.id,
                name:local.state.name
            },
            headers:{Auth:local.props.token}
        }).then(function (res) {
            const data = []
            const list = local.state.stations
            for (let i = 0; i < list.length; i++) {
                if (list[i].id === item.id){
                    list[i].name = local.state.name
                }
                data.push(list[i])
            }
            local.setState({
                stations:data
            })
            local.props.dispatch(stations(data))
            message['success']('修改成功')
            local.cancel()
        }).catch(function (e) {
            message['error']('修改失败，请稍后再试')
        })
    }
    showModal(){
        this.setState({
            showModal:true
        })
    }
    closeModal(){
        this.setState({
            showModal:false
        })
    }
    addSta(e){
        const list = this.state.stations
        const data = []
        list.map((item)=>{
            data.push(item)
        })
        data.push({
            id:e.id,
            name:e.name,
            deleated:e.deleated ===0?'启用':'禁用',
            key:list.length+1
        })
        this.setState({
            stations:data
        })
        this.props.dispatch(stations(data))
    }
    render() {
        const {stations} = this.state
        return (
            <div>
                <h1>岗位列表</h1>
                <Form component={false} ref={this.formRef}>
                    <Table components={{
                        body: {
                            cell: this.EditableCell
                        }
                    }}
                           columns={this.mergerColumns()} dataSource={stations} scroll={{x:1500,y:640}}
                           pagination={false}/>
                </Form>
                <Button type='primary' style={{marginTop:10}} onClick={()=>this.showModal()}>添加岗位</Button>
                <Modal title='创建岗位' open={this.state.showModal} footer={[]}
                    onCancel={()=>this.closeModal()}
                       cancelText='取消'
                >
                    <CreateStation close = {this.closeModal.bind(this)} addSta = {this.addSta.bind(this)}/>
                </Modal>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    station:state.stations,
})
export default connect(mapStateToProps)(StationConfig);