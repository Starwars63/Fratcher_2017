import axios from "axios";
import React from "react";
import User from "../util/User";

class UsersList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            users: []
        }

        this.handleClick = this.handleClick.bind(this);
    }

    // This function is called before render() to initialize its state.
    componentWillMount() {
        axios.get('/api/user')
            .then(({data}) => {
                this.setState({
                    users: data
                })
            });
    }


    handleClick(id) {
        this.props.history.push(`/user/${id}`);
    }

    renderUsers() {
        return this.state.users.map((user => {

            let date = new Date(user.registerDate).toDateString();

            return (
                <tr key={user.id} onClick={() => this.handleClick(user.id)} className={user.online ? 'success' : ''}>
                    <td>{date}</td>
                    <td>{user.online ? 'Online' : 'Offline'} </td>
                    <td>{user.name}</td>
                </tr>
            );
        }));
    }


    render() {
        return (
            <div className="component">
                <table className="table table-hover">
                    <thead>
                    <tr>
                        <th className="col-sm-2">Member since</th>
                        <th className="col-sm-8">Status</th>
                        <th className="col-sm-2">Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.renderUsers()}
                    </tbody>
                </table>
            </div>
        );
    }
}


export default UsersList;