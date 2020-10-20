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

  const inputChangedHandler = ( event, gameId ) => {
    console.log(gameId)
    console.log(event.target.checked)
    console.log(event.target.value)
  }

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

  let gamesOutput = null;

  let trythis = null;
  if(games.length != 0) {
    console.log(games)
    gamesOutput = games.map(game => {
      if(game['bets'] != null) {
        console.log('meettooo')
        console.log(game)
      }
      return <Game 
        key={game['id']} 
        gameinfo={game} 
        checkboxSp1={( event ) => inputChangedHandler( event, game['id'] )}
        checkedSp1 = {game['bets'] != null ? game['bets']['sp1'] : false}
        valueSp1= {game['bets'] != null ? game['bets']['sp1Value'] : 0}
        >
      </Game>
    })
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
      {gamesOutput}
      </table>
      <input type="submit" value="Submit"></input>
      </form>
    </React.Fragment>
  );
};
  
export default withErrorHandler(BetGames, axios);