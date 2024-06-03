import { IonButton, IonCol, IonDatetime, IonGrid, IonIcon, IonRow, IonSelect, IonSelectOption, IonText, IonTitle } from "@ionic/react"
import { useContext } from "react";
import { arrowForward, handRight } from "ionicons/icons";
import { SharedVariablesContext } from "../support/Variables";

const TimeContainer: React.FC = (() => {
    const {
    connections,
    setConnections,
    possible,
    setPossible,
    selectedOrigin,
    setSelectedOrigin,
    selectedDestination,
    setSelectedDestination,
    setSelectedDate,
  } = useContext(SharedVariablesContext);
    const updatePossibilities = () => {
        
    } 
    return (
        <IonGrid className='ion-padding'>
              <IonRow>
                <IonCol className="ion-text-center">
                  <IonTitle color={"tertiary"}>Time Selection</IonTitle>
                </IonCol>
              </IonRow>
              <IonRow className="ion-padding-top">
                <IonCol>
                  <IonTitle className="ion-padding-bottom">When do you want to go?</IonTitle>
                  <IonDatetime min={new Date().toISOString()} firstDayOfWeek={1} presentation="date" onIonChange={(e) => {if(e.detail.value) setSelectedDate(e.detail.value.toString().split('T')[0])}}></IonDatetime>
                </IonCol>
              </IonRow>
              
        </IonGrid>
    );
})

export default TimeContainer;