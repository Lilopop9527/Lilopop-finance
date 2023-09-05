import React, {Component} from 'react';
import {Menu} from "antd";
import {Link} from "react-router-dom";
import '../home.css'
import {connect} from "react-redux";
import request from "../../../utils/request";
const SubMenu = Menu.SubMenu
class Left extends Component {
    constructor(props) {
        super(props);
        this.state = {
            menuItem: []
        }
    }

    componentDidMount() {
        const menuItem = this.getMenuItem(this.props.menus)
        this.setState({
            menuItem: menuItem
        })
    }

    //  getMenus(){
    //     const p = this.props
    //     const local = this
    //     const menus =  request({
    //         url: '/auth/route/lists',
    //         method: 'get',
    //         headers: {
    //             'Auth': p.token
    //         }
    //     }).then(
    //         function (response) {
    //             local.setState({
    //                 menus: response.data.data
    //             })
    //         }
    //     )
    // }


    getMenuItem = (data)=>{
        return data.map(
            (item)=>{
                if (item.path !== '/'){
                    // 有子菜单
                    if(item.children.length>0){
                        return(
                            <SubMenu title={item.title} key={item.path}>
                                {this.getMenuItem(item.children)}
                            </SubMenu>
                        )
                    }
                    return (
                        <Menu.Item title={item.title} key={item.path}>
                            <Link to={item.path}>
                                {item.title}
                            </Link>
                        </Menu.Item>
                    )
                }
            }
        )
    }

    render() {
        return (
            <div>
                <div className='logo'>
                    <h1>软件</h1>
                </div>
                <Menu theme='dark'>{this.state.menuItem}</Menu>
            </div>
        );
    }
}
const mapStateToProps = (state)=>({
    token:state.token,
    menus:state.route
})
export default connect(mapStateToProps)(Left);