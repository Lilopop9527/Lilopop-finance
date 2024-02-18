import React, {Component} from 'react';
import request from "../../../../../utils/request";
import {connect} from "react-redux";
import {Button, Input, message, Modal, Pagination, Radio, Table} from "antd";
import {firstEntry, secondEntry} from "../../../../../stores/auth/action";
import Update from "./update";
import Create from "./save";
class EntryList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            pageNum:0,
            pageSize:20,
            total:0,
            searchValue:'',
            showCreate:false,
            showUpdate:-1,
            first:[],
            second:[],
            radioValue:0,
            delSelect:[],
            columns:[
                {
                title:'编号',
                dataIndex:'key',
                key:'key',
                fixed:'left',
                align:'center',
                width:30
                },
                {
                    title:'名称',
                    dataIndex: 'name',
                    key:'name',
                    fixed: 'left',
                    align: 'center',
                    width: 150
                },
                {
                    title:'上级名称',
                    dataIndex:'parent',
                    key:'parent',
                    align: 'center',
                    width: 150
                },
                {
                    title:'状态',
                    dataIndex:'deleted',
                    key:'deleted',
                    align: 'center',
                    width: 150
                },
                {
                    title:'修改状态',
                    dataIndex:'changeSta',
                    key:'changeSta',
                    align: 'center',
                    width: 100,
                    render:(_,record)=>(
                        <Button type='primary' onClick={()=>this.del(record.id,record.deleted)}>{record.deleted === '禁用'?'启用':'禁用'}</Button>
                    )
                },
                {
                    title:'修改信息',
                    dataIndex: 'update',
                    key:'update',
                    align: 'center',
                    width: 100,
                    render:(_,record)=>([
                        <Button type='dashed' onClick={()=>this.update(record.id)}>修改信息</Button>,
                        <Modal title = '修改信息' open={this.state.showUpdate === record.id} footer={[]}
                               onCancel={()=>{
                                   this.setState({
                                       showUpdate:-1
                                   })
                               }}
                               cancelText='取消'
                        >
                            <Update entry={record} close={this.closeUpdate.bind(this)}/>
                        </Modal>
                    ])
                }]
        }
    }
    componentDidMount() {
        this.searchAll(0,this.state.pageSize)
    }
    searchAll = async (pn,pz)=>{
        const local = this
        let size = local.state.pageSize
        if (pz !== local.state.pageSize){
            size = pz
            local.setState({
                pageSize:pz
            })
        }
        if (pn === 0)
            pn = 1
        const msg = await request({
            url:'/finance/entry/l',
            method:'get',
            params:{
                pageNum:pn,
                pageSize:size
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = res.data.data.data
            local.createMsg(data,local)
            local.setState({
                total:res.data.data.total,
                pageNum:res.data.data.pageNum,
                pageSize:res.data.data.pageSize
            })
            local.props.dispatch(firstEntry(local.state.first))
            local.props.dispatch(secondEntry(local.state.second))
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }

    searchByName = async (pn,pz)=>{
        const local = this
        if (local.state.searchValue === ''||local.state.searchValue.length === 0)
            return
        let size = local.state.pageSize
        if (pz !== local.state.pageSize){
            size = pz
            local.setState({
                pageSize:pz
            })
        }
        if (pn === 0)
            pn = 1
        const msg = await request({
            url:'/finance/entry/lbyn',
            method:'get',
            params:{
                pageNum:pn,
                pageSize:size,
                name:'%'+local.state.searchValue+'%'
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = res.data.data.data
            local.createMsg(data)
            local.setState({
                total:res.data.data.total,
                pageNum:res.data.data.pageNum,
                pageSize:res.data.data.pageSize
            })
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }

    searchByFirst = async (pn,pz)=>{
        const local = this
        let size = local.state.pageSize
        if (pz !== local.state.pageSize){
            size = pz
            local.setState({
                pageSize:pz
            })
        }
        if (pn === 0)
            pn = 1
        const msg = await request({
            url:'/finance/entry/lbyf',
            method:'get',
            params:{
                pageNum:pn,
                pageSize:size
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            const data = res.data.data.data
            local.createMsg(data)
            local.setState({
                total:res.data.data.total,
                pageNum:res.data.data.pageNum,
                pageSize:res.data.data.pageSize
            })
        }).catch(function (e) {
            message['error']('网络错误，请稍后再试')
        })
    }
    createMsg = (data,local)=>{
        let a = []
        let b = []
        data.map((item)=>{
            if (item.parent === 0){
                b.push(item)
            }
        })
        local.setState({
            second:b
        })
        data.map((item,index)=>{
            a[index] = {
                key: index+1,
                name: item.name,
                deleted: item.deleted === 0 ? '启用' : '禁用',
                parent:item.parent === 0?'一级菜单':local.getParentName(item.parent,b),
                id:item.id,
                parentId:item.parent
            }
        })
        local.setState({
            first:a
        })
    }

    getParentName=(p,arr)=>{
        let n = '上级条目已作废'
        arr.map((item)=>{
            if (item.id === p){
                n = item.name
            }
        })
        return n
    }
    del = async (id,del)=>{
        const local = this
        await request({
            url:'/finance/entry/del',
            method:'delete',
            params:{
                id:id,
                del:del === '启用'?0:1
            },
            headers:{
                Auth:local.props.token
            }
        }).then(function (res) {
            let data = []
            const arr = local.state.first
            for (let i = 0; i < arr.length; i++) {
                if (arr[i].id === res.data.data.id){
                    data[i] = {
                        key: i+1,
                        name: res.data.data.name,
                        deleted: res.data.data.deleted === 0 ? '启用' : '禁用',
                        parent:res.data.data.parent === 0?'一级菜单':local.getParentName(res.data.data.parent,local.state.second),
                        id:res.data.data.id
                    }
                }else {
                    data[i] = {...arr[i]}
                }
            }
            if (data.length > 0){
                local.props.dispatch(firstEntry(data))
                local.setState({
                    first:data
                })
            }
            message['success']('修改成功')
        }).catch(function (e) {
            console.log(e)
            message['error']('修改失败，请稍后再试')
        })

    }
    update = (id)=>{
        //console.log(id,showUpdate)
        this.setState({
            showUpdate:id
        })
    }
    closeUpdate = ()=>{
        this.setState({
            showUpdate:-1
        })
    }
    save = ()=>{
        this.setState({
            showCreate:true
        })
    }

    closeSave = ()=>{
        this.setState({
            showCreate:false
        })
    }
    changeRadioValue=(e)=>{
        this.setState({
            radioValue:e.target.value
        })
    }

    search = (pn,pz)=>{
        switch (this.state.radioValue){
            case 1:
                this.searchByName(pn,pz)
                break
            case 2:
                this.searchByFirst(pn,pz)
                break
            default:
                this.searchAll(pn,pz)
        }
    }

    changePage = (page,size)=>{
        this.search(page,size)
    }
    changeDelSelect = (selectedRowKeys)=>{
        this.setState({
            delSelect:selectedRowKeys
        })
    }
    changeSearchValue = (e)=>{
        this.setState({
            searchValue:e.target.value
        })
    }

    render() {
        const {radioValue,pageSize,showCreate,columns,first,delSelect,total} = this.state
        return (
            <div>
                <div>
                    <Input style={{width:200}} onChange={(e)=>this.changeSearchValue(e)}/>
                    <Radio.Group style={{marginLeft:10}} onChange={(e)=>this.changeRadioValue(e)} value={radioValue}>
                        <Radio value={0}>全部</Radio>
                        <Radio value={1}>按名称</Radio>
                        <Radio value={2}>只看一级项目</Radio>
                    </Radio.Group>
                    <Button type='primary' onClick={()=>this.search(1,pageSize)}>查询</Button>
                    <Button type='primary' onClick={()=>this.save()} style={{float:"right",marginRight:40}}>添加条目</Button>
                    <Modal title='添加信息' open={showCreate} footer={[]}
                           onCancel={()=>{
                               this.setState({
                                   showCreate:false
                               })
                           }}
                           cancelText='取消'
                    >
                        <Create close={this.closeSave.bind(this)}/>
                    </Modal>
                </div>
                <Table scroll={{x:1500,y:640}} pagination={false} columns={columns} dataSource={first}
                       rowSelection={{
                           columnTitle:'',
                           columnWidth:25,
                           fixed:true,
                           selectedRowKeys:delSelect,
                           onChange:this.changeDelSelect
                       }}
                />
                <Pagination className='pagination'
                            total={total}
                            showSizeChanger
                            showQuickJumper
                            showTotal={()=>{
                                return '共'+total+'条'
                            }}
                            pageSize={pageSize}
                            onChange={(page,size)=>this.changePage(page,size)}
                />
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    firstEntry:state.firstEntry,
    secondEntry:state.secondEntry
})
export default connect(mapStateToProps)(EntryList)