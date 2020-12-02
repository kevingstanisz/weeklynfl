import * as actionTypes from './actionTypes'
import axios from '../../axios-games';
import authHeader from '../../services/auth-header';

export const fetchLeagueInfoSuccess = (leagueInfo) => {
    return{
        type: actionTypes.FETCH_LEAGUE_INFO,
        weeks: leagueInfo.weeks,
        users: leagueInfo.users
    }
}

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

export const getResults = (week) => {
    console.log('get game results')
    return dispatch => {

        let url = '/games/getresults';

        console.log('WEEK IN GET RESULTS' + week)

        let body = {weekNumberFromFrontend: week}

        axios.post(url, body, { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(adminCallSuccess('game results saved'));
        })
        .catch(err => {
            console.log('error' + err)
        });
    }
}

export const getLeagueInfo = () => {
    console.log('hello league info')
    return dispatch => {
        axios.get('games/leagueinfo', { headers: authHeader() })
        .then(res => {
            console.log(res.data)
            dispatch(fetchLeagueInfoSuccess(res.data))
        })
        .catch(err => {
            console.log(err);
        });
    }
}

export const getBets = (id) => {
    console.log('get bets')
    return dispatch => {

        console.log(id)

        let url = null;
        if(isNaN(id)) {
            url = '/games/bets/user/' + id;
        }
        else {
            url = '/games/bets/week/' + id;
        }

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


export const gradeBets = (week) => {
    return dispatch => {

        let url = '/games/gradebets';

        let body = {weekNumberFromFrontend: week}

        axios.post(url, body, { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(adminCallSuccess('bets graded'));
        })
        .catch(err => {
            console.log('error' + err)
        });
    }
}

export const saveLines = () => {
    return dispatch => {

        let url = '/games/savelines';

        axios.post(url, '', { headers: authHeader() })
        .then(response => {
            console.log(response)
            dispatch(adminCallSuccess('lines saved'));
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
