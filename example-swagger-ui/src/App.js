import React, { Component } from 'react';
import SwaggerUI from "swagger-ui";
import 'swagger-ui/dist/swagger-ui.css'

class App extends Component {
    componentDidMount() {
    SwaggerUI({
      dom_id: "#ui",
	url: "http://localhost:3000/schema.json"
    })
  }
  render() {
    return (
      <div className="App">
        
        <div id="ui" />
      </div>
    );
  }
}

export default App;
