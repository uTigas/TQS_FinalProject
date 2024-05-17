import { IonButton, IonCol, IonGrid, IonIcon, IonRow, IonSelect, IonSelectOption, IonTitle } from "@ionic/react"
import { arrowForwardCircle, handRight, search } from "ionicons/icons";
import TimeContainer from "./TimeContainer";
import React, { useContext, useEffect, useState } from "react";
import { SharedVariablesContext } from "../support/SharedVariablesContext";
import APIWrapper from "./APIWrapper";

const SelectContainer: React.FC = (() => {
  const {
    connections,
    setConnections,
    possible,
    setPossible,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
  } = useContext(SharedVariablesContext);

  const [origin, setOrigin] = useState<string | null>(null);
  const [destination, setDestination] = useState<string | null>(null);


  const updatePossibilities = async () => {
    const response = await APIWrapper.fetchOrganizations(origin, destination)
    if (response)
      setPossible(await response.json()) 
  } 

  useEffect (()=>{
    updatePossibilities()
  },[origin, destination])
  
  return (
      <IonGrid className='ion-padding'>
            <IonRow>
              <IonTitle>Where would you like to go?</IonTitle>
            </IonRow>
            <form>
              <IonRow className='ion-padding'>
                <IonCol>
                  <IonSelect id='selectOrigin' label="From" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setOrigin(e.detail.value)}>
                    {possible.map((con) => <IonSelectOption>{con.origin}</IonSelectOption>)}
                  </IonSelect>
                </IonCol>
                <IonCol>
                  <IonSelect id='selectDestination' label="To" labelPlacement="floating" fill="outline" interface="popover" onIonChange={(e) => setDestination(e.detail.value)}>
                    {possible.map((con) => <IonSelectOption>{con.destination}</IonSelectOption>)}
                  </IonSelect>
                </IonCol>
              </IonRow>
            </form>
      </IonGrid>
  );
})

export default SelectContainer;