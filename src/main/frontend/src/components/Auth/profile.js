import React, { useState, useEffect, useCallback } from 'react';
import { Redirect } from 'react-router-dom';
import { connect, useDispatch, useSelector } from 'react-redux';
import Modal from "../UI/Modal/Modal"
import Acknowledge from "../UI/Modal/Acknowledge/Acknowledge"
import withErrorHandler from '../../hoc/withErrorHandler/withErrorHandler';
import axios from '../../axios-games';
import * as actions from '../../store/actions/index';
import Button from '../UI/Button/Button'
import calcWeek from "../../helpers/calcWeek"


const Profile = props => {
  const dispatch = useDispatch();
  const onCloseModal = () => dispatch(actions.closeModal());
  const onSaveGameResults = (week) => dispatch(actions.getResults(week));
  const onSaveBets = (week) => dispatch(actions.gradeBets(week));
  const onSaveLines = () => dispatch(actions.saveLines());

  const currentUser = useSelector(state => {
    return state.auth.user;
  }); 

  const showModal = useSelector(state => {
    return state.betReducer.modal;
  }); 

  const modalMessage = useSelector(state => {
    return state.betReducer.message;
  }); 

  const modalError = useSelector(state => {
    return state.betReducer.error;
  }); 

  const closeModalHandler = () => {
    onCloseModal();
    console.log('closemodal')
  }

  const saveGameResults = () => {
    onSaveGameResults(calcWeek());
  }

  const saveBets = () => {
    onSaveBets(calcWeek());
  }

  const saveLines = () => {
    onSaveLines();
  }

  const saveLastWeekGameResults = () => {
    onSaveGameResults(calcWeek() - 1);
  }

  const saveLastWeekBets = () => {
    onSaveBets(calcWeek() - 1);
  }

  if (!currentUser) {
    return <Redirect to="/login" />;
  }

  return (
    <div className="container">
      <header className="jumbotron">
        <h3>
          <strong>{currentUser.username}</strong> Profile
        </h3>
      </header>
      <p>
        <strong>Token:</strong> {currentUser.accessToken.substring(0, 20)} ...{" "}
        {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
      </p>
      <p>
        <strong>Id:</strong> {currentUser.id}
      </p>
      <p>
        <strong>Email:</strong> {currentUser.email}
      </p>
      <strong>Authorities:</strong>
      <ul>
        {currentUser.roles &&
          currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
      </ul>
      <ul>
        <Button clicked={saveGameResults} btnType="Success">Save Game Results</Button>
        <Button clicked={saveBets} btnType="Success">Grade Bets</Button>
      </ul>
      <ul>
        <Button clicked={saveGameResults} btnType="Success">Save Last Week's Game Results</Button>
        <Button clicked={saveBets} btnType="Success">Grade Last Week's Bets</Button>
      </ul>
      <ul>
        <Button clicked={saveLines} btnType="Success">Save Lines</Button>
      </ul>
      
      <Modal show = {showModal}><Acknowledge  modalClosed = {closeModalHandler}>{modalMessage}</Acknowledge></Modal>
    </div>
  );
}


export default withErrorHandler(Profile, axios);