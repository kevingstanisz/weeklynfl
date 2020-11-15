import React, { useState, useEffect, useCallback } from 'react';
import { connect, useDispatch, useSelector } from 'react-redux';
import * as actions from '../../store/actions/index';
import classes from './ShowBets.module.css';

import axios from '../../axios-games';
import withErrorHandler from '../../hoc/withErrorHandler/withErrorHandler';
import Spinner from '../../components/UI/Spinner/Spinner';
import Game from '../../components/Game/Game';
import BetRequirements from '../../components/Bet/BetRequirements/BetRequirements'
import { updateObject, checkValidity } from '../../shared/utility';
import game from '../../components/Game/Game';
import { fetchGamesSuccess } from '../../store/actions/betGames';
import CommonBets from '../../components/Bet/CommonBet/CommonBet'

const ShowBets = props => {
    const [stateBets, setBets] = useState([]);

    const dispatch = useDispatch();
    const onGetBets = () => dispatch(actions.getBets());

    const bets = useSelector(state => {
        return state.betReducer.bets;
    }); 

    useEffect(() => {
        onGetBets()
    }, []);

    useEffect(() => {
        setBets(bets)
      }, [bets]);

    console.log(bets)

    let betsOutput = null;

    if(stateBets != null){
        console.log('hi')

            betsOutput = stateBets.map((bet) => {
            return <CommonBets bet = {bet}>
            </CommonBets>
            })
        
    }

    return (
        <React.Fragment>
            {betsOutput}
        </React.Fragment>
    );
};
  
export default withErrorHandler(ShowBets, axios);