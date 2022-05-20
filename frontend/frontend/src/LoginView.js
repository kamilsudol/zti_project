import React from 'react';
import ReactDOM from 'react-dom/client';

import './Login.css';

const { Component } = React

class EntryPage extends Component {
  constructor(props){
    super(props)
    this.state = {
      currentView: "signUp"
    }
  }

  changeView = (view) => {
    this.setState({
      currentView: view
    })
  }

  registerEvent = (event) => {
    console.log(document.getElementById("username").value);
    fetch('http://localhost:8080/test')
        .then(response => response.json())
        .then(data => this.setState({ postId: data.id }));
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
            <button>Zaloguj się</button>
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
// ReactDOM.render(<EntryPage/>, document.getElementById("app"))