import * as actionTypes from '../actions/actionTypes';

const initialState = {
    games: []
};

const reducer = ( state = initialState, action ) => {
    switch ( action.type ) {
        case actionTypes.FETCH_GAMES_SUCCESS:
            return{
                ...state,
                games: action.games
            };

        default:
            return state;
    }
};

export default reducer;