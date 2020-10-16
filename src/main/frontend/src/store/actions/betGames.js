import * as actionTypes from './actionTypes'
import axios from '../../axios-games';

export const fetchGamesSuccess = (games) => {
    return{
        type: actionTypes.FETCH_GAMES_SUCCESS,
        games: games
    }
}

export const getGames = () => {
    return dispatch => {
        axios.get('/games')
        .then(res => {
            console.log(res.data)
            dispatch(fetchGamesSuccess(res.data))
        })
        .catch(err => {
            console.log(err);
        });
    }
}
