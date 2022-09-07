import React from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import SiteNav from './SiteNav';
import {backend} from './Constants';

const { Component } = React

class Notifications extends Component {
  constructor(props){
    super(props)
    this.state = {
      content: [],
      userId: this.props.userId,
    }
    fetch(backend + '/notification', {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type':'application/json'},
        body: JSON.stringify({ id: this.state.userId })
       })
        .then(response => response.json())
        .then(data => {
            var tmp = [];
            for (var i=0; i < data.result.length; i++) {
              tmp.push(JSON.parse(data.result[i].chars));
            }
            this.setState({ content: tmp });
        });
  }

  render() {
    return (
        <>
        <SiteNav userId={this.state.userId}/>
        <div style={{textAlign: "center", marginTop: "5px", marginLeft: "30px", marginRight: "30px"}}>
            <h1 style={{marginBottom: "5px"}}>Powiadomienia</h1>
            {this.state.content.length === 0
            ? <div>Nie masz żadnych powiadomień</div>
            : this.state.content.map((notification, key) => {return <p key={key}>{notification.message}</p>})}
        </div>
        </>
    )
  }
}

export default Notifications;