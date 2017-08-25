import axios from "axios";
import React from "react";
import {Button, Modal} from "react-bootstrap";
import User from "../util/User";

class RequestsList extends React.Component {
    constructor(props) {
        super();
        this.state = {
            users: []
        }

        this.handleClick = this.handleClick.bind(this);
        this.handleUserAccept = this.handleUserAccept.bind(this);
        this.handleUserIgnore = this.handleUserIgnore.bind(this);
    }

    // This function is called before render() to initialize its state.
    componentWillMount() {
        axios.get('/api/friendship/requests')
            .then(({data}) => {
                this.setState({
                    users: data
                })
            });
    }


    handleClick(id) {
        this.props.history.push(`/user/${id}`);
    }
    
    handleUserAccept(id) {
        axios.post(`/api/friendship/${id}/accept`)
            .then((data) => {
               
            });
    }
    handleUserIgnore(id) {
        axios.post(`/api/friendship/${id}/ignore`)
            .then((data) => {
                
            });
    }

    renderUsers() {
        return this.state.users.map((user => {

            let date = new Date(user.registerDate).toDateString();

            return (
                <tr key={user.id} onClick={() => this.handleClick(user.id)} className={user.online ? 'success' : ''}>
                    <td>{date}</td>
                    <td>{user.online ? 'Online' : 'Offline'} </td>
                    <td>{user.name}</td>
                    <td>
                    <Button onClick={this.handleUserAccept(user.id)} bsStyle="btn btn-success">Accept</Button> | 
            			<Button onClick={this.handleUserIgnore(user.id)} bsStyle="btn btn-error">Ignore</Button>
                    </td>
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
                        <th className="col-sm-2">Status</th>
                        <th className="col-sm-6">Name</th>
                        <th className="col-sm-2">Action</th>
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


export default RequestsList;