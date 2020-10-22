import React from 'react';


const betRequirements = props => {
  return (
    <React.Fragment>
        <br></br>
        {props.satisfied ? 'true' : 'false'}: {props.children}
    </React.Fragment>
  );
};

export default betRequirements;
