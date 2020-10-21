import React, { useState, useEffect, useCallback } from 'react';
import { connect, useDispatch, useSelector } from 'react-redux';
import * as actions from '../../store/actions/index';

import axios from '../../axios-games';
import withErrorHandler from '../../hoc/withErrorHandler/withErrorHandler';
import Spinner from '../../components/UI/Spinner/Spinner';
import Game from '../../components/Game/Game';
import { updateObject, checkValidity } from '../../shared/utility';

const BetGames = props => {
  const [stateBets, setBets] = useState([]);

  const dispatch = useDispatch();
  const onGetGames = () => dispatch(actions.getGames());

  const games = useSelector(state => {
    return state.games;
  }); 

  useEffect(() => {
    onGetGames()
  }, []);

  useEffect(() => {
    // console.log('ran here')
    // console.log(games)
    setBets(games)
  }, [games]);




  // setBets(games)
  // console.log('bets stae')
    console.log('state result:')
   console.log(stateBets);

  const inputChangedHandler = ( event, gameId, index ) => {
    console.log(event.target.value)

    let updatedBet = null;
    if(event.target.id == 'checkbox') {
        updatedBet =  {
          ...stateBets[index]['bets'],
          [event.target.value] : event.target.checked,
          [event.target.value + 'Value'] : event.target.checked ? stateBets[index]['bets'][event.target.value + 'Value'] : 0
        };
    }
    else {
      updatedBet =  {
        ...stateBets[index]['bets'],
        [event.target.id] : event.target.value
      };
    }

    const updatedBetForm = {
      ...stateBets[index],
      ['bets']: updatedBet
    }

    const finalBetForm = {
      ...stateBets,
      [index]: updatedBetForm
    }

    setBets(Object.values(finalBetForm));
    
  }

  const submitHandler = (event) => {
    event.preventDefault();



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

  if(stateBets.length != 0) {
    console.log('before it fails')
    console.log(stateBets)
    gamesOutput = stateBets.map((game, index) => {
      return <Game 
        key={index} 
        gameinfo={game} 
        betChange={( event ) => inputChangedHandler( event, game['id'], index )}
        checkedSp1 = {game['bets'] != null ? game['bets']['sp1'] : false}
        valueSp1= {game['bets'] != null ? game['bets']['sp1Value'] : 0}
        checkedSp2 = {game['bets'] != null ? game['bets']['sp2'] : false}
        valueSp2= {game['bets'] != null ? game['bets']['sp2Value'] : 0}
        checkedMl1 = {game['bets'] != null ? game['bets']['ml1'] : false}
        valueMl1= {game['bets'] != null ? game['bets']['ml1Value'] : 0}
        checkedMl2 = {game['bets'] != null ? game['bets']['ml2'] : false}
        valueMl2 = {game['bets'] != null ? game['bets']['ml2Value'] : 0}
        checkedOver = {game['bets'] != null ? game['bets']['over'] : false}
        valueOver= {game['bets'] != null ? game['bets']['overValue'] : 0}
        checkedUnder = {game['bets'] != null ? game['bets']['under'] : false}
        valueUnder = {game['bets'] != null ? game['bets']['underValue'] : 0}
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