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
import { useParams } from "react-router";
import calcWeek from "../../helpers/calcWeek"

const ShowBets = props => {
    let { id } = useParams();
    console.log(id)

    const [stateBets, setBets] = useState([]);

    const dispatch = useDispatch();
    const onGetBets = (id) => dispatch(actions.getBets(id));

    const bets = useSelector(state => {
        return state.betReducer.bets;
    }); 

    useEffect(() => {
        onGetBets(id)
    }, [id]);

    useEffect(() => {
        setBets(bets)
      }, [bets]);

    console.log(bets)

    let betsOutput = null;

    if(stateBets != null){
        betsOutput = stateBets.map((bet, i) => {
            let header = bet.weekNumber != null ? 'Week ' + bet.weekNumber : bet.username;
            return(
                <CommonBets key = {i}  bet = {bet} header = {header}>
                </CommonBets>
            )
        })
    
    }

    let header = null; 
    if(isNaN(id)) {
        header = id + `'s Picks`
    }
    else {
        header = 'Week ' + id + ' Picks'
    }

    return (
        <React.Fragment>
            <h1>{header}</h1>
            {betsOutput}
        </React.Fragment>
    );
};
  
export default withErrorHandler(ShowBets, axios);