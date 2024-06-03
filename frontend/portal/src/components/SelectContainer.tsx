import { IonCol, IonGrid, IonLabel, IonRow, IonSelect, IonSelectOption, IonTitle } from "@ionic/react"
import React, { useContext, useEffect, useState } from "react";
import { SharedVariablesContext } from "../support/Variables";
import APIWrapper from "./APIWrapper";

const SelectContainer: React.FC = (() => {
  const {
    stations,
    setStations,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
  } = useContext(SharedVariablesContext);

  const [origin, setOrigin] = useState<string | null>(null);
  const [destination, setDestination] = useState<string | null>(null);

  useEffect (()=>{
  },[])
  
  return (
      <IonGrid className='ion-padding'>
            <IonRow>
              <IonTitle>Where would you like to go?</IonTitle>
            </IonRow>
            <form>
              <IonRow className='ion-padding'>
                <IonCol>
                  <IonSelect id='selectOrigin' label="From" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setOrigin(e.detail.value)}>
                    {stations.map((item, index) => <IonSelectOption key={index}>{item.name}</IonSelectOption>)}
                  </IonSelect>
                </IonCol>
                <IonCol>
                    {stations.map((item, index) => <IonLabel key={index}>{item.name}</IonLabel>)}
                  <IonSelect id='selectDestination' label="To" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setDestination(e.detail.value)}>
                  </IonSelect>
                </IonCol>
              </IonRow>
            </form>
      </IonGrid>
  );
})

export default SelectContainer;