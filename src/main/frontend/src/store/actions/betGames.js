import * as actionTypes from './actionTypes'
import axios from '../../axios-games';
import authHeader from '../../services/auth-header';

export const fetchGamesSuccess = (games) => {
    return{
        type: actionTypes.FETCH_GAMES_SUCCESS,
        games: games
    }
}

export const fetchBetsSuccess = (bets) => {
    return{
        type: actionTypes.FETCH_BETS_SUCCESS,
        bets: bets
    }
}

export const adminCallSuccess = (message) => {
    return{
        type: actionTypes.ADMIN_CALL_SUCCESS,
        message: message
    }
}

export const adminCallFail = (error) => {
    return{
        type: actionTypes.ADMIN_CALL_FAIL,
        error: error
    }
}

export const getGames = () => {
    return dispatch => {
        axios.get('/games/1', { headers: authHeader() })
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

        console.log('in action')
        console.log(betsToSave)

        axios.post(url, betsToSave, { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(getGames());
        })
        .catch(err => {
            console.log('error')
        });
    }
}

export const getResults = () => {
    console.log('get game results')
    return dispatch => {

        let url = '/games/getresults';

        axios.post(url, '',{ headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(adminCallSuccess('game results saved'));
        })
        .catch(err => {
            console.log('error' + err)
        });
    }
}

export const getBets = () => {
    console.log('get bets')
    return dispatch => {

        let url = '/games/bets/week/10';

        axios.get(url, { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(fetchBetsSuccess(response.data));
        })
        .catch(err => {
            console.log('error' + err)
        });
    }
}


export const gradeBets = () => {
    return dispatch => {

        let url = '/games/gradebets';

        axios.post(url, '', { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(adminCallSuccess('bets graded'));
        })
        .catch(err => {
            console.log('error' + err)
        });
    }
}

export const closeModal = () => {
    return{
        type: actionTypes.CLOSE_MODAL,
    }
}
