import React, { useState, useEffect, useCallback } from 'react';
import { connect, useDispatch, useSelector } from 'react-redux';
import * as actions from '../../store/actions/index';

import axios from '../../axios-games';
import withErrorHandler from '../../hoc/withErrorHandler/withErrorHandler';
import Spinner from '../../components/UI/Spinner/Spinner';
import Game from '../../components/Game/Game';

const BetGames = props => {
  const dispatch = useDispatch();
  const onGetGames = () => dispatch(actions.getGames());

  const games = useSelector(state => {
    return state.games;
  }); 

  useEffect(() => {
    onGetGames()
  }, []);

  const submitHandler = (event) => {
    event.preventDefault();

    console.log('helllooo')

    

    // const formData = {};
    // for (let formElementIdentifier in authForm) {
    //     formData[formElementIdentifier] = authForm[formElementIdentifier].value;
    // }
    // const results = {
    //     score: finalScore,
    //     name: formData.name
    // }

    // const updatedControls = updateObject( authForm, {
    //     ['name']: updateObject( authForm['name'], {
    //         lockButton: true
    //     } )
    // } );
    // setAuthForm(updatedControls);
    
    // onCheckUsername(results)
  }


  let trythis = null;
  if(games.length != 0) {
    console.log(games)
    trythis = <Game key={games[0]['id']} gameinfo={games[0]}></Game>
  }

  return (
    <React.Fragment>
      <h1>hello!!</h1>
      <form onSubmit={submitHandler}>
      <table>
        <tbody>
          <tr>
            <th>Team</th>
            <th>Spread</th>
            <th>Moneyline</th>
            <th>Total</th>
          </tr>
        </tbody>
      {trythis}
      </table>
      <input type="submit" value="Submit"></input>
      </form>
    </React.Fragment>
  );
};
  
export default withErrorHandler(BetGames, axios);