import React from 'react';
import Bet from '../Bet'
import classes from './CommonBet.module.css';



const CommonBet = props => {

    let betsOutput = null;
    let indBets = props.bet.betList;

    let wonBets = 0;
    let totalBetsGraded = 0; 
    let totalWon = 0;

    console.log(props.bet)

    if(indBets.length != 0) {
        betsOutput = indBets.map((game, index) => {
            let result = 'PENDING';

            if(game.betResult != -1) {
                result = '$' + game.totalWon
            }

            let betInWords = null;
            let readableLine = null;
            let readableJuice = null;
            
            switch(game.betType) {
                case 1:
                    readableLine = game.gameLine.sp1
                    if(readableLine >= 0) {
                        readableLine = '+' + readableLine
                    }

                    readableJuice = game.gameLine.sp1Odds
                    if(readableJuice > 0) {
                        readableJuice = '+' + readableJuice
                    }
                    betInWords = game.team1['teamAbr'] + ' ' + readableLine + ' (' + readableJuice + ')'
                break;
                case 2:
                    readableLine = game.gameLine.sp2
                    if(readableLine >= 0) {
                        readableLine = '+' + readableLine
                    }

                    readableJuice = game.gameLine.sp2Odds
                    if(readableJuice > 0) {
                        readableJuice = '+' + readableJuice
                    }
                    betInWords = game.team2['teamAbr'] + ' ' + readableLine + ' (' + readableJuice + ')'
                break;
                case 3:
                    readableJuice = game.gameLine.ml1
                    if(readableJuice >= 0) {
                        readableJuice = '+' + readableJuice
                    }

                    betInWords = game.team1['teamAbr'] + ' ML (' + readableJuice + ')'
                break;
                case 4:
                    readableJuice = game.gameLine.ml2
                    if(readableJuice >= 0) {
                        readableJuice = '+' + readableJuice
                    }

                    betInWords = game.team2['teamAbr'] + ' ML (' + readableJuice + ')'
                break;
                case 5:
                    readableLine = game.gameLine.over

                    readableJuice = game.gameLine.overOdds
                    if(readableJuice > 0) {
                        readableJuice = '+' + readableJuice
                    }
                    betInWords = game.team1['teamAbr'] + '/' + game.team2['teamAbr'] + ' O' + readableLine + ' (' + readableJuice + ')'
                break;
                case 6:
                    readableLine = game.gameLine.under

                    readableJuice = game.gameLine.underOdds
                    if(readableJuice > 0) {
                        readableJuice = '+' + readableJuice
                    }
                    betInWords = game.team1['teamAbr'] + '/' + game.team2['teamAbr'] + ' U' + readableLine + ' (' + readableJuice + ')'
                break;
                default:
                    betInWords = 'Cannot find bet'
            }

            let style = classes.Pending

            if(game.betResult == 0) {
                style = classes.Loss
                totalBetsGraded++
            }
            else if(game.betResult == 1){
                wonBets++
                totalBetsGraded++
                totalWon += game.totalWon
                style = classes.Win
            }

            return <td className = {style}>
                {betInWords}
                <br></br>
                {game.team1['teamAbr'] + '(' + game.gameLine.result1 + ') vs. ' + game.team2['teamAbr'] + '(' + game.gameLine.result2 + ')'}
                <br></br>
                {'Bet: $' + game.betValue}
                <br></br>
                {'Result: ' + result}
            </td>
        })
    }

  return (
        <React.Fragment>
            <h2>{props.bet.username}</h2>
            <table>
                <tr>
                    <td>
                        Graded Bets: {wonBets}/{totalBetsGraded}
                        <br></br>
                        Total: ${totalWon}
                    </td>
                    {betsOutput}
                </tr>
            </table>
        </React.Fragment>
  );
};

export default CommonBet;