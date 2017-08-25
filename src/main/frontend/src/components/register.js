import axios from "axios";
import React from "react";

import {translate} from "react-i18next";

// See https://facebook.github.io/react/docs/forms.html for documentation about
// forms.
class UserRegister extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            password:''
        };
        this.handleEmailChange = this.handleEmailChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleNameChange(event) {
        this.setState({name: event.target.value});
    }
    
    handleEmailChange(event) {
        this.setState({email: event.target.value});
    }


    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    handleSubmit(event) {
        event.preventDefault();
        /*
		 * let comments = []; if (this.state.comment) { comments = [{text:
		 * this.state.comment}]; }
		 */
        axios.post('/api/user',
            {
                email: this.state.email,
                name: this.state.name,
                password: this.state.password
            })
            .then((data) => {
                // Redirect to front page.
                this.props.history.push("/");
            });
    }


    render() {
        const {t} = this.props;

        return (
            <div className="component">
                <form onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label>
                            Name
                        </label>
                        <input type="text" name="name" className="form-control" required="required"
                               autoFocus={true}
                               value={this.state.name}
                        		  onChange={this.handleNameChange}
                        />
                    </div>

                        <div className="form-group">
                        <label>
                            Email
                        </label>
                        <input type="email" name="email" className="form-control" required="required"
                        	onChange={this.handleEmailChange}
                               value={this.state.email}/>
                    </div>
                        <div className="form-group">
                        <label>
                            Password
                        </label>
                        <input type="password" name="password" className="form-control"  required="required"
                        	onChange={this.handlePasswordChange}
                        	value={this.state.password}/>
                    </div>
                    <input type="submit" className="btn btn-success" value="Submit"/>
                </form>
            </div>
        );
    }
}

export default translate()(UserRegister);