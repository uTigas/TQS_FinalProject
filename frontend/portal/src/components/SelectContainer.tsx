import { IonButton, IonCol, IonGrid, IonIcon, IonRow, IonSelect, IonSelectOption, IonTitle } from "@ionic/react"
import { arrowForwardCircle, handRight, search } from "ionicons/icons";
import TimeContainer from "./TimeContainer";
import React from "react";
import { container, connections, possible, selectedOrigin, selectedDestination } from '../pages/Tab1';

const SelectContainer: React.FC = (() => {
    const updatePossibilities = () => {
        
    }

    const showTimeContainer = () => {
        container = <TimeContainer origin={selectedOrigin} destination={selectedDestination} />;
    }  

    return (
        <IonGrid className='ion-padding'>
              <IonRow>
                <IonTitle>Where would you like to go?</IonTitle>
              </IonRow>
              <form>
                <IonRow className='ion-padding'>
                  <IonCol>
                    <IonSelect id='selectOrigin' label="Select Origin" labelPlacement="floating" fill="outline" interface="popover" onIonChange={() => updatePossibilities()}>
                      {possible.map((con) => <IonSelectOption>{con.origin}</IonSelectOption>)}
                    </IonSelect>
                  </IonCol>
                  <IonCol>
                    <IonSelect id='selectDestination' label="Select Destination" labelPlacement="floating" fill="outline" interface="popover" onIonChange={() => updatePossibilities()}>
                      {possible.map((con) => <IonSelectOption>{con.destination}</IonSelectOption>)}
                    </IonSelect>
                  </IonCol>
                  
                </IonRow>
              </form>
        </IonGrid>
    );
})

export default SelectContainer;