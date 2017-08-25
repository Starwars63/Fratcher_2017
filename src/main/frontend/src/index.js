import React from "react";
import {CookiesProvider} from "react-cookie";
import ReactDOM from "react-dom";

import {I18nextProvider} from "react-i18next";
import {HashRouter as Router, Route, Switch} from "react-router-dom";

import Authentication from "./components/authentication";
import UserRegister from "./components/register";
import UsersList from "./components/users";
import MatchesUsersList from "./components/matches";
import RequestsList from "./components/requests";
import UserDetail from "./components/user_detail";

import Navigation from "./components/navigation";
import i18n from "./i18n";
import User from "./util/User";


// Design decision: We use a global parent component for inter-sibling communication.
class Root extends React.Component {
    constructor(props) {
        super(props);
        // Force initialization of the object.
        User.isAuthenticated();
        this.updateAuthentication = this.updateAuthentication.bind(this);
    }

    // This is called whenever the authentication state of a user is changed by a component. Additionally,
    // this is an example of intersibling communication with a common parent.
    updateAuthentication() {
        this.nav.updateAuthentication();
    }

    render() {
        return (
            <div>
                <Navigation ref={(component) => {
                    this.nav = component;
                }}/>
                <Switch>
                    {/*Authentication*/}
                    // See https://github.com/ReactTraining/react-router/issues/4627
                    <Route path="/user/login"
                           render={(props) => (
                               <Authentication {...props} updateAuthentication={this.updateAuthentication}/> )}/>

                    <Route path="/user/register" component={UserRegister}/>       
                    {/*Post handling*/}
                    <Route path="/users" component={UsersList}/>
                    <Route path="/matches" component={MatchesUsersList}/>
                    <Route path="/requests" component={RequestsList}/>
                    <Route path="/user/:id" component={UserDetail}/>
                    {/* Default route */}
                    <Route path="/" component={UsersList}/>
                </Switch>
            </div>
        );
    }
}

ReactDOM.render(
    <CookiesProvider>
        <I18nextProvider i18n={i18n}>
            <Router>
                <Root />
            </Router>
        </I18nextProvider>
    </CookiesProvider>
    , document.getElementById('root'));

