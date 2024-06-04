import { IonCol, IonGrid, IonLabel, IonRow, IonSelect, IonSelectOption, IonTitle } from "@ionic/react"
import React, { useContext, useEffect, useState } from "react";
import { SharedVariablesContext, StationData } from "../support/Variables";

const SelectContainer: React.FC = (() => {
  const {
    stations,
    setStations,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
  } = useContext(SharedVariablesContext);


  return (
      <IonGrid className='ion-padding'>
            <IonRow>
              <IonTitle>Where would you like to go?</IonTitle>
            </IonRow>
            <form>
              <IonRow className='ion-padding'>
                <IonCol>
                  <IonSelect id='selectOrigin' label="From" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setSelectedOrigin(e.detail.value)}>
                    {stations.map((item, index) => <IonSelectOption key={index}>{item.name}</IonSelectOption>)}
                  </IonSelect>
                </IonCol>
                <IonCol>
                  <IonSelect id='selectDestination' label="To" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setSelectedDestination(e.detail.value)}>
                    {stations.map((item, index) => <IonSelectOption key={index}>{item.name}</IonSelectOption>)}
                  </IonSelect>
                </IonCol>
              </IonRow>
            </form>
      </IonGrid>
  );
})

export default SelectContainer;