import { IonBackdrop, IonButton, IonCol, IonContent, IonDatetime, IonDatetimeButton, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonModal, IonPage, IonRow, IonSearchbar, IonSelect, IonSelectOption, IonTitle, IonToolbar } from '@ionic/react';
import './Tab1.css';
import React, { useContext, useEffect, useState } from 'react';
import Header from '../support/Header';
import SelectContainer from '../components/SelectContainer';
import TimeContainer from '../components/TimeContainer';
import { arrowBackCircle, arrowBackOutline, arrowForwardCircle } from 'ionicons/icons';
import APIWrapper from '../components/APIWrapper';
import ReturnTripContainer from '../components/ReturnTripContainer';
import SearchOptionsContainer from '../components/SearchOptionsContainer';
import { SharedVariablesContext } from '../support/Variables';

const Tab1: React.FC = () => { 
  const {
    connections,
    setConnections,
    possible,
    setPossible,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
    results, 
    setResults,
  } = useContext(SharedVariablesContext);

  const [selectedContainer, setSelectedContainer] = useState(1);

  const handlePreviousContainer = () => {
    setSelectedContainer((prev) => (prev === 1 ? 4 : prev - 1));
  };

  const handleNextContainer = () => {
    setSelectedContainer((prev) => (prev === 4 ? 1 : prev + 1));
  };
  
  const fetchOrganizations = async () => {
    const response = await APIWrapper.fetchOrganizations()
    if (response){
      const data = await response.json();
      setConnections(data)
      setPossible(data)
    }
  }

  useEffect(()=>{
    fetchOrganizations()
  },[])
  return (
    <IonPage>
      <IonHeader>
        <Header name='Homepage'/>
      </IonHeader>
      <IonContent fullscreen>
        <IonRow className='ion-padding'>
          <IonCol size='1' style={{ display: "flex", alignItems: "center" }}>
            {selectedContainer > 1 ? (
              <IonButton onClick={handlePreviousContainer} id='prevContainer'>
                <IonIcon icon={arrowBackCircle} size="large"></IonIcon>
              </IonButton>
            ):(<></>)}
          </IonCol>
          <IonCol>
            {selectedContainer === 1 && <SelectContainer />}
            {selectedContainer === 2 && <TimeContainer/>}
            {selectedContainer === 3 && <ReturnTripContainer />}
            {selectedContainer === 4 && <SearchOptionsContainer/>}
          </IonCol>
          <IonCol size='1' style={{ display: "flex", alignItems: "center" }}>
            {selectedContainer < 4 && selectedOrigin != "" && selectedDestination != "" ? (
              <IonButton onClick={handleNextContainer} id='nextContainer'>
                <IonIcon icon={arrowForwardCircle} size="large"></IonIcon>
              </IonButton>
            ):(<></>)}
          </IonCol>
        </IonRow>
        <IonRow>
          {results.length != 0 ? (
              <IonCol>
                Results
              </IonCol>
          ):(<></>)}
        </IonRow>
      </IonContent>
    </IonPage>
  );
};

export default Tab1;
