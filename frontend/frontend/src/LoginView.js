import React from 'react';
import ReactDOM from 'react-dom/client';
import Home from './Home';
import {backend} from './Constants';
import './Login.css';

const { Component } = React
const root = ReactDOM.createRoot(document.getElementById('root'));

class EntryPage extends Component {
  constructor(props){
    super(props)
    this.state = {
      currentView: "signUp"
    }
    fetch(backend)
        .then(response => response)
        .then(data => {
        });
  }

  changeView = (view) => {
    this.setState({
      currentView: view
    })
  }


  loginEvent = (event) => {
    event.preventDefault();
    var user = document.getElementById("username").value;
    var passwd = document.getElementById("password").value;
    if (user !== "" && passwd !== ""){
      fetch(backend + '/user', {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type':'application/json'},
        body: JSON.stringify({ user: user, password: passwd, type: "login" })
       })
        .then(response => response.json())
        .then(data => {
          if(data.result.valueType === "TRUE"){
            root.render(<Home userId={data.id.chars}/>);
          } else {
            alert("Błędny login lub hasło!");
          }
        });
    }
  }

  registerEvent = (event) => {
    event.preventDefault();
    var user = document.getElementById("username").value;
    var passwd = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    if (user !== "" && passwd !== "" && email !== ""){
      fetch(backend + '/user', {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type':'application/json'},
        body: JSON.stringify({ user: user, password: passwd, email: email, type: "register" })
       })
        .then(response => response.json())
        .then(data => {
          if(data.result.valueType === "TRUE"){
            root.render(<Home userId={data.id.chars}/>);
          } else {
            alert("Użytkownik o zadanym loginie już istnieje!");
          }
        });
    }
  }

  currentView = () => {
    switch(this.state.currentView) {
      case "signUp":
        return (
          <form>
            <h2>Zarejestruj się!</h2>
            <fieldset>
              <legend>Rejestracja</legend>
              <ul>
                <li>
                  <label for="username">Nazwa użytkownika:</label>
                  <input type="text" id="username" required/>
                </li>
                <li>
                  <label for="email">E-mail:</label>
                  <input type="email" id="email" required/>
                </li>
                <li>
                  <label for="password">Hasło:</label>
                  <input type="password" id="password" required/>
                </li>
              </ul>
            </fieldset>
            <button onClick={this.registerEvent}>Zatwierdź</button>
            <button type="button" onClick={ () => this.changeView("logIn")}>Masz już konto?</button>
          </form>
        )
        break
      case "logIn":
        return (
          <form>
            <h2>Zaloguj się!</h2>
            <fieldset>
              <legend>Logowanie</legend>
              <ul>
                <li>
                  <label for="username">Nazwa użytkownika:</label>
                  <input type="text" id="username" required/>
                </li>
                <li>
                  <label for="password">Hasło:</label>
                  <input type="password" id="password" required/>
                </li>
              </ul>
            </fieldset>
            <button onClick={this.loginEvent}>Zaloguj się</button>
            <button type="button" onClick={ () => this.changeView("signUp")}>Utwórz nowe konto</button>
          </form>
        )
        break
      default:
        break
    }
  }


  render() {
    return (
      <section id="entry-page">
        {this.currentView()}
      </section>
    )
  }
}

export default EntryPage;