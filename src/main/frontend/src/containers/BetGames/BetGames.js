import React, { useState, useEffect, useCallback } from 'react';
import { connect, useDispatch, useSelector } from 'react-redux';
import * as actions from '../../store/actions/index';
import classes from './BetGames.module.css';

import axios from '../../axios-games';
import withErrorHandler from '../../hoc/withErrorHandler/withErrorHandler';
import Spinner from '../../components/UI/Spinner/Spinner';
import Game from '../../components/Game/Game';
import BetRequirements from '../../components/Bet/BetRequirements/BetRequirements'
import { updateObject, checkValidity } from '../../shared/utility';
import game from '../../components/Game/Game';
import { fetchGamesSuccess } from '../../store/actions/betGames';
import lockWeek from '../../helpers/calcWeek'

const BetGames = props => {
  const [stateBets, setBets] = useState([]);
  const [initialLoad, setinitialLoad] = useState([]);

  const [betRequirements, setBetRequirements] = useState({
    numberBets: false,
    betTotal: false, 
    minimumBet: false
  })

  const dispatch = useDispatch();
  const onGetGames = () => dispatch(actions.getGames());
  const onSaveBets = (betsToSave) => dispatch(actions.saveBets(betsToSave));

  const games = useSelector(state => {
    return state.betReducer.games;
  }); 

  useEffect(() => {
    onGetGames()
  }, []);

  useEffect(() => {
    setBets(games)

    let alreadyCompleted = false;
    for(let i = 0; i < games.length; i++){
      if((games[i]['bets']['sp1Value'] > 0) || (games[i]['bets']['sp2Value'] > 0) || 
      (games[i]['bets']['ml1Value'] > 0) || (games[i]['bets']['ml2Value'] > 0) || 
      (games[i]['bets']['overValue'] > 0) || (games[i]['bets']['underValue'] > 0)) {
        alreadyCompleted = true; 
        break;
      }
    }

    setinitialLoad(alreadyCompleted)
  }, [games]);




  // setBets(games)
  // console.log('bets stae')
    console.log('state result:')
   console.log(stateBets);

  const inputChangedHandler = ( event, gameId, index ) => {
    setinitialLoad(false)
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

    let game = null;
    let bets = null;
    let betTotal = 0;
    let minimumBet = 100;
    let numberOfBets = 0;

    for(game of Object.values(finalBetForm)){
      bets = game['bets']

      if(bets.ml1) {
        betTotal += parseInt(bets.ml1Value);
        minimumBet = minimumBet < bets.ml1Value ? minimumBet : bets.ml1Value;
        numberOfBets++;
      }
      if(bets.ml2) {
        betTotal += parseInt(bets.ml2Value);
        minimumBet = minimumBet < bets.ml2Value ? minimumBet : bets.ml2Value;
        numberOfBets++;
      }
      if(bets.sp1) {
        betTotal += parseInt(bets.sp1Value);
        minimumBet = minimumBet < bets.sp1Value ? minimumBet : bets.sp1Value;
        numberOfBets++;
      }
      if(bets.sp2) {
        betTotal += parseInt(bets.sp2Value);
        minimumBet = minimumBet < bets.sp2Value ? minimumBet : bets.sp2Value;
        numberOfBets++;
      }
      if(bets.over) {
        betTotal += parseInt(bets.overValue);
        minimumBet = minimumBet < bets.overValue ? minimumBet : bets.overValue;
        numberOfBets++;
      }
      if(bets.under) {
        betTotal += parseInt(bets.underValue);
        minimumBet = minimumBet < bets.underValue ? minimumBet : bets.underValue;
        numberOfBets++;
      }
    }

    setBetRequirements({
      numberBets: numberOfBets > 2 ? true : false,
      betTotal: betTotal == 100 ? true : false, 
      minimumBet: minimumBet >= 20 ? true : false
    })

    setBets(Object.values(finalBetForm));
    
  }

  const submitHandler = (event) => {
    event.preventDefault();
    const betForm = {stateBets}
    onSaveBets(stateBets)
  }

  let gamesOutput = null;

  if(stateBets.length != 0) {
    gamesOutput = stateBets.map((game, index) => {
      return <Game 
        key={index} 
        gameinfo={game} 
        locked = {lockWeek()}
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
      <BetRequirements satisfied = {betRequirements.numberBets || initialLoad}>Minimum 3 Bets</BetRequirements>
      <BetRequirements satisfied = {betRequirements.minimumBet || initialLoad}>Minimum 20 per Bet</BetRequirements>
      <BetRequirements satisfied = {betRequirements.betTotal || initialLoad}>Bets total to 100</BetRequirements>
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
      <input type="submit" value="Submit"  disabled = {!(betRequirements.betTotal && betRequirements.minimumBet && betRequirements.numberBets)}></input>
      </form>
    </React.Fragment>
  );
};
  
export default withErrorHandler(BetGames, axios);