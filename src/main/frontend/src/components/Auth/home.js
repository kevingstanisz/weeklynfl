import React, { Component } from "react";

import UserService from "../../services/user.service";
import BetGames from "../../containers/BetGames/BetGames";

export default class Home extends Component {
  constructor(props) {
    super(props);

    this.state = {
      content: ""
    };
  }

  componentDidMount() {
    UserService.getPublicContent().then(
      response => {
        this.setState({
          content: response.data
        });
      },
      error => {
        this.setState({
          content:
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        });
      }
    );
  }

  render() {
    return (
      <React.Fragment>
          <BetGames></BetGames>
      </React.Fragment>
    );
  }
}