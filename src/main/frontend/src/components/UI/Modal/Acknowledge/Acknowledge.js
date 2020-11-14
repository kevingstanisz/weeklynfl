import React, {useState} from 'react';
import Button from '../../Button/Button';

const Acknowledge = props => {

    return(
        <React.Fragment>
            {props.children}
            <br></br>
            <Button clicked={props.modalClosed} btnType="Success">OK</Button>
        </React.Fragment>
    ); 
};

export default Acknowledge;