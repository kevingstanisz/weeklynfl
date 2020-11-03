import React, { Component } from 'react';
import { Redirect, Route, Switch } from 'react-router-dom';
import logo from './logo.svg';
import './App.css';

import Layout from './hoc/Layout/Layout';

import BetGames from './containers/BetGames/BetGames';

function App() {
  return (
    <div>
      <Layout>
        <Switch>
          <Route path="/" exact component={BetGames} />
          <Redirect to="/" />
        </Switch>
      </Layout>
    </div>
  );
}

export default App;
