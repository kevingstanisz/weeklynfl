import React from 'react';


const game = props => {
  return (
    <tbody>
        <br></br>
        <tr>
            <td>{props.gameinfo['team1']}</td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'sp1'} value="sp1" checked = {props.checkedSp1} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'sp1'}>{props.gameinfo['sp1']} ({props.gameinfo['sp1Odds']})</label>
                <input id="sp1Value" type="number" value={props.valueSp1} onChange={props.betChange}></input>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'ml1'} value="ml1" checked = {props.checkedMl1} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'ml1'}>{props.gameinfo['ml1']}</label>
                <input id="ml1Value" type="number" value={props.valueMl1} onChange={props.betChange}></input>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'over'} value="over" checked = {props.checkedOver} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'over'}>{'O' + props.gameinfo['over']} ({props.gameinfo['overOdds']})</label>
                <input id="overValue" type="number" value={props.valueOver} onChange={props.betChange}></input>
            </td>
        </tr>
        <tr>
        <td>{props.gameinfo['team2']}</td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'sp2'} value="sp2" checked = {props.checkedSp2} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'sp2'}>{props.gameinfo['sp2']} ({props.gameinfo['sp2Odds']})</label>
                <input id="sp2Value" type="number" value={props.valueSp2} onChange={props.betChange}></input>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'ml2'} value="ml2" checked = {props.checkedMl2} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'ml2'}>{props.gameinfo['ml2']}</label>
                <input id="ml2Value" type="number" value={props.valueMl2} onChange={props.betChange}></input>
            </td>
            <td>
                <input type="checkbox" id="checkbox" name={props.gameinfo['id'] + 'under'} value="under" checked = {props.checkedUnder} onChange={props.betChange}/>
                <label htmlFor={props.gameinfo['id'] + 'under'}>{'U' + props.gameinfo['under']} ({props.gameinfo['underOdds']})</label>
                <input id="underValue" type="number" value={props.valueUnder} onChange={props.betChange}></input>
            </td>
        </tr>
    </tbody>
  );
};

export default game;
