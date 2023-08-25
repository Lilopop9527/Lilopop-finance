import React, {Component} from 'react';
import {Menu} from "antd";
import menus from "../../../routes/menuConfig";
import {Link} from "react-router-dom";
import '../home.css'
const SubMenu = Menu.SubMenu
class Left extends Component {
    constructor(props) {
        super(props);
        this.state = {
            menuItem: ''
        }
    }

    componentDidMount() {
        const menuItem = this.getMenuItem(menus)
        this.setState({
            menuItem: menuItem
        })
    }

    getMenuItem = (data)=>{
        return data.map(
            (item)=>{
                if (item.path !== '/'){
                    // 有子菜单
                    if(item.children){
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

export default Left;