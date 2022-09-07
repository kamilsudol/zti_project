import React from 'react';
import ReactDOM from 'react-dom/client';
import {Navbar, Container, Nav} from 'react-bootstrap';
import Home from './Home';
import Reservation from './Reservation';
import Positions from './Positions';
import 'bootstrap/dist/css/bootstrap.min.css';
import EntryPage from './LoginView';
import Notifications from './Notifications';

const { Component } = React
var root = ReactDOM.createRoot(document.getElementById('root'));

class SiteNav extends Component {
  constructor(props){
    super(props)
    this.state = {
      userId: this.props.userId,
      currentView: "signUp"
    }
  }

  renderHome = (event) => {
    root.render(<Home userId={this.state.userId}/>);
  }

  renderReservation = (event) => {
    root.render(<Reservation userId={this.state.userId}/>);
  }

  renderLogout = (event) => {
    root.render(<EntryPage/>);
  }

  renderPositions = (event) => {
    root.render(<Positions userId={this.state.userId}/>);
  }

  renderNotifications = (event) => {
    root.render(<Notifications userId={this.state.userId}/>);
  }

  render() {
    return (
        <Navbar bg="light" expand="lg">
        <Container>
            <Navbar.Brand>B00ker ;]</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
            <Nav className="me-auto">
                <Nav.Link onClick={this.renderHome}>Lista książek</Nav.Link>
                <Nav.Link onClick={this.renderPositions}>Twoje pozycje</Nav.Link>
                <Nav.Link onClick={this.renderReservation}>Twoje rezerwacje</Nav.Link>
                <Nav.Link onClick={this.renderNotifications}>Powiadomienia</Nav.Link>
                <Nav.Link className='navbar-right' onClick={this.renderLogout}>Wyloguj</Nav.Link>
            </Nav>
            </Navbar.Collapse>
        </Container>
        </Navbar>
    )
  }
}

export default SiteNav;