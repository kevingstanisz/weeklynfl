import React from 'react';


const game = props => {

    console.log(props)

  return (
    <tbody>
        <br></br>
        <tr>
            <td>{props.gameinfo['team1']}</td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'sp1'} value="sp1"  checked = {props.checkedSp1} onChange={props.checkboxSp1}/>
                <label htmlFor={props.gameinfo['id'] + 'sp1'}>{props.gameinfo['sp1']} ({props.gameinfo['sp1Odds']})</label>
                <input id="sp1Value" type="number" value={props.valueSp1}></input>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'ml1'} value="ml1"/>
                <label htmlFor={props.gameinfo['id'] + 'ml1'}>{props.gameinfo['ml1']}</label>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'over'} value="over"/>
                <label htmlFor={props.gameinfo['id'] + 'over'}>{'O' + props.gameinfo['over']} ({props.gameinfo['overOdds']})</label>
            </td>
        </tr>
        <tr>
        <td>{props.gameinfo['team2']}</td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'sp2'} value="sp2"/>
                <label htmlFor={props.gameinfo['id'] + 'sp2'}>{props.gameinfo['sp2']} ({props.gameinfo['sp2Odds']})</label>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'ml2'} value="ml2"/>
                <label htmlFor={props.gameinfo['id'] + 'ml2'}>{props.gameinfo['ml2']}</label>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'under'} value="under"/>
                <label htmlFor={props.gameinfo['id'] + 'under'}>{'U' + props.gameinfo['under']} ({props.gameinfo['underOdds']})</label>
            </td>
        </tr>
    </tbody>
  );
};

export default game;
