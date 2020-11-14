import * as actionTypes from '../actions/actionTypes';

const initialState = {
    games: [],
    modal: false,
    message: '',
    error: ''
};

const reducer = ( state = initialState, action ) => {
    switch ( action.type ) {
        case actionTypes.FETCH_GAMES_SUCCESS:
            return{
                ...state,
                games: action.games
            };

        case actionTypes.ADMIN_CALL_SUCCESS:
            return{
                ...state,
                modal: true,
                message: action.message
            };

        case actionTypes.ADMIN_CALL_FAIL:
            return{
                ...state,
                modal: true,
                error: action.error
            };

        case actionTypes.CLOSE_MODAL:
            return{
                ...state,
                modal: false,
                error: '',
                message: ''
            };

        default:
            return state;
    }
};

export default reducer;