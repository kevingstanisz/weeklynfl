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
        axios.get('/games/1')
        .then(res => {
            console.log(res.data)
            dispatch(fetchGamesSuccess(res.data))
        })
        .catch(err => {
            console.log(err);
        });
    }
}

export const saveBets = (betsToSave) => {
    return dispatch => {
        console.log(betsToSave);

        let url = '/games/savebets';
        const authData = {
            // email: email,
            // password: password,
            // returnSecureToken: true
        };

        console.log('in action')
        console.log(betsToSave)

        axios.post(url, betsToSave)
        .then(response => {
            console.log(response)
            // const expirationDate = new Date(new Date().getTime() + response.data.expiresIn * 1000);
            // localStorage.setItem('token', response.data.idToken);
            // localStorage.setItem('expirationDate', expirationDate);
            // localStorage.setItem('userId', response.data.localId);
            // dispatch(authSuccess(response.data.idToken, response.data.localId));
            dispatch(getGames());
        })
        .catch(err => {
            console.log('hi')
            //dispatch(authFail(err.response.data.error));
        });
    }
}
