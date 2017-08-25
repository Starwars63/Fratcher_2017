import axios from "axios";
import React from "react";
import {Button, Modal} from "react-bootstrap";

import User from "../util/User";

class UserDetail extends React.Component {
    constructor(props) {
        super();
        this.state = {
            user: undefined,
            chats: [],
            post: '',
            message: '',
            askDelete: false
        }

        this.handlePostSubmit = this.handlePostSubmit.bind(this);
        this.handlePostChange = this.handlePostChange.bind(this);
        this.handleMessageSubmit = this.handleMessageSubmit.bind(this);
        this.handleMessageChange = this.handleMessageChange.bind(this);
        this.handleUserLike = this.handleUserLike.bind(this);
        this.handleUserDisLike = this.handleUserDisLike.bind(this);
        this.askDelete = this.askDelete.bind(this);
    }

    componentWillMount() {
        axios.get(`/api/user/${this.props.match.params.id}`)
            .then(({data}) => {
                this.setState({
                    user: data
                });
                
                this.setState({
                    post: data.post
                });
                
            });
        
        
        axios.get(`/api/chats/${this.props.match.params.id}`)
        .then(({data}) => {
            this.setState({
            		chats: data
            });
        });
        
    }

    handlePostChange(event) {
        this.setState({post: event.target.value});
    }

    handleMessageChange(event) {
        this.setState({message: event.target.value});
    }
    
    handleUserLike() {
        axios.post(`/api/friendship/${this.props.match.params.id}/like`)
            .then((data) => {
               
            });
    }
    handleUserDisLike() {
        axios.post(`/api/friendship/${this.props.match.params.id}/dislike`)
            .then((data) => {
                
            });
    }
    
    handlePostSubmit(event) {
        event.preventDefault();

        if (this.state.post.trim() === "") {
            return;
        }

        axios.post(`/api/user/post`,
            {
                post: this.state.post
            })
            .then((data) => {
                this.componentWillMount();
            });
    }
    
    handleMessageSubmit(event) {
        event.preventDefault();

        if (this.state.message.trim() === "") {
            return;
        }
        
        axios.post(`/api/chat/${this.props.match.params.id}/message`,
            {
                message: this.state.message
            })
            .then((data) => {
                this.renderChats();
            });
    }

    deletePost(id) {
        axios.delete(`/api/user/post`)
            .then((data) => {
                this.props.history.push("/");
            });
    }

    askDelete() {
        this.setState({askDelete: true});
    }

    renderChats() {
        return this.state.chats.map((chat => {
            return (
                <tr key={chat.id}>
                <td className="col-sm-2">{chat.source.id == this.state.user.id ? "Me" : chat.source.name}</td>    
                <td className="col-sm-2">{new Date(chat.createdAt).toDateString()}</td>
                    <td className="col-sm-8">{chat.message}</td>
                </tr>
            );
        }));
    }

    render() {
        const user = this.state.user;
        if (!user) {
            // Do not show anything while loading.
            return <div></div>;
        }

        return (
            <div>

                <div>
                    {/*A row in a bootstrap context must be stored in a container*/}
                    <div className="container-fluid post-detail">
                        <div>
                        
                            <span className="post-title">{user.name}</span>
                            
                            <div className="pull-right delete-button">
                                { User.isAuthenticated() && User.id == this.state.user.id &&
                                <button
                                    onClick={() => this.askDelete()}
                                    className="btn btn-danger">Delete Post</button>
                                }
                            </div>
                        </div>
                    </div>

                    <div className="row">
                    <div className="col-md-8">
                    <h3>Chat</h3>
                    <hr />
                    
                    {this.state.chats.length == 0 ? "No Messages yet" : ""}
                    
                    <table className="table table-striped">
                        <tbody>
                        {this.renderChats()}
                        </tbody>
                        <tfoot>
                        
                        </tfoot>
                    </table>
                    <hr/>
                    <form onSubmit={this.handleMessageSubmit}>
                    <div className="form-group">
                    <textarea
                        autoFocus={true}
                        placeholder="Your message..."
                        className="form-control"
                        name="message"
                        value={this.state.message}
                    	   onChange={this.handleMessageChange}
                    />
                    </div>
                    <input type="submit" value="Submit" className="btn btn-success"/>
                </form>
                    </div>
                    <div className="col-md-4">
                    <h3>Post</h3>
                    <hr />
                    { !User.isAuthenticated() || User.id != this.state.user.id &&
                    	
                    	<div class="user-post">
                    
                {this.state.user.post}
                    	
                    	
                    	
                    	</div>
                    	
                    	
                    	
                    }
                    { User.isAuthenticated() && User.id != this.state.user.id &&
                    <div>
                	<Button onClick={this.handleUserLike} bsStyle="btn btn-success">Like</Button> | 
            		<Button onClick={this.handleUserDisLike} bsStyle="btn btn-success">DisLike</Button>
                	</div>
                    }
                    { User.isAuthenticated() && User.id == this.state.user.id &&
                    <form onSubmit={this.handlePostSubmit}>
                        <div className="form-group">
                        <textarea
                            autoFocus={true}
                            placeholder="Your post..."
                            className="form-control"
                            name="post"
                            value={this.state.post}
                        	   onChange={this.handlePostChange}
                        />
                        </div>
                        <input type="submit" value="Submit" className="btn btn-success"/>
                    </form>
                    }
                    </div>
                    </div>
                    { this.state.askDelete &&
                    <Modal.Dialog>
                        <Modal.Header>
                            <Modal.Title>Are you sure?</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            Do you want to delete your post?
                        </Modal.Body>
                        <Modal.Footer>
                            <Button onClick={() => {
                                this.setState({askDelete: false})
                            }
                            }>Cancel</Button>
                            <Button
                                onClick={() => {
                                    this.deletePost(this.state.user.id)
                                }}
                                bsStyle="primary">Delete</Button>
                        </Modal.Footer>
                    </Modal.Dialog>
                    }
                </div>
            </div>
        );
    }
}

export default UserDetail;